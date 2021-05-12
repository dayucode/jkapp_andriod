package zblibrary.demo.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.SinglePicker;
import zblibrary.demo.R;
import zblibrary.demo.heartrate.ToastUtil;
import zblibrary.demo.model.GoodsCategory;
import zblibrary.demo.model.UserBean;
import zblibrary.demo.model.result.Result;
import zblibrary.demo.model.result.ResultEnum;
import zblibrary.demo.util.GsonUtil;
import zblibrary.demo.util.HttpRequest;
import zblibrary.demo.util.SPUtil;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.interfaces.OnHttpResponseListener;

/**
 * 关于界面
 *
 * @author Lemon
 */
public class RegisterActivity extends BaseActivity implements OnClickListener, OnBottomDragListener {
    private static final String TAG = "RegisterActivity";
    private TextView height;
    private EditText phone, password;
    private CheckBox sex;
    String mHeight,mSex;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity, this);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        height = findViewById(R.id.height);
        sex = findViewById(R.id.sex);
        sex.isChecked();
    }

    @Override
    public void initData() {
        phone.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (phone.getText().length() > 0) {
                    String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
                    if (phone.getText().length() != 11) {
                        phone.setError("手机号应为11位数");
                    }
                    if (!phone.getText().toString().matches(regex)) {
                        phone.setError("输入正确的手机号");
                    }
                    if (phone.getText().length() == 11) {
                        verifyAccount();
                    }
                } else {
                    phone.setHint("手机号");
                }
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (password.getText().length() > 0) {
                    String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
                    boolean matches1 = password.getText().length() < 8 || password.length() > 16;
                    boolean matches2 = password.getText().toString().matches(regex);
                    if (matches1 || !matches2) {
                        password.setError("长度8-16位由字母和数字组成");
                    }
                } else {
                    password.setHint("密码");
                }
            }
        });
        mHeight = height.getText().toString().trim();
        if (sex.isChecked()){
            mSex = "男";
        }else {
            mSex = "女";
        }

    }

    @Override
    public void initEvent() {
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
        findViewById(R.id.height_down).setOnClickListener(this);
    }

    @Override
    public void onDragBottom(boolean rightToLeft) {
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                startActivity(LoginActivity.createIntent(RegisterActivity.this));
                finish();
                break;
            case R.id.register:
                if (!valid()){
                    return;
                }
                register();
                break;
            case R.id.height_down:
                NumberPicker picker3 = new NumberPicker(this);
                picker3.setWidth(picker3.getScreenWidthPixels());
                picker3.setCycleDisable(false);
                picker3.setDividerVisible(false);
                picker3.setOffset(2);//偏移量
                picker3.setRange(0, 300, 1);//数字范围
                picker3.setSelectedItem(172);
                picker3.setLabel("厘米");
                picker3.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNumberPicked(int index, Number item) {
                        height.setText(item.intValue() + "");
                    }
                });
                picker3.show();
                break;
            default:
                break;
        }
    }
    private boolean valid(){
        boolean valid = true;
        String text = "";
        if(phone.getText().toString().isEmpty()){
            text = "手机号";
            valid = false;
        }
        if(password.getText().toString().isEmpty()){
            text = "密码";
            valid = false;
        }
        if(height.getText().toString().isEmpty()){
            text = "身高";
            valid = false;
        }
        if (!valid){
            ToastUtil.showToast(RegisterActivity.this,text+"为空");
        }
        return valid;
    }
    private void register(){
        UserBean userBean = new UserBean();
        userBean.setId(phone.getText().toString());
        userBean.setPassword(password.getText().toString());
        userBean.setSex(sex.isChecked()?"女":"男");
        userBean.setHeight(Integer.parseInt(height.getText().toString()));
        userBean.setIntroduce("等着，总有一天你的名字会出现在我家户口上。");
        userBean.setNickname(phone.getText().toString());


        Log.i(TAG, "register: ==============================="+userBean);

        Map requestMap = JSON.parseObject(JSON.toJSONString(userBean), Map.class);
        HttpRequest.register(requestMap, 0, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Log.i(TAG, "onHttpResponse:"+resultJson);
                Result result = GsonUtil.json2Bean(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())){
                    ToastUtil.showToast(RegisterActivity.this,"注册成功");
                    startActivity(LoginActivity.createIntent(RegisterActivity.this));
                    finish();
                }else {
                    ToastUtil.showToast(RegisterActivity.this,ResultEnum.LOGIN_FAIL.getDesc());
                }
            }
        });
    }
    private void verifyAccount(){
        HttpRequest.verifyPhone(phone.getText().toString(), 0, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Log.i(TAG, "onHttpResponse:"+resultJson);
                Result result = GsonUtil.gsonToBean(resultJson, Result.class);
                Log.i(TAG, "result:"+result);
                if (result.getCode().equals("1")){
                    phone.setError(result.getMessage());
                }
            }
        });
    }

}
