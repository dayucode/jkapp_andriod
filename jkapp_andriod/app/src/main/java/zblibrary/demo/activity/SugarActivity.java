package zblibrary.demo.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.TimePicker;
import cn.qqtheme.framework.util.ConvertUtils;
import lombok.SneakyThrows;
import zblibrary.demo.R;
import zblibrary.demo.enums.PressureToastEnum;
import zblibrary.demo.enums.PressureTypeEnum;
import zblibrary.demo.enums.SugarTypeEnum;
import zblibrary.demo.heartrate.ToastUtil;
import zblibrary.demo.model.BloodPressure;
import zblibrary.demo.model.Sugar;
import zblibrary.demo.model.result.Result;
import zblibrary.demo.model.result.ResultEnum;
import zblibrary.demo.util.GsonUtil;
import zblibrary.demo.util.HttpRequest;
import zblibrary.demo.util.SPUtil;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.util.StringUtil;

public class SugarActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener, OnBottomDragListener {
    private static final String TAG = "XuTangActivity";
    TextView text1,text2,text3;
    ImageView level1,level2,level3,level4;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sugar_activity);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        level4 = findViewById(R.id.level4);

    }

    @Override
    public void initData() {
        userId = (String) SPUtil.getParam(Objects.requireNonNull(getActivity()), "userId", "");

    }

    @Override
    public void initEvent() {
        findViewById(R.id.onTimePicker).setOnClickListener(this);
        findViewById(R.id.onYearMonthDayPicker).setOnClickListener(this);
        findViewById(R.id.onSinglePicker).setOnClickListener(this);

    }

    @SuppressLint({"NonConstantResourceId", "DefaultLocale"})
    @Override
    public void onClick(View v) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curDateStr = sdf.format(date);
        String[] sub_date = curDateStr.split(" ");
        String sub1 = sub_date[0];
        String sub2 = sub_date[1];
        String[] dateArr = sub1.split("-");
        int year = Integer.parseInt(dateArr[0]);
        int mouth = Integer.parseInt(dateArr[1]);
        int day = Integer.parseInt(dateArr[2]);
        String[] timeArr = sub2.split(":");
        int hour_x = Integer.parseInt(timeArr[0]);
        int minute_x = Integer.parseInt(timeArr[1]);
        switch (v.getId()) {
            case R.id.onYearMonthDayPicker:
                final DatePicker picker1 = new DatePicker(this);
                picker1.setCanceledOnTouchOutside(true);
                picker1.setUseWeight(true);
                picker1.setTopPadding(ConvertUtils.toPx(this, 10));
                picker1.setRangeEnd(2050, 1, 1);
                picker1.setRangeStart(2016, 1, 1);
                picker1.setSelectedItem(year, mouth, day);
                picker1.setResetWhileWheel(false);
                picker1.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @SneakyThrows
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        text1.setText(year + "-" + month + "-" + day);
                        saveSugar(text1.getText().toString(),text2.getText().toString(),text3.getText().toString());
                    }
                });
                picker1.setOnWheelListener(new DatePicker.OnWheelListener() {
                    @Override
                    public void onYearWheeled(int index, String year) {
                        picker1.setTitleText(year + "-" + picker1.getSelectedMonth() + "-" + picker1.getSelectedDay());
                    }

                    @Override
                    public void onMonthWheeled(int index, String month) {
                        picker1.setTitleText(picker1.getSelectedYear() + "-" + month + "-" + picker1.getSelectedDay());
                    }

                    @Override
                    public void onDayWheeled(int index, String day) {
                        picker1.setTitleText(picker1.getSelectedYear() + "-" + picker1.getSelectedMonth() + "-" + day);
                    }
                });
                picker1.show();
                break;
            case R.id.onTimePicker:
                TimePicker picker2 = new TimePicker(this, TimePicker.HOUR_24);
                picker2.setUseWeight(false);
                picker2.setCycleDisable(false);
                picker2.setRangeStart(0, 0);//00:00
                picker2.setRangeEnd(23, 59);//23:59
                picker2.setSelectedItem(hour_x, minute_x);
                picker2.setTopLineVisible(false);
                picker2.setTextPadding(ConvertUtils.toPx(this, 15));
                picker2.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                    @SneakyThrows
                    @Override
                    public void onTimePicked(String hour, String minute) {
                        text2.setText(hour + ":" + minute);
                        saveSugar(text1.getText().toString(),text2.getText().toString(),text3.getText().toString());
                    }
                });
                picker2.show();
                break;
            case R.id.onSinglePicker:
                NumberPicker picker3 = new NumberPicker(this);
                picker3.setWidth(picker3.getScreenWidthPixels());
                picker3.setCycleDisable(false);
                picker3.setDividerVisible(false);
                picker3.setOffset(2);//?????????
                picker3.setRange(0, 25, 0.1);//????????????
                picker3.setSelectedItem(125);
                picker3.setLabel("(mmol/L");
                picker3.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
                    @SneakyThrows
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNumberPicked(int index, Number item) {
                        text3.setText(item+"");
                        Log.i(TAG, "onNumberPicked: ========"+item);
                        saveSugar(text1.getText().toString(),text2.getText().toString(),text3.getText().toString());
                    }
                });
                picker3.show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void onDragBottom(boolean rightToLeft) {

    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    void saveSugar(String text1,String text2,String text3) throws ParseException {
        String choseText = "?????????";
        if (!text1.equals(choseText) && !text2.equals(choseText) && !text3.equals(choseText)){
            String testTime = text1+" "+text2+":00";
            Sugar sugar = new Sugar();
            sugar.setUserId(userId);
            sugar.setSugarValue(Double.valueOf(text3));
            sugar.setTestTime(testTime);
            Map requestMap = JSON.parseObject(JSON.toJSONString(sugar), Map.class);
            HttpRequest.saveSugar(requestMap, 5, new OnHttpResponseListener() {
                @Override
                public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                    Log.i(TAG, "onHttpResponse:"+resultJson);
                    Result result = GsonUtil.json2Bean(resultJson, Result.class);
                    if (result.getCode().equals(ResultEnum.SUCCESS.getCode())){
                        lastTimeTest();
                    }else {
                        String message = result.getMessage();
                        if (!StringUtil.isEmpty(message)){
                            ToastUtil.showToast(SugarActivity.this, message);
                        }
                    }
                }
            });
        }
    }
    void lastTimeTest() {
        HttpRequest.lastTimeSugar(userId, 4, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Log.i(TAG, "onHttpResponse:"+resultJson);
                Result result = JSONObject.parseObject(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONObject jsonObject = JSONObject.parseObject(result.getData().toString());
                        String pressureType = jsonObject.getString("sugarType");
                        if (!StringUtil.isEmpty(pressureType)) {
                            level1.setImageResource(R.drawable.level);
                            level2.setImageResource(R.drawable.level);
                            level3.setImageResource(R.drawable.level);
                            level4.setImageResource(R.drawable.level);
                            Integer level = Integer.parseInt(pressureType);
                            if (level.equals(SugarTypeEnum.LOW.getCode())) {
                                level1.setImageResource(R.drawable.level_active);
                                //ToastUtil.showToastLong(SugarActivity.this,"????????????:"+ SugarTypeEnum.LOW.getMsg());
                            }
                            if (level.equals(SugarTypeEnum.NORMAL.getCode())) {
                                level2.setImageResource(R.drawable.level_active);
                                //ToastUtil.showToastLong(SugarActivity.this,"????????????:"+ SugarTypeEnum.NORMAL.getMsg());
                            }
                            if (level.equals(SugarTypeEnum.HIGH.getCode())) {
                                level3.setImageResource(R.drawable.level_active);
                                //ToastUtil.showToastLong(SugarActivity.this,"????????????:"+ SugarTypeEnum.HIGH.getMsg());
                            }
                            if (level.equals(SugarTypeEnum.SERIOUS.getCode())) {
                                level4.setImageResource(R.drawable.level_active);
                                //ToastUtil.showToastLong(SugarActivity.this,"????????????:"+ SugarTypeEnum.SERIOUS.getMsg());
                            }
                        }
                    }
                } else {
                    ToastUtil.showToast(SugarActivity.this, "??????????????????");
                }
            }
        });
    }
}
