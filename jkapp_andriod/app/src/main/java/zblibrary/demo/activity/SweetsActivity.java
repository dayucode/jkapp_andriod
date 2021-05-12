package zblibrary.demo.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import zblibrary.demo.R;
import zblibrary.demo.heartrate.ToastUtil;
import zblibrary.demo.model.result.Result;
import zblibrary.demo.model.result.ResultEnum;
import zblibrary.demo.util.HttpRequest;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.interfaces.OnHttpResponseListener;

public class SweetsActivity extends BaseActivity implements OnBottomDragListener {
    private static final String TAG = "SweetsActivity";
    private TextView mTitle, mContext;
    ImageView mImage;
    String id;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, SweetsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sweets_activity);
        Intent intent = getIntent();
        // 根据key获取value
        id = intent.getStringExtra("id");
        initView();
        initData();
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void initView() {
        mImage = findViewById(R.id.image);
        mTitle = findViewById(R.id.title);
        mContext = findViewById(R.id.context);
    }

    @Override
    public void initData() {
        getTweetsById(id);
    }

    @Override
    public void initEvent() {
    }

    void getTweetsById(String id) {
        HttpRequest.getTweetsById(id, 4, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Result result = JSONObject.parseObject(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONObject jsonObject = JSONObject.parseObject(result.getData().toString());
                        String title = jsonObject.getString("title");
                        String text = jsonObject.getString("text");
                        mTitle.setText(title);
                        mContext.setText("\u3000\u3000"+text);
                    }
                } else {
                    ToastUtil.showToast(SweetsActivity.this, "获取信息失败");
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
