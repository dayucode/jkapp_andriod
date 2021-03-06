package zblibrary.demo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import zblibrary.demo.R;
import zblibrary.demo.enums.EyeStatusToastEnum;
import zblibrary.demo.enums.EyeStatusTypeEnum;
import zblibrary.demo.enums.PressureTypeEnum;
import zblibrary.demo.eyetest.JsonParser;
import zblibrary.demo.heartrate.ToastUtil;
import zblibrary.demo.model.BloodPressure;
import zblibrary.demo.model.EyeTest;
import zblibrary.demo.model.result.Result;
import zblibrary.demo.model.result.ResultEnum;
import zblibrary.demo.util.GsonUtil;
import zblibrary.demo.util.HttpRequest;
import zblibrary.demo.util.SPUtil;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.util.StringUtil;

public class TestEyeActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG = TestEyeActivity.class.getSimpleName();
    static final int DIRECT_UNCLEAR = -1;
    static final int DIRECT_UP = 0;
    static final int DIRECT_RIGHT = 1;
    static final int DIRECT_DOWN = 2;
    static final int DIRECT_LEFT = 3;
    static final String TEXT_UP = "???";
    static final String TEXT_RIGHT = "???";
    static final String TEXT_DOWN = "???";
    static final String TEXT_LEFT = "???";
    static final String TEXT_UNCLEAR = "?????????";
    static final String TEXT_NOT_SEE = "?????????";
    static final String TEXT_NOT_VISIBLE = "?????????";
    private static final String GRAMMAR_TYPE_ABNF = "abnf";

    private ImageView mImgEye;
    private int mIndex = 0;
    private float mLastToDegrees = 0;
    private int mDirect = 1;
    private int mRet = 0;
    private ArrayList<Item> mUserList = new ArrayList<>();
    private Toast mToast;
    private String mEngineType = SpeechConstant.TYPE_CLOUD;


    // ??????????????????
    private SpeechRecognizer mAsr;
    // ??????
    private SharedPreferences mSharedPreferences;
    private static final String KEY_GRAMMAR_ABNF_ID = "grammar_abnf_id";
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_eye);
        init();
        initAsr();
    }

    /**
     * ?????????view
     */
    private void init() {
        mImgEye = (ImageView) findViewById(R.id.img_e);
        findViewById(R.id.rl_top).setOnClickListener(this);
        findViewById(R.id.rl_bottom).setOnClickListener(this);
        findViewById(R.id.rl_left).setOnClickListener(this);
        findViewById(R.id.rl_right).setOnClickListener(this);
        userId = (String) SPUtil.getParam(TestEyeActivity.this, "userId", "");
    }


    /**
     * ?????????????????????????????????
     */
    private void initAsr() {
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mSharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        // ?????????????????????
        mAsr = SpeechRecognizer.createRecognizer(this, mInitListener);
        buildAsr();
    }


    /**
     * ??????????????????
     */
    private void buildAsr() {
//        showTip("?????????????????????/????????????");
        // ??????-?????????????????????????????????id
        String mCloudGrammar =
                "#ABNF 1.0 UTF-8;"
                        + "languagezh-CN;"
                        + "mode voice;"
                        + "root $main;"
                        + "$main = $place1$place2| ????????? | ????????? | ?????????"
                        + "$place1 = ??? | ??? | ??? | ??? ;"
                        + "$place2 = ??? | ??? | ???;";
        //??????????????????
        mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        mAsr.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mRet = mAsr.buildGrammar(GRAMMAR_TYPE_ABNF, mCloudGrammar, mCloudGrammarListener);
        if (mRet != ErrorCode.SUCCESS) {
//            showTip("??????????????????,????????????" + mRet);
        } else {
            startListening();
        }

    }


    //????????????
    private void startListening() {
        mRet = mAsr.startListening(mRecognizerListener);
        if (mRet != ErrorCode.SUCCESS) {
            if (mRet == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //???????????????????????????????????????
            } else {
//                showTip("????????????,?????????: " + mRet);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_top:
                up();
                break;
            case R.id.rl_bottom:
                down();
                break;
            case R.id.rl_left:
                left();
                break;
            case R.id.rl_right:
                right();
                break;
        }
    }


    private void up() {

        final boolean flag = mDirect == DIRECT_UP;

        //?????????????????? ??????????????????
        if (!checkErrorTimes() && !flag) {
            showResult();
            return;
        }
        mUserList.add(new Item(mIndex, mDirect, mDirect == DIRECT_UP ? DIRECT_UP : DIRECT_UNCLEAR));
        next(flag);
    }

    private void down() {
        final boolean flag = mDirect == DIRECT_DOWN;

        //?????????????????? ??????????????????
        if (!checkErrorTimes() && !flag) {
            showResult();
            return;
        }
        mUserList.add(new Item(mIndex, mDirect, flag ? DIRECT_DOWN : DIRECT_UNCLEAR));
        next(flag);

    }

    private void left() {
        final boolean flag = mDirect == DIRECT_LEFT;

        //?????????????????? ??????????????????
        if (!checkErrorTimes() && !flag) {
            showResult();
            return;
        }
        mUserList.add(new Item(mIndex, mDirect, flag ? DIRECT_LEFT : DIRECT_UNCLEAR));
        next(flag);

    }

    private void right() {
        final boolean flag = mDirect == DIRECT_RIGHT;
        //?????????????????? ??????????????????
        if (!checkErrorTimes() && !flag) {
            showResult();
            return;
        }

        mUserList.add(new Item(mIndex, mDirect, flag ? DIRECT_RIGHT : DIRECT_UNCLEAR));
        next(flag);

    }

    /**
     * ???????????? ???????????????
     */
    private void unClear() {
        //?????????????????? ????????????
        if (!checkErrorTimes()) {
            showResult();
            return;
        }
        mUserList.add(new Item(mIndex, mDirect, DIRECT_UNCLEAR));
        next(false);
    }


    /**
     * ?????????????????????????????????
     *
     * @return flag
     */
    private boolean checkErrorTimes() {
        boolean flag = true;
        for (Item item : mUserList) {
            int index = item.getIndex();
            if (index == mIndex && item.getUser_direct() == DIRECT_UNCLEAR) {
                flag = false;
                break;
            }
        }
        return flag;
    }


    private void next(boolean clear) {
        if (clear) {
            mIndex++;
        }
        if (mIndex > 8) {
            showResult();
        } else {
            playAnim();
        }
    }


    private void showResult() {
        int wrongTimes = 0;
        int rightTimes = 0;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("????????????");
        String msg = "";
        for (Item item : mUserList) {
            if (item.getResult().contains("??????")){
                rightTimes++;
            }else {
                wrongTimes++;
            }
            msg += item.getResult();
        }
        saveEyeTest(wrongTimes,rightTimes);
        dialog.setMessage(msg+getReqResult());
        dialog.create();
        dialog.setNegativeButton("????????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(new Intent(TestEyeActivity.this, TestEyeActivity.class));
            }
        });
        dialog.setPositiveButton("????????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.show();
    }


    /**
     * ???????????? ?????? + ??????
     * ??????mIndex++ ??????????????????10%
     * mDirect?????????????????? ??????????????????????????? ????????????
     */
    private void playAnim() {

        float from = 1 - (float) (0.1 * mIndex);
        float to = 1 - (float) (0.1 * (mIndex + 1));

        AnimationSet set = new AnimationSet(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(from, to, from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(scaleAnimation);

        Random random = new Random();
        int rd = random.nextInt(4);

        //???????????????????????? ??????????????????
        float fromDegrees = mLastToDegrees;
        mLastToDegrees = fromDegrees + 90 * rd;
        float toDegrees = mLastToDegrees;


        RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);

        set.setDuration(500);
        set.setFillAfter(true);
        mImgEye.startAnimation(set);


        //????????????????????????
        mDirect += rd;
        if (mDirect < 4) {
            mDirect += 4;
        }
        mDirect %= 4;

        mAsr.startListening(mRecognizerListener);

    }


    /**
     * ??????????????????????????????
     */
    private GrammarListener mCloudGrammarListener = new GrammarListener() {
        @Override
        public void onBuildFinish(String grammarId, SpeechError error) {
            if (error == null) {
                String grammarID = new String(grammarId);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                if (!TextUtils.isEmpty(grammarId))
                    editor.putString(KEY_GRAMMAR_ABNF_ID, grammarID);
                editor.apply();
//                showTip("?????????????????????" + grammarId);
            } else {
//                showTip("??????????????????,????????????" + error.getErrorCode());
            }
        }
    };


    /**
     * ?????????????????????
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
//                showTip("???????????????,????????????" + code);
            }
        }
    };


    /**
     * ??????????????????
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
//            showTip("????????????????????????????????????" + volume);
            Log.d(TAG, "?????????????????????" + data.length);
        }

        @Override
        public void onResult(final RecognizerResult result, boolean isLast) {
            if (null != result) {
                Log.d(TAG, "recognizer result???" + result.getResultString());

                if ("cloud".equalsIgnoreCase(mEngineType)) {

                    String text = JsonParser.parseGrammarResult(result.getResultString());

                    if (TextUtils.isEmpty(text)) {
//                        showTip("??????????????????");
                    } else {
                        if (text.contains(TEXT_UP)) {
                            up();
                        } else if (text.contains(TEXT_RIGHT)) {
                            right();
                        } else if (text.contains(TEXT_DOWN)) {
                            down();
                        } else if (text.contains(TEXT_LEFT)) {
                            left();
                        } else if (text.contains(TEXT_UNCLEAR) || text.contains(TEXT_NOT_VISIBLE) || text.contains(TEXT_NOT_SEE)) {
                            unClear();
                        } else {
//                            showTip("??????????????????");
                        }
                    }
                }

            } else {
                Log.d(TAG, "recognizer result : null");
            }
        }

        @Override
        public void onEndOfSpeech() {
            // ??????????????????????????????????????????????????????????????????????????????????????????????????????
//            showTip("????????????");
        }

        @Override
        public void onBeginOfSpeech() {
            // ??????????????????sdk??????????????????????????????????????????????????????????????????
//            showTip("????????????");
        }

        @Override
        public void onError(SpeechError error) {
//            showTip("onError Code???" + error.getErrorCode());
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // ??????????????????????????????????????????id??????????????????????????????id??????????????????????????????????????????????????????????????????????????????
            // ??????????????????????????????id???null
            //  if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //      String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //      Log.d(TAG, "session id =" + sid);
            //  }
        }

    };
    public String getReqResult() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }


    private void showTip(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mToast.setText(str);
                mToast.show();
            }
        });
    }


    /**
     * ????????? ??????????????????
     */
    class Item {
        //??????
        private int index;
        //???????????????
        private int direct;
        //?????????????????????
        private int user_direct;

        public Item(int index, int direct, int user_direct) {
            this.index = index;
            this.direct = direct;
            this.user_direct = user_direct;
        }

        public int getIndex() {
            return index;
        }

        public int getUser_direct() {
            return user_direct;
        }

        public String getResult() {
            StringBuilder sb = new StringBuilder();
            sb.append("???");
            sb.append(index + 1);
            sb.append("???");
            sb.append("  ");
            sb.append(appendText(direct));
            sb.append("  ");
            sb.append("??????");
            sb.append("  :  ");
            sb.append(appendText(user_direct));
            sb.append("  ---->  ");
            sb.append(direct == user_direct ? "??????" : "??????");
            sb.append("\n");
            return sb.toString();
        }
        private String appendText(int direct) {
            String text = "";
            switch (direct) {
                case DIRECT_UP:
                    text = "???";
                    break;
                case DIRECT_RIGHT:
                    text = "???";
                    break;
                case DIRECT_DOWN:
                    text = "???";
                    break;
                case DIRECT_LEFT:
                    text = "???";
                    break;
                case DIRECT_UNCLEAR:
                    text = "?????????";
                    break;
            }

            return text;
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ?????????????????????
        mAsr.cancel();
        mAsr.destroy();
    }
    private void saveEyeTest(int wrongTimes,int rightTimes){
        EyeTest eyeTest = new EyeTest();
        eyeTest.setUserId(userId);
        eyeTest.setWrongTimes(wrongTimes);
        eyeTest.setRightTimes(rightTimes);
        Map requestMap = JSON.parseObject(JSON.toJSONString(eyeTest), Map.class);
        HttpRequest.saveEyeTest(requestMap, 6, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Log.i(TAG, "onHttpResponse:"+resultJson);
                Result result = GsonUtil.json2Bean(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())){
                    lastTimeTest();
                }else {
                    String message = result.getMessage();
                    if (!StringUtil.isEmpty(message)){
                        ToastUtil.showToast(TestEyeActivity.this, message);
                    }
                }
            }
        });
    }
    void lastTimeTest() {
        HttpRequest.lastTimeEyeTest(userId, 4, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Log.i(TAG, "onHttpResponse:"+resultJson);
                Result result = JSONObject.parseObject(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONObject jsonObject = JSONObject.parseObject(result.getData().toString());
                        String testType = jsonObject.getString("testType");
                        if (!StringUtil.isEmpty(testType)) {
                            Integer type = Integer.parseInt(testType);
                            if (type.equals(EyeStatusTypeEnum.LOW.getCode())) {
                               ToastUtil.showToastLong(TestEyeActivity.this,"????????????:"+ EyeStatusToastEnum.LOW.getMsg());
                            }
                            if (type.equals(EyeStatusTypeEnum.NORMAL.getCode())) {
                                ToastUtil.showToastLong(TestEyeActivity.this,"????????????:"+ EyeStatusToastEnum.NORMAL.getMsg());
                            }
                            if (type.equals(EyeStatusTypeEnum.LIGHT.getCode())) {
                                ToastUtil.showToastLong(TestEyeActivity.this,"????????????:"+ EyeStatusToastEnum.LIGHT.getMsg());
                            }
                            if (type.equals(EyeStatusTypeEnum.MODERATE.getCode())) {
                                ToastUtil.showToastLong(TestEyeActivity.this,"????????????:"+ EyeStatusToastEnum.MODERATE.getMsg());
                            }
                            if (type.equals(EyeStatusTypeEnum.SERIOUS.getCode())) {
                                ToastUtil.showToastLong(TestEyeActivity.this,"????????????:"+ EyeStatusToastEnum.SERIOUS.getMsg());
                            }
                        }
                    }
                }
            }
        });
    }


}
