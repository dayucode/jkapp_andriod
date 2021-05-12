package zblibrary.demo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import zblibrary.demo.R;
import zblibrary.demo.activity.AboutActivity;
import zblibrary.demo.activity.SettingActivity;
import zblibrary.demo.adapter.MyPagerAdapter;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.ui.AlertDialog.OnDialogButtonClickListener;

/**
 * 设置fragment
 *
 * @author Lemon
 * @use new SettingFragment(),详细使用见.DemoFragmentActivity(initData方法内)
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<View> aList;
    private ArrayList<String> sList;
    private MyPagerAdapter mAdapter;
    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static HomeFragment createInstance() {
        return new HomeFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.home_fragment);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();

        aList.add(li.inflate(R.layout.view_page_xinlv, null, false));
        aList.add(li.inflate(R.layout.view_page_xueya, null, false));
        aList.add(li.inflate(R.layout.view_page_weight, null, false));
        aList.add(li.inflate(R.layout.view_page_sugar, null, false));
        aList.add(li.inflate(R.layout.view_page_shili, null, false));

        sList = new ArrayList<String>();
        sList.add("心率测试");
        sList.add("血压测试");
        sList.add("体重测试");
        sList.add("血糖测试");
        sList.add("视力测试");

        mAdapter = new MyPagerAdapter(aList, sList, context);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void initView() {//必须调用

    }

    @Override
    public void initData() {//必须调用

    }

    private void logout() {
        context.finish();
    }

    @Override
    public void initEvent() {//必须调用
    }
}