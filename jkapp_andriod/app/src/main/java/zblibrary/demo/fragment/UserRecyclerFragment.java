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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import zblibrary.demo.R;
import zblibrary.demo.adapter.MyPagerAdapter;
import zblibrary.demo.adapter.MyPagerTwoAdapter;
import zuo.biao.library.base.BaseFragment;

public class UserRecyclerFragment extends BaseFragment {
    private static final String TAG = "UserListFragment";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<View> aList;
    private ArrayList<String> sList;
    private MyPagerTwoAdapter mAdapter;

    public static UserRecyclerFragment createInstance() {
        return new UserRecyclerFragment();
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

        aList.add(li.inflate(R.layout.view_page_xinlv_history, null, false));
        aList.add(li.inflate(R.layout.view_page_xueya_history, null, false));
        aList.add(li.inflate(R.layout.view_page_shili_history, null, false));
        aList.add(li.inflate(R.layout.view_page_sugar_history, null, false));
        aList.add(li.inflate(R.layout.view_page_weight_history, null, false));

        sList = new ArrayList<String>();
        sList.add("心率记录");
        sList.add("血压记录");
        sList.add("视力记录");
        sList.add("血糖记录");
        sList.add("体重记录");
        mAdapter = new MyPagerTwoAdapter(aList, sList, context);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabMode(TabLayout.SCREEN_STATE_OFF);
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