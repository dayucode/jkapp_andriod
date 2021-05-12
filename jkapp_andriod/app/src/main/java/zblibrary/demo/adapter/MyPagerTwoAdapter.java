package zblibrary.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import zblibrary.demo.R;
import zblibrary.demo.activity.SugarActivity;
import zblibrary.demo.activity.TestEyeActivity;
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
import zblibrary.demo.util.GsonUtil;
import zblibrary.demo.util.HttpRequest;
import zblibrary.demo.util.SPUtil;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.util.StringUtil;

public class MyPagerTwoAdapter extends PagerAdapter {
    private ArrayList<View> viewLists;
    private ArrayList<String> titleLists;
    private Context mContent;
    String userId;
    ListView xl_list, xy_list, sl_list,xt_list,tz_list;
    Button delete_xy,delete_xl,delete_sl,delete_sg,delete_tz;

    public MyPagerTwoAdapter() {
    }

    public MyPagerTwoAdapter(ArrayList<View> viewLists, ArrayList<String> titleLists, Context context) {
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
                xl_list = view0.findViewById(R.id.list);
                delete_xl = view0.findViewById(R.id.delete4);
                view0.findViewById(R.id.list);
                delete_xl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpRequest.deleteXL(userId, 5, new OnHttpResponseListener() {
                            @Override
                            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                                Result result = GsonUtil.json2Bean(resultJson, Result.class);
                                String message = result.getMessage();
                                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())){
                                    ToastUtil.showToast(mContent, message);
                                }else {
                                    if (!StringUtil.isEmpty(message)){
                                        ToastUtil.showToast(mContent, message);
                                    }
                                }
                            }
                        });
                    }
                });
                xl_lastThreeMonth();
                break;
            case 1:
                View view1 = viewLists.get(1);
                xy_list = view1.findViewById(R.id.list);
                delete_xy = view1.findViewById(R.id.delete5);
                delete_xy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpRequest.deleteXy(userId, 5, new OnHttpResponseListener() {
                            @Override
                            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                                Result result = GsonUtil.json2Bean(resultJson, Result.class);
                                String message = result.getMessage();
                                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())){
                                    ToastUtil.showToast(mContent, message);
                                }else {
                                    if (!StringUtil.isEmpty(message)){
                                        ToastUtil.showToast(mContent, message);
                                    }
                                }
                            }
                        });
                    }
                });
                xy_lastThreeMonth();
                break;
            case 2:
                View view2 = viewLists.get(2);
                sl_list = view2.findViewById(R.id.list);
                delete_sl = view2.findViewById(R.id.delete1);
                delete_sl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpRequest.deleteSL(userId, 5, new OnHttpResponseListener() {
                            @Override
                            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                                Result result = GsonUtil.json2Bean(resultJson, Result.class);
                                String message = result.getMessage();
                                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())){
                                    ToastUtil.showToast(mContent, message);
                                }else {
                                    if (!StringUtil.isEmpty(message)){
                                        ToastUtil.showToast(mContent, message);
                                    }
                                }
                            }
                        });
                    }
                });
                sl_lastThreeMonth();
            case 3:
                View view3 = viewLists.get(3);
                xt_list = view3.findViewById(R.id.list);
                delete_sg = view3.findViewById(R.id.delete2);
                delete_sg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpRequest.deleteSg(userId, 5, new OnHttpResponseListener() {
                            @Override
                            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                                Result result = GsonUtil.json2Bean(resultJson, Result.class);
                                String message = result.getMessage();
                                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())){
                                    ToastUtil.showToast(mContent, message);
                                }else {
                                    if (!StringUtil.isEmpty(message)){
                                        ToastUtil.showToast(mContent, message);
                                    }
                                }
                            }
                        });
                    }
                });
                xt_lastThreeMonth();
            case 4:
                View view4 = viewLists.get(4);
                tz_list = view4.findViewById(R.id.list);
                delete_tz = view4.findViewById(R.id.delete3);
                delete_tz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpRequest.deleteTz(userId, 5, new OnHttpResponseListener() {
                            @Override
                            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                                Result result = GsonUtil.json2Bean(resultJson, Result.class);
                                String message = result.getMessage();
                                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())){
                                    ToastUtil.showToast(mContent, message);
                                }else {
                                    if (!StringUtil.isEmpty(message)){
                                        ToastUtil.showToast(mContent, message);
                                    }
                                }
                            }
                        });
                    }
                });
                tz_lastThreeMonth();
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

    void xl_lastThreeMonth() {
        HttpRequest.lastThreeMonthHeartRate(userId, 5, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Result result = JSONObject.parseObject(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONArray jsonArray = JSONArray.parseArray(result.getData().toString());
                        List<Map<String, Object>> xl_data = new ArrayList<Map<String, Object>>();
                        Map<String,Object> map=new HashMap<String, Object>();
                        map.put("beatTimes","心跳次数");
                        map.put("heartRate","心率");
                        map.put("heartTypeText","结果");
                        map.put("testTime","时间");
                        xl_data.add(map);
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String beatTimes = jsonObject.getString("beatTimes");
                            String heartRate = jsonObject.getString("heartRate");
                            String heartType = jsonObject.getString("heartType");
                            String testTime = jsonObject.getString("testTime");
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
                            Map<String, Object> map2 = new HashMap<String, Object>();
                            map2.put("beatTimes", beatTimes);
                            map2.put("heartRate", heartRate);
                            map2.put("heartTypeText", heartTypeText);
                            map2.put("testTime", testTime);
                            xl_data.add(map2);
                        }
                        xl_list.setAdapter(new SimpleAdapter(mContent,
                                xl_data, R.layout.xl_adapter_item,
                                new String[]{"beatTimes", "heartRate", "heartTypeText", "testTime"},
                                new int[]{R.id.text1, R.id.text2, R.id.text3, R.id.text4}));
                    }
                } else {
                    ToastUtil.showToast(mContent, "获取信息失败");
                }
            }
        });
    }

    void xy_lastThreeMonth() {
        HttpRequest.lastThreeMonthBloodPressure(userId, 6, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Result result = JSONObject.parseObject(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONArray jsonArray = JSONArray.parseArray(result.getData().toString());
                        List<Map<String, Object>> xy_data = new ArrayList<Map<String, Object>>();
                        Map<String,Object> map=new HashMap<String, Object>();
                        map.put("heightPressure","高压");
                        map.put("lowPressure","低压");
                        map.put("typeText","结果");
                        map.put("testTime","时间");
                        xy_data.add(map);
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String heightPressure = jsonObject.getString("heightPressure");
                            String lowPressure = jsonObject.getString("lowPressure");
                            String pressureType = jsonObject.getString("pressureType");
                            String testTime = jsonObject.getString("testTime");
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
                            }
                            Map<String, Object> map2 = new HashMap<String, Object>();
                            map2.put("heightPressure", heightPressure);
                            map2.put("lowPressure", lowPressure);
                            map2.put("typeText", typeText);
                            map2.put("testTime", testTime);
                            xy_data.add(map2);
                        }
                        xy_list.setAdapter(new SimpleAdapter(mContent,
                                xy_data, R.layout.xl_adapter_item,
                                new String[]{"heightPressure", "lowPressure", "typeText", "testTime"},
                                new int[]{R.id.text1, R.id.text2, R.id.text3, R.id.text4}));
                    }
                } else {
                    ToastUtil.showToast(mContent, "获取信息失败");
                }
            }
        });
    }

    void sl_lastThreeMonth() {
        HttpRequest.lastThreeMonthEyeTest(userId, 7, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Result result = JSONObject.parseObject(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONArray jsonArray = JSONArray.parseArray(result.getData().toString());
                        List<Map<String,Object>> sl_data=new ArrayList<Map<String,Object>>();
                        Map<String,Object> map=new HashMap<String, Object>();
                        map.put("rightTimes","正确次数");
                        map.put("wrongTimes","错误次数");
                        map.put("typeText","结果");
                        map.put("create_time","时间");
                        sl_data.add(map);
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String rightTimes = jsonObject.getString("rightTimes");
                            String wrongTimes = jsonObject.getString("wrongTimes");
                            String testType = jsonObject.getString("testType");
                            String create_time = jsonObject.getString("createTime");
                            String typeText = null;
                            if (!StringUtil.isEmpty(testType)) {
                                Integer type = Integer.parseInt(testType);
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
                            }
                            Map<String,Object> map2=new HashMap<String, Object>();
                            map2.put("rightTimes",rightTimes);
                            map2.put("wrongTimes",wrongTimes);
                            map2.put("typeText",typeText);
                            map2.put("create_time",create_time);
                            sl_data.add(map2);
                        }
                        sl_list.setAdapter(new SimpleAdapter(mContent,
                                sl_data, R.layout.xl_adapter_item,
                                new String[]{"rightTimes","wrongTimes","typeText","create_time"},
                                new int[]{R.id.text1,R.id.text2, R.id.text3,R.id.text4}));
                    }
                }else {
                    ToastUtil.showToast(mContent, "获取信息失败");
                }
            }
        });
    }
    void xt_lastThreeMonth() {
        HttpRequest.lastThreeMonthSugar(userId, 7, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Result result = JSONObject.parseObject(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONArray jsonArray = JSONArray.parseArray(result.getData().toString());
                        List<Map<String,Object>> sl_data=new ArrayList<Map<String,Object>>();
                        Map<String,Object> map=new HashMap<String, Object>();
                        map.put("sugarValue","血糖指数");
                        map.put("sugarType","血糖类型");
                        map.put("create_time","时间");
                        sl_data.add(map);
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String sugarValue = jsonObject.getString("sugarValue");
                            String testType = jsonObject.getString("sugarType");
                            String create_time = jsonObject.getString("createTime");
                            String typeText = null;
                            if (!StringUtil.isEmpty(testType)) {
                                Integer type = Integer.parseInt(testType);
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
                            }
                            Map<String,Object> map2=new HashMap<String, Object>();
                            map2.put("sugarValue",sugarValue);
                            map2.put("sugarType",typeText);
                            map2.put("create_time",create_time);
                            sl_data.add(map2);
                        }
                        xt_list.setAdapter(new SimpleAdapter(mContent,
                                sl_data, R.layout.xl_adapter_item2,
                                new String[]{"sugarValue","sugarType","create_time"},
                                new int[]{R.id.text1,R.id.text2,R.id.text3}));
                    }
                }else {
                    ToastUtil.showToast(mContent, "获取信息失败");
                }
            }
        });
    }
    void tz_lastThreeMonth() {
        HttpRequest.lastThreeMonthObesity(userId, 7, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Result result = JSONObject.parseObject(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONArray jsonArray = JSONArray.parseArray(result.getData().toString());
                        List<Map<String,Object>> sl_data=new ArrayList<Map<String,Object>>();
                        Map<String,Object> map=new HashMap<String, Object>();
                        map.put("obesityValue","体重值");
                        map.put("bmi","BMI指数");
                        map.put("obesityType","体重状态");
                        map.put("create_time","时间");
                        sl_data.add(map);
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String obesityValue = jsonObject.getString("obesityValue");
                            String bmi = jsonObject.getString("bmi");
                            String testType = jsonObject.getString("obesityType");
                            String create_time = jsonObject.getString("createTime");
                            String typeText = null;
                            if (!StringUtil.isEmpty(testType)) {
                                Integer type = Integer.parseInt(testType);
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
                            }
                            Map<String,Object> map2=new HashMap<String, Object>();
                            map2.put("obesityValue",obesityValue);
                            map2.put("bmi",bmi);
                            map2.put("obesityType",typeText);
                            map2.put("create_time",create_time);
                            sl_data.add(map2);
                        }
                        tz_list.setAdapter(new SimpleAdapter(mContent,
                                sl_data, R.layout.xl_adapter_item,
                                new String[]{"obesityValue","bmi","obesityType","create_time"},
                                new int[]{R.id.text1,R.id.text2, R.id.text3,R.id.text4}));
                    }
                }else {
                    ToastUtil.showToast(mContent, "获取信息失败");
                }
            }
        });
    }
}
