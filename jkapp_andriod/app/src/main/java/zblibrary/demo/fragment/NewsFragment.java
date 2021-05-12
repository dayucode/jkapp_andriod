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

import android.annotation.SuppressLint;
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
import zblibrary.demo.adapter.MyNewsPagerAdapter;
import zuo.biao.library.base.BaseFragment;

public class NewsFragment extends BaseFragment {
    private static final String TAG = "NewsFragment";

    public static NewsFragment createInstance() {
        return new NewsFragment();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.news_fragment);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        ArrayList<View> aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();

        aList.add(li.inflate(R.layout.view_page_news_one, null, false));
        aList.add(li.inflate(R.layout.view_page_news_two, null, false));
        aList.add(li.inflate(R.layout.view_page_news_three, null, false));

        ArrayList<String> sList = new ArrayList<String>();
        sList.add("热点");
        sList.add("饮食");
        sList.add("运动");
        MyNewsPagerAdapter mAdapter = new MyNewsPagerAdapter(aList, sList, context);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        return view;
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