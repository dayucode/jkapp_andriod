package zblibrary.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Objects;

import zblibrary.demo.R;
import zblibrary.demo.heartrate.ToastUtil;
import zblibrary.demo.model.result.Result;
import zblibrary.demo.model.result.ResultEnum;
import zblibrary.demo.util.HttpRequest;
import zblibrary.demo.util.SPUtil;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.util.JSON;

public class ReportActivity extends BaseActivity implements OnBottomDragListener {
    private static final String TAG = "SweetsActivity";
    private TextView xy1, xy2, xy3, xy4, xy5, xy6;
    private TextView xt1, xt2, xt3, xt4;
    private TextView sl1, sl2, sl3, sl4, sl5;
    private TextView xl1, xl2, xl3;
    private TextView tz1, tz2, tz3, tz4;
    String userId;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, ReportActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);
        initView();
        initData();
    }

    @Override
    public void initView() {
        xy1 = findViewById(R.id.xy1);
        xy2 = findViewById(R.id.xy2);
        xy3 = findViewById(R.id.xy3);
        xy4 = findViewById(R.id.xy4);
        xy5 = findViewById(R.id.xy5);
        xy6 = findViewById(R.id.xy6);

        xt1 = findViewById(R.id.xt1);
        xt2 = findViewById(R.id.xt2);
        xt3 = findViewById(R.id.xt3);
        xt4 = findViewById(R.id.xt4);

        sl1 = findViewById(R.id.sl1);
        sl2 = findViewById(R.id.sl2);
        sl3 = findViewById(R.id.sl3);
        sl4 = findViewById(R.id.sl4);
        sl5 = findViewById(R.id.sl5);

        xl1 = findViewById(R.id.xl1);
        xl2 = findViewById(R.id.xl2);
        xl3 = findViewById(R.id.xl3);

        tz1 = findViewById(R.id.tz1);
        tz2 = findViewById(R.id.tz2);
        tz3 = findViewById(R.id.tz3);
        tz4 = findViewById(R.id.tz4);
    }

    @Override
    public void initData() {
        userId = (String) SPUtil.getParam(Objects.requireNonNull(getActivity()), "userId", "");
        getUserReport(userId);
    }

    @Override
    public void initEvent() {
    }

    void getUserReport(String userId) {
        HttpRequest.getUserReport(userId, 4, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Result result = JSONObject.parseObject(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONObject json = JSONObject.parseObject(result.getData().toString());
                        JSONArray pressureReport = json.getJSONArray("pressureReport");
                        for (Object o:pressureReport){
                            String count = JSON.parseObject(o).getString("count");
                            String type = JSON.parseObject(o).getString("type");
                            if (type.equals("0")){
                                xy1.setText(count);
                            }
                            if (type.equals("1")){
                                xy2.setText(count);
                            }
                            if (type.equals("2")){
                                xy3.setText(count);
                            }
                            if (type.equals("3")){
                                xy4.setText(count);
                            }
                            if (type.equals("4")){
                                xy5.setText(count);
                            }
                            if (type.equals("5")){
                                xy6.setText(count);
                            }
                        }
                        JSONArray heartRateReport = json.getJSONArray("heartRateReport");
                        for (Object o:heartRateReport){
                            String count = JSON.parseObject(o).getString("count");
                            String type = JSON.parseObject(o).getString("type");
                            if (type.equals("0")){
                                xl1.setText(count);
                            }
                            if (type.equals("1")){
                                xl2.setText(count);
                            }
                            if (type.equals("2")){
                                xl3.setText(count);
                            }
                        }
                        JSONArray eyeTestReport = json.getJSONArray("eyeTestReport");
                        for (Object o:eyeTestReport){
                            String count = JSON.parseObject(o).getString("count");
                            String type = JSON.parseObject(o).getString("type");
                            if (type.equals("0")){
                                sl1.setText(count);
                            }
                            if (type.equals("1")){
                                sl2.setText(count);
                            }
                            if (type.equals("2")){
                                sl3.setText(count);
                            }
                            if (type.equals("3")){
                                sl4.setText(count);
                            }
                            if (type.equals("4")){
                                sl5.setText(count);
                            }
                        }
                        JSONArray obesityReport = json.getJSONArray("obesityReport");
                        for (Object o:obesityReport){
                            String count = JSON.parseObject(o).getString("count");
                            String type = JSON.parseObject(o).getString("type");
                            if (type.equals("0")){
                                tz1.setText(count);
                            }
                            if (type.equals("1")){
                                tz2.setText(count);
                            }
                            if (type.equals("2")){
                                tz3.setText(count);
                            }
                            if (type.equals("3")){
                                tz4.setText(count);
                            }
                        }
                        JSONArray sugarReport = json.getJSONArray("sugarReport");
                        for (Object o:sugarReport){
                            String count = JSON.parseObject(o).getString("count");
                            String type = JSON.parseObject(o).getString("type");
                            if (type.equals("0")){
                                xt1.setText(count);
                            }
                            if (type.equals("1")){
                                xt2.setText(count);
                            }
                            if (type.equals("2")){
                                xt3.setText(count);
                            }
                            if (type.equals("3")){
                                xt4.setText(count);
                            }
                        }
                        json.getJSONObject("userInfo");

                    }
                } else {
                    ToastUtil.showToast(ReportActivity.this, "获取信息失败");
                }
            }
        });
    }

    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {

            return;
        }

        finish();
    }

}
