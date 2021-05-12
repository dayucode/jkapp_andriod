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
    static final String TEXT_UP = "上";
    static final String TEXT_RIGHT = "右";
    static final String TEXT_DOWN = "下";
    static final String TEXT_LEFT = "左";
    static final String TEXT_UNCLEAR = "看不清";
    static final String TEXT_NOT_SEE = "看不见";
    static final String TEXT_NOT_VISIBLE = "不清楚";
    private static final String GRAMMAR_TYPE_ABNF = "abnf";

    private ImageView mImgEye;
    private int mIndex = 0;
    private float mLastToDegrees = 0;
    private int mDirect = 1;
    private int mRet = 0;
    private ArrayList<Item> mUserList = new ArrayList<>();
    private Toast mToast;
    private String mEngineType = SpeechConstant.TYPE_CLOUD;


    // 语音识别对象
    private SpeechRecognizer mAsr;
    // 缓存
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
     * 初始化view
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
     * 初始化讯飞在线语音识别
     */
    private void initAsr() {
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mSharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        // 初始化识别对象
        mAsr = SpeechRecognizer.createRecognizer(this, mInitListener);
        buildAsr();
    }


    /**
     * 构建语法文件
     */
    private void buildAsr() {
//        showTip("上传预设关键词/语法文件");
        // 本地-构建语法文件，生成语法id
        String mCloudGrammar =
                "#ABNF 1.0 UTF-8;"
                        + "languagezh-CN;"
                        + "mode voice;"
                        + "root $main;"
                        + "$main = $place1$place2| 看不见 | 看不清 | 不清楚"
                        + "$place1 = 上 | 下 | 左 | 右 ;"
                        + "$place2 = 面 | 边 | 方;";
        //指定引擎类型
        mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        mAsr.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mRet = mAsr.buildGrammar(GRAMMAR_TYPE_ABNF, mCloudGrammar, mCloudGrammarListener);
        if (mRet != ErrorCode.SUCCESS) {
//            showTip("语法构建失败,错误码：" + mRet);
        } else {
            startListening();
        }

    }


    //开始监听
    private void startListening() {
        mRet = mAsr.startListening(mRecognizerListener);
        if (mRet != ErrorCode.SUCCESS) {
            if (mRet == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //未安装则跳转到提示安装页面
            } else {
//                showTip("识别失败,错误码: " + mRet);
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

        //已经错误一次 且本次也错误
        if (!checkErrorTimes() && !flag) {
            showResult();
            return;
        }
        mUserList.add(new Item(mIndex, mDirect, mDirect == DIRECT_UP ? DIRECT_UP : DIRECT_UNCLEAR));
        next(flag);
    }

    private void down() {
        final boolean flag = mDirect == DIRECT_DOWN;

        //已经错误一次 且本次也错误
        if (!checkErrorTimes() && !flag) {
            showResult();
            return;
        }
        mUserList.add(new Item(mIndex, mDirect, flag ? DIRECT_DOWN : DIRECT_UNCLEAR));
        next(flag);

    }

    private void left() {
        final boolean flag = mDirect == DIRECT_LEFT;

        //已经错误一次 且本次也错误
        if (!checkErrorTimes() && !flag) {
            showResult();
            return;
        }
        mUserList.add(new Item(mIndex, mDirect, flag ? DIRECT_LEFT : DIRECT_UNCLEAR));
        next(flag);

    }

    private void right() {
        final boolean flag = mDirect == DIRECT_RIGHT;
        //已经错误一次 且本次也错误
        if (!checkErrorTimes() && !flag) {
            showResult();
            return;
        }

        mUserList.add(new Item(mIndex, mDirect, flag ? DIRECT_RIGHT : DIRECT_UNCLEAR));
        next(flag);

    }

    /**
     * 看不清楚 两次后终止
     */
    private void unClear() {
        //已经错误一次 再次错误
        if (!checkErrorTimes()) {
            showResult();
            return;
        }
        mUserList.add(new Item(mIndex, mDirect, DIRECT_UNCLEAR));
        next(false);
    }


    /**
     * 检查是否已经错误过一次
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
        dialog.setTitle("测试结果");
        String msg = "";
        for (Item item : mUserList) {
            if (item.getResult().contains("正确")){
                rightTimes++;
            }else {
                wrongTimes++;
            }
            msg += item.getResult();
        }
        saveEyeTest(wrongTimes,rightTimes);
        dialog.setMessage(msg+getReqResult());
        dialog.create();
        dialog.setNegativeButton("重新测试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(new Intent(TestEyeActivity.this, TestEyeActivity.class));
            }
        });
        dialog.setPositiveButton("退出测试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.show();
    }


    /**
     * 播放动画 缩放 + 旋转
     * 随着mIndex++ 图片每次缩小10%
     * mDirect方向随机生成 如果错误一次不缩小 只换旋转
     */
    private void playAnim() {

        float from = 1 - (float) (0.1 * mIndex);
        float to = 1 - (float) (0.1 * (mIndex + 1));

        AnimationSet set = new AnimationSet(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(from, to, from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(scaleAnimation);

        Random random = new Random();
        int rd = random.nextInt(4);

        //保存本次旋转角度 以便下次使用
        float fromDegrees = mLastToDegrees;
        mLastToDegrees = fromDegrees + 90 * rd;
        float toDegrees = mLastToDegrees;


        RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);

        set.setDuration(500);
        set.setFillAfter(true);
        mImgEye.startAnimation(set);


        //记录旋转后的方向
        mDirect += rd;
        if (mDirect < 4) {
            mDirect += 4;
        }
        mDirect %= 4;

        mAsr.startListening(mRecognizerListener);

    }


    /**
     * 云端构建语法监听器。
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
//                showTip("语法构建成功：" + grammarId);
            } else {
//                showTip("语法构建失败,错误码：" + error.getErrorCode());
            }
        }
    };


    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
//                showTip("初始化失败,错误码：" + code);
            }
        }
    };


    /**
     * 识别监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
//            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onResult(final RecognizerResult result, boolean isLast) {
            if (null != result) {
                Log.d(TAG, "recognizer result：" + result.getResultString());

                if ("cloud".equalsIgnoreCase(mEngineType)) {

                    String text = JsonParser.parseGrammarResult(result.getResultString());

                    if (TextUtils.isEmpty(text)) {
//                        showTip("语音识别失败");
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
//                            showTip("语音识别失败");
                        }
                    }
                }

            } else {
                Log.d(TAG, "recognizer result : null");
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
//            showTip("结束说话");
        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
//            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
//            showTip("onError Code：" + error.getErrorCode());
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
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
     * 内部类 记录每次选择
     */
    class Item {
        //次数
        private int index;
        //系统的方向
        private int direct;
        //用户选择的方向
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
            sb.append("第");
            sb.append(index + 1);
            sb.append("次");
            sb.append("  ");
            sb.append(appendText(direct));
            sb.append("  ");
            sb.append("选择");
            sb.append("  :  ");
            sb.append(appendText(user_direct));
            sb.append("  ---->  ");
            sb.append(direct == user_direct ? "正确" : "错误");
            sb.append("\n");
            return sb.toString();
        }
        private String appendText(int direct) {
            String text = "";
            switch (direct) {
                case DIRECT_UP:
                    text = "上";
                    break;
                case DIRECT_RIGHT:
                    text = "右";
                    break;
                case DIRECT_DOWN:
                    text = "下";
                    break;
                case DIRECT_LEFT:
                    text = "左";
                    break;
                case DIRECT_UNCLEAR:
                    text = "看不清";
                    break;
            }

            return text;
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时释放连接
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
                               ToastUtil.showToastLong(TestEyeActivity.this,"温馨提示:"+ EyeStatusToastEnum.LOW.getMsg());
                            }
                            if (type.equals(EyeStatusTypeEnum.NORMAL.getCode())) {
                                ToastUtil.showToastLong(TestEyeActivity.this,"温馨提示:"+ EyeStatusToastEnum.NORMAL.getMsg());
                            }
                            if (type.equals(EyeStatusTypeEnum.LIGHT.getCode())) {
                                ToastUtil.showToastLong(TestEyeActivity.this,"温馨提示:"+ EyeStatusToastEnum.LIGHT.getMsg());
                            }
                            if (type.equals(EyeStatusTypeEnum.MODERATE.getCode())) {
                                ToastUtil.showToastLong(TestEyeActivity.this,"温馨提示:"+ EyeStatusToastEnum.MODERATE.getMsg());
                            }
                            if (type.equals(EyeStatusTypeEnum.SERIOUS.getCode())) {
                                ToastUtil.showToastLong(TestEyeActivity.this,"温馨提示:"+ EyeStatusToastEnum.SERIOUS.getMsg());
                            }
                        }
                    }
                }
            }
        });
    }


}
