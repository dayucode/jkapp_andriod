/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package zblibrary.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.text.ParseException;
import java.util.Map;
import java.util.Objects;
import lombok.SneakyThrows;
import zblibrary.demo.R;
import zblibrary.demo.activity.ReportActivity;
import zblibrary.demo.activity.SweetsActivity;
import zblibrary.demo.heartrate.ToastUtil;
import zblibrary.demo.model.UserBean;
import zblibrary.demo.model.result.Result;
import zblibrary.demo.model.result.ResultEnum;
import zblibrary.demo.stepModule.StepActivity;
import zblibrary.demo.util.GsonUtil;
import zblibrary.demo.util.HttpRequest;
import zblibrary.demo.util.SPUtil;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.ui.AlertDialog.OnDialogButtonClickListener;

public class UserFragment extends BaseFragment implements OnClickListener, OnDialogButtonClickListener {
    private static final String TAG = "UserFragment";
    EditText nickname,phone,sex,age,height,email,introduce;
    View divider;
    LinearLayout submit,report,step;
    String userId;
    ImageView can_edit;
    boolean canChange = true;

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static UserFragment createInstance() {
        return new UserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.user_fragment);
        initView();
        initData();
        initData2();
        initEvent();
        return view;
    }

    @Override
    public void initView() {//必须调用
        nickname = findViewById(R.id.nickname);
        phone = findViewById(R.id.phone);
        sex = findViewById(R.id.sex);
        age = findViewById(R.id.age);
        height = findViewById(R.id.height);
        email = findViewById(R.id.email);
        introduce = findViewById(R.id.introduce);
        divider = findViewById(R.id.divider);
        submit = findViewById(R.id.submit);
        can_edit = findViewById(R.id.can_edit);
        report = findViewById(R.id.report);
        step = findViewById(R.id.step);

        nickname.setEnabled(false);
        phone.setEnabled(false);
        sex.setEnabled(false);
        age.setEnabled(false);
        height.setEnabled(false);
        email.setEnabled(false);
        introduce.setEnabled(false);
        divider.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        initData2();
    }

    private void initData2() {
        can_edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canChange){
                    nickname.setEnabled(true);
                    age.setEnabled(true);
                    height.setEnabled(true);
                    email.setEnabled(true);
                    introduce.setEnabled(true);
                    divider.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);
                    canChange = false;
                }else {
                    nickname.setEnabled(false);
                    age.setEnabled(false);
                    height.setEnabled(false);
                    email.setEnabled(false);
                    introduce.setEnabled(false);
                    divider.setVisibility(View.GONE);
                    submit.setVisibility(View.GONE);
                    canChange = true;
                }
            }
        });
        submit.setOnClickListener(new OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {
                UserBean userBean = new UserBean();
                userBean.setId(userId);
                userBean.setNickname(nickname.getText().toString().trim());
                userBean.setAge(Integer.parseInt(age.getText().toString().trim()));
                userBean.setHeight(Integer.parseInt(height.getText().toString().trim()));
                userBean.setEmail(email.getText().toString().trim());
                userBean.setIntroduce(introduce.getText().toString().trim());
                modifyUser(userBean);
            }
        });
        report.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                startActivity(intent);
            }
        });
        step.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StepActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {//必须调用
        userId = (String) SPUtil.getParam(Objects.requireNonNull(getActivity()), "userId", "");
        getUserInfo();
    }


    private void logout() {
        context.finish();
    }

    @Override
    public void initEvent() {//必须调用
        findViewById(R.id.setting_logout).setOnClickListener(this);
    }


    @Override
    public void onDialogButtonClick(int requestCode, boolean isPositive) {
        if (!isPositive) {
            return;
        }
        if (requestCode == 0) {
            SPUtil.clearAll(Objects.requireNonNull(getActivity()));
            logout();
        }
    }

    @Override
    public void onClick(View v) {//直接调用不会显示v被点击效果
        if (v.getId() == R.id.setting_logout) {
            new AlertDialog(context, "退出登录", "确定退出登录？", true, 0, this).show();
        }
    }

    private void getUserInfo() {
        HttpRequest.getUserInfo(userId, 1, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Log.i(TAG, "onHttpResponse:" + resultJson);
                Result result = JSONObject.parseObject(resultJson, Result.class);
                Log.i(TAG, "result:" + result);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONObject jsonObject = JSONObject.parseObject(result.getData().toString());
                        nickname.setText(jsonObject.getString("nickname"));
                        phone.setText(jsonObject.getString("id"));
                        sex.setText(jsonObject.getString("sex"));
                        age.setText(jsonObject.getString("age"));
                        email.setText(jsonObject.getString("email"));
                        height.setText(jsonObject.getString("height"));
                        introduce.setText(jsonObject.getString("introduce"));
                    }
                } else {
                    ToastUtil.showToast(getActivity(), "获取个人信息失败");
                }
            }
        });
    }

    void modifyUser(UserBean userBean) throws ParseException {
        if (userBean!=null){
            Map requestMap = JSON.parseObject(JSON.toJSONString(userBean), Map.class);
            HttpRequest.modifyUser(requestMap, 5, new OnHttpResponseListener() {
                @Override
                public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                    Log.i(TAG, "onHttpResponse:"+resultJson);
                    Result result = GsonUtil.json2Bean(resultJson, Result.class);
                    String message = result.getMessage();
                    ToastUtil.showToast(getActivity(), message);
                    nickname.setEnabled(false);
                    age.setEnabled(false);
                    height.setEnabled(false);
                    email.setEnabled(false);
                    introduce.setEnabled(false);
                    divider.setVisibility(View.GONE);
                    submit.setVisibility(View.GONE);
                }
            });
        }
    }
}