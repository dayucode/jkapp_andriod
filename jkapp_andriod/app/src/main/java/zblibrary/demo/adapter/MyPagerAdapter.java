package zblibrary.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import zblibrary.demo.R;
import zblibrary.demo.activity.SugarActivity;
import zblibrary.demo.activity.TestEyeActivity;
import zblibrary.demo.activity.WeightActivity;
import zblibrary.demo.activity.XueYaActivity;
import zblibrary.demo.enums.EyeStatusTypeEnum;
import zblibrary.demo.enums.HeartRateTypeEnum;
import zblibrary.demo.enums.PressureTypeEnum;
import zblibrary.demo.enums.SugarTypeEnum;
import zblibrary.demo.enums.WeightTypeEnum;
import zblibrary.demo.heartrate.HeartRateActivity;
import zblibrary.demo.heartrate.ToastUtil;
import zblibrary.demo.model.result.Result;
import zblibrary.demo.model.result.ResultEnum;
import zblibrary.demo.util.HttpRequest;
import zblibrary.demo.util.SPUtil;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.util.StringUtil;

public class MyPagerAdapter extends PagerAdapter {
    //private static final String TAG = "MyPagerAdapter";
    private ArrayList<View> viewLists;
    private ArrayList<String> titleLists;
    private Context mContent;
    TextView xl_text1, xl_text2, xl_text3;
    TextView xy_text1, xy_text2, xy_text3,xy_text4;
    TextView tz_text1, tz_text2, tz_text3,tz_text4;
    TextView sg_text1, sg_text2, sg_text3;
    TextView sl_text1, sl_text2;
    String userId;

    public MyPagerAdapter() {
    }

    public MyPagerAdapter(ArrayList<View> viewLists, ArrayList<String> titleLists, Context context) {
        this.viewLists = viewLists;
        this.titleLists = titleLists;
        this.mContent = context;
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewLists.get(position));
        userId = (String) SPUtil.getParam(Objects.requireNonNull(mContent), "userId", "");
        switch (position) {
            case 0:
                View view0 = viewLists.get(0);
                xl_text1 = view0.findViewById(R.id.text1);
                xl_text2 = view0.findViewById(R.id.text2);
                xl_text3 = view0.findViewById(R.id.text3);
                xl_lastTimeTest();
                ImageButton button1 = view0.findViewById(R.id.img_bottom);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContent, HeartRateActivity.class);//你要跳转的界面
                        mContent.startActivity(intent);
                    }
                });
                break;
            case 1:
                View view1 = viewLists.get(1);
                xy_text1 = view1.findViewById(R.id.text1);
                xy_text2 = view1.findViewById(R.id.text2);
                xy_text3 = view1.findViewById(R.id.text3);
                xy_text4 = view1.findViewById(R.id.text4);
                xy_lastTimeTest();
                ImageButton button2 = view1.findViewById(R.id.img_bottom);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContent, XueYaActivity.class);//你要跳转的界面
                        mContent.startActivity(intent);
                    }
                });
                break;
            case 2:
                View view2 = viewLists.get(2);
                tz_text1 = view2.findViewById(R.id.text1);
                tz_text2 = view2.findViewById(R.id.text2);
                tz_text3 = view2.findViewById(R.id.text3);
                tz_text4 = view2.findViewById(R.id.text4);
                wt_lastTimeTest();
                ImageButton button3 = view2.findViewById(R.id.img_bottom);
                button3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContent, WeightActivity.class);//你要跳转的界面
                        mContent.startActivity(intent);
                    }
                });
                break;
            case 3:
                View view3 = viewLists.get(3);
                sg_text1 = view3.findViewById(R.id.text1);
                sg_text2 = view3.findViewById(R.id.text2);
                sg_text3 = view3.findViewById(R.id.text3);
                sg_lastTimeTest();
                ImageButton button4 = view3.findViewById(R.id.img_bottom);
                button4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContent, SugarActivity.class);//你要跳转的界面
                        mContent.startActivity(intent);
                    }
                });
                break;
            case 4:
                View view4 = viewLists.get(4);
                sl_text1 = view4.findViewById(R.id.text1);
                sl_text2 = view4.findViewById(R.id.text2);
                sl_lastTimeTest();
                ImageView button = view4.findViewById(R.id.eye_test);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContent, TestEyeActivity.class);//你要跳转的界面
                        mContent.startActivity(intent);
                    }
                });
                break;
            default:
                break;
        }
        return viewLists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleLists.get(position);
    }

    void xl_lastTimeTest() {
        HttpRequest.lastTimeHeartRate(userId, 4, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Result result = JSONObject.parseObject(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONObject jsonObject = JSONObject.parseObject(result.getData().toString());
                        String heartType = jsonObject.getString("heartType");
                        String heartTypeText = null;
                        if (!StringUtil.isEmpty(heartType)) {
                            Integer type = Integer.parseInt(heartType);
                            if (type.equals(HeartRateTypeEnum.DAMAGE.getCode())) {
                                heartTypeText = HeartRateTypeEnum.DAMAGE.getMsg();
                            }
                            if (type.equals(HeartRateTypeEnum.NORMAL.getCode())) {
                                heartTypeText = HeartRateTypeEnum.NORMAL.getMsg();
                            }
                            if (type.equals(HeartRateTypeEnum.LOW.getCode())) {
                                heartTypeText = HeartRateTypeEnum.LOW.getMsg();
                            }
                        }
                        xl_text1.setText(heartTypeText);
                        xl_text2.setText("心率(次):" + jsonObject.getString("heartRate"));
                        xl_text3.setText("时间:" + jsonObject.getString("testTime"));
                    }
                } else {
                    ToastUtil.showToast(mContent, "获取信息失败");
                }
            }
        });
    }
    void xy_lastTimeTest() {
        HttpRequest.lastTimeBloodPressure(userId, 4, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Result result = JSONObject.parseObject(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONObject jsonObject = JSONObject.parseObject(result.getData().toString());
                        String pressureType = jsonObject.getString("pressureType");
                        String typeText = null;
                        if (!StringUtil.isEmpty(pressureType)) {
                            Integer level = Integer.parseInt(pressureType);
                            if (level.equals(PressureTypeEnum.LOW.getCode())) {
                                typeText = PressureTypeEnum.LOW.getMsg();
                            }
                            if (level.equals(PressureTypeEnum.NORMAL.getCode())) {
                                typeText = PressureTypeEnum.NORMAL.getMsg();
                            }
                            if (level.equals(PressureTypeEnum.HIGH.getCode())) {
                                typeText = PressureTypeEnum.HIGH.getMsg();
                            }
                            if (level.equals(PressureTypeEnum.LIGHT.getCode())) {
                                typeText = PressureTypeEnum.LIGHT.getMsg();
                            }
                            if (level.equals(PressureTypeEnum.MODERATE.getCode())) {
                                typeText = PressureTypeEnum.MODERATE.getMsg();
                            }
                            if (level.equals(PressureTypeEnum.SERIOUS.getCode())) {
                                typeText = PressureTypeEnum.SERIOUS.getMsg();
                            }
                            xy_text1.setText(typeText);
                            xy_text2.setText("高压(mmHg):" + jsonObject.getString("heightPressure"));
                            xy_text3.setText("低压(mmHg):" + jsonObject.getString("lowPressure"));
                            xy_text4.setText("时间:" + jsonObject.getString("testTime"));
                        }
                    }
                } else {
                    ToastUtil.showToast(mContent, "获取信息失败");
                }
            }
        });
    }
    void sl_lastTimeTest() {
        HttpRequest.lastTimeEyeTest(userId, 4, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Result result = JSONObject.parseObject(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONObject jsonObject = JSONObject.parseObject(result.getData().toString());
                        String testType = jsonObject.getString("testType");
                        if (!StringUtil.isEmpty(testType)) {
                            Integer type = Integer.parseInt(testType);
                            String typeText = null;
                            if (type.equals(EyeStatusTypeEnum.LOW.getCode())) {
                                typeText = EyeStatusTypeEnum.LOW.getMsg();
                            }
                            if (type.equals(EyeStatusTypeEnum.NORMAL.getCode())) {
                                typeText = EyeStatusTypeEnum.NORMAL.getMsg();
                            }
                            if (type.equals(EyeStatusTypeEnum.LIGHT.getCode())) {
                                typeText = EyeStatusTypeEnum.LIGHT.getMsg();
                            }
                            if (type.equals(EyeStatusTypeEnum.MODERATE.getCode())) {
                                typeText = EyeStatusTypeEnum.MODERATE.getMsg();
                            }
                            if (type.equals(EyeStatusTypeEnum.SERIOUS.getCode())) {
                                typeText = EyeStatusTypeEnum.SERIOUS.getMsg();
                            }
                            sl_text1.setText(jsonObject.getString("createTime"));
                            sl_text2.setText(typeText);
                        }
                    }
                }
            }
        });
    }
    void wt_lastTimeTest() {
        HttpRequest.lastTimeObesity(userId, 4, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Result result = JSONObject.parseObject(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONObject jsonObject = JSONObject.parseObject(result.getData().toString());
                        String testType = jsonObject.getString("obesityType");
                        if (!StringUtil.isEmpty(testType)) {
                            Integer type = Integer.parseInt(testType);
                            String typeText = null;
                            if (type.equals(WeightTypeEnum.LOW.getCode())) {
                                typeText = WeightTypeEnum.LOW.getMsg();
                            }
                            if (type.equals(WeightTypeEnum.NORMAL.getCode())) {
                                typeText = WeightTypeEnum.NORMAL.getMsg();
                            }
                            if (type.equals(WeightTypeEnum.HIGH.getCode())) {
                                typeText = WeightTypeEnum.HIGH.getMsg();
                            }
                            if (type.equals(WeightTypeEnum.SERIOUS.getCode())) {
                                typeText = WeightTypeEnum.SERIOUS.getMsg();
                            }
                            tz_text1.setText(typeText);
                            tz_text2.setText("BIM值:" + jsonObject.getString("bmi"));
                            tz_text3.setText("体重(kg):" + jsonObject.getString("obesityValue"));
                            tz_text4.setText("时间:" + jsonObject.getString("testTime"));
                        }
                    }
                }
            }
        });
    }
    void sg_lastTimeTest() {
        HttpRequest.lastTimeSugar(userId, 4, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Result result = JSONObject.parseObject(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONObject jsonObject = JSONObject.parseObject(result.getData().toString());
                        String testType = jsonObject.getString("sugarType");
                        if (!StringUtil.isEmpty(testType)) {
                            Integer type = Integer.parseInt(testType);
                            String typeText = null;
                            if (type.equals(SugarTypeEnum.LOW.getCode())) {
                                typeText = SugarTypeEnum.LOW.getMsg();
                            }
                            if (type.equals(SugarTypeEnum.NORMAL.getCode())) {
                                typeText = SugarTypeEnum.NORMAL.getMsg();
                            }
                            if (type.equals(SugarTypeEnum.HIGH.getCode())) {
                                typeText = SugarTypeEnum.HIGH.getMsg();
                            }
                            if (type.equals(SugarTypeEnum.SERIOUS.getCode())) {
                                typeText = SugarTypeEnum.SERIOUS.getMsg();
                            }
                            sg_text1.setText(typeText);
                            sg_text2.setText("血糖(mmol/L):" + jsonObject.getString("sugarValue"));
                            sg_text3.setText("时间:" + jsonObject.getString("testTime"));
                        }
                    }
                }
            }
        });
    }
}
