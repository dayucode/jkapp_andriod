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

package zblibrary.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import zblibrary.demo.DEMO.DemoTabFragment;
import zblibrary.demo.R;
import zblibrary.demo.fragment.HomeFragment;
import zblibrary.demo.fragment.NewsFragment;
import zblibrary.demo.fragment.SettingFragment;
import zblibrary.demo.fragment.UserFragment;
import zblibrary.demo.fragment.UserListFragment;
import zblibrary.demo.fragment.UserRecyclerFragment;
import zuo.biao.library.base.BaseBottomTabActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;

/**
 * 应用主页
 *
 * @author Lemon
 * @use MainTabActivity.createIntent(...)
 */
public class MainTabActivity extends BaseBottomTabActivity implements OnBottomDragListener {
    	private static final String TAG = "MainTabActivity";
    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, MainTabActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_activity, this);
        initView();
        initData();
        initEvent();
    }
    @Override
    public void initView() {// 必须调用
        super.initView();
        //exitAnim = R.anim.bottom_push_out;
    }


    @Override
    protected int[] getTabClickIds() {
        return new int[]{R.id.llBottomTabTab,R.id.llBottomTabTab0, R.id.llBottomTabTab1, R.id.llBottomTabTab2};
    }

    @Override
    protected int[][] getTabSelectIds() {
        return new int[][]{
                new int[]{R.id.ivBottomTabTab,R.id.ivBottomTabTab0, R.id.ivBottomTabTab1, R.id.ivBottomTabTab2},//顶部图标
                new int[]{R.id.tvBottomTabTab,R.id.tvBottomTabTab0, R.id.tvBottomTabTab1, R.id.tvBottomTabTab2}//底部文字
        };
    }

    @Override
    public int getFragmentContainerResId() {
        return R.id.flMainTabFragmentContainer;
    }

    @Override
    protected Fragment getFragment(int position) {
        switch (position) {
            case 1:
                return HomeFragment.createInstance();
            case 2:
                return UserRecyclerFragment.createInstance();
            case 3:
                return UserFragment.createInstance();
            default:
                return NewsFragment.createInstance();
        }
    }
    private static final String[] TAB_NAMES = {"健康咨询","健康体检", "历史数据", "个人中心"};

    @Override
    protected void selectTab(int position) {
        //导致切换时闪屏，建议去掉BottomTabActivity中的topbar，在fragment中显示topbar
        //		rlBottomTabTopbar.setVisibility(position == 2 ? View.GONE : View.VISIBLE);

        tvBaseTitle.setText(TAB_NAMES[position]);
    }
    @Override
    public void initData() {// 必须调用
        super.initData();

    }
    @Override
    public void initEvent() {// 必须调用
        super.initEvent();

    }
    private long firstTime = 0;//第一次返回按钮计时

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    showShortToast("再按一次退出");
                    firstTime = secondTime;
                } else {//完全退出
                    moveTaskToBack(false);//应用退到后台
                    System.exit(0);
                }
                return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onDragBottom(boolean rightToLeft) {

    }
}