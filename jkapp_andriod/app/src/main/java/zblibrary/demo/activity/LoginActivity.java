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
import android.widget.EditText;

import java.util.Objects;

import zblibrary.demo.R;
import zblibrary.demo.heartrate.ToastUtil;
import zblibrary.demo.model.result.Result;
import zblibrary.demo.model.result.ResultEnum;
import zblibrary.demo.util.GsonUtil;
import zblibrary.demo.util.HttpRequest;
import zblibrary.demo.util.SPUtil;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.util.StringUtil;

/**
 * 关于界面
 * @author Lemon
 */
public class LoginActivity extends BaseActivity implements OnClickListener,OnBottomDragListener {
    private static final String TAG = "LoginActivity";
    EditText phone,password;
    String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
    /**
     * 启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity, this);
        isLogin();
        initView();
        initData();
        initEvent();
    }
    private void isLogin() {
        String userId = (String) SPUtil.getParam(Objects.requireNonNull(getActivity()), "userId", "");
        if (!StringUtil.isEmpty(userId)){
            regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
            if (userId.length()==11 && userId.matches(regex)) {
                startActivity(MainTabActivity.createIntent(context).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(R.anim.bottom_push_in, R.anim.hold);
                enterAnim = exitAnim = R.anim.null_anim;
                finish();
            }
        }
    }

    @Override
    public void initView() {
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);

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
                    if (phone.getText().length() != 11) {
                        phone.setError("手机号应为11位数");
                    }
                    if (!phone.getText().toString().matches(regex)) {
                        phone.setError("输入正确的手机号");
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
    }
    @Override
    public void initEvent() {
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
    }
    @Override
    public void onDragBottom(boolean rightToLeft) {
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                valid();
                login();
                break;
            case R.id.register:
                startActivity(RegisterActivity.createIntent(LoginActivity.this));
                finish();
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
        if (!valid){
            ToastUtil.showToast(LoginActivity.this,text+"为空");
        }
        return valid;
    }
    private void login(){
        HttpRequest.login(phone.getText().toString(), password.getText().toString(),1, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Log.i(TAG, "onHttpResponse:"+resultJson);
                Result result = GsonUtil.gsonToBean(resultJson, Result.class);
                Log.i(TAG, "result:"+result);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())){
                    SPUtil.clear(LoginActivity.this,"userId");
                    SPUtil.setParam(LoginActivity.this,"userId",phone.getText().toString());
                    ToastUtil.showToast(LoginActivity.this,ResultEnum.LOGIN_SUCCESS.getDesc());
                    startActivity(MainTabActivity.createIntent(context).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    overridePendingTransition(R.anim.bottom_push_in, R.anim.hold);
                    enterAnim = exitAnim = R.anim.null_anim;
                    finish();
                }else {
                    ToastUtil.showToast(LoginActivity.this,ResultEnum.LOGIN_FAIL.getDesc());
                }
            }
        });
    }
}
