package zblibrary.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import zblibrary.demo.R;
import zblibrary.demo.activity.SweetsActivity;
import zblibrary.demo.heartrate.HeartRateActivity;
import zblibrary.demo.heartrate.ToastUtil;
import zblibrary.demo.model.result.Result;
import zblibrary.demo.model.result.ResultEnum;
import zblibrary.demo.util.HttpRequest;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.util.StringUtil;

public class MyNewsPagerAdapter extends PagerAdapter {
    private final String TAG = "MyNewsPagerAdapter";
    private ArrayList<View> viewLists;
    private ArrayList<String> titleLists;
    private Context mContent;
    ListView rd_list, ys_list, yd_list;
    List<Map<String, Object>> list_data_scope;
    List<Map<String, Object>> list_data2_scope;
    List<Map<String, Object>> list_data3_scope;

    public MyNewsPagerAdapter() {
    }

    public MyNewsPagerAdapter(ArrayList<View> viewLists, ArrayList<String> titleLists, Context context) {
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
        switch (position) {
            case 0:
                View view0 = viewLists.get(0);
                rd_list = view0.findViewById(R.id.list1);
                getTweetsList(1);
                rd_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String rd_id = (String) list_data_scope.get(position).get("id");
                        Intent intent = new Intent(mContent, SweetsActivity.class);//你要跳转的界面
                        intent.putExtra("id",rd_id);
                        mContent.startActivity(intent);
                    }
                });
                break;
            case 1:
                View view1 = viewLists.get(1);
                ys_list = view1.findViewById(R.id.list2);
                getTweetsList(2);
                ys_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String ys_id = (String) list_data2_scope.get(position).get("id");
                        Intent intent = new Intent(mContent, SweetsActivity.class);//你要跳转的界面
                        intent.putExtra("id",ys_id);
                        mContent.startActivity(intent);
                    }
                });
                break;
            case 2:
                View view2 = viewLists.get(2);
                yd_list = view2.findViewById(R.id.list3);
                getTweetsList(3);
                yd_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String ys_id = (String) list_data3_scope.get(position).get("id");
                        Intent intent = new Intent(mContent, SweetsActivity.class);//你要跳转的界面
                        intent.putExtra("id",ys_id);
                        mContent.startActivity(intent);
                    }
                });
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

    void getTweetsList(final int type) {
        HttpRequest.getTweetsListByType(type,8, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Result result = JSONObject.parseObject(resultJson, Result.class);
                if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    if (result.getData() != null) {
                        JSONArray jsonArray = JSONArray.parseArray(result.getData().toString());
                        List<Map<String, Object>> list_data = new ArrayList<>();
                        List<Map<String, Object>> list_data2 = new ArrayList<>();
                        List<Map<String, Object>> list_data3 = new ArrayList<>();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String title = jsonObject.getString("title");
                            String id = jsonObject.getString("id");
                            String img = jsonObject.getString("img");
                            String synopsis = jsonObject.getString("synopsis");
                            String type = jsonObject.getString("type");
                            if (!StringUtil.isEmpty(type)) {
                                if (type.equals("1")) {
                                    Map<String,Object> map=new HashMap<String, Object>();
                                    map.put("title", title);
                                    map.put("synopsis", synopsis);
                                    map.put("img", img);
                                    map.put("id", id);
                                    list_data.add(map);
                                }
                                if (type.equals("2")) {
                                    Map<String,Object> map=new HashMap<String, Object>();
                                    map.put("title", title);
                                    map.put("synopsis", synopsis);
                                    map.put("img", img);
                                    map.put("id", id);
                                    list_data2.add(map);
                                }
                                if (type.equals("3")) {
                                    Map<String,Object> map=new HashMap<String, Object>();
                                    map.put("title", title);
                                    map.put("synopsis", synopsis);
                                    map.put("img", img);
                                    map.put("id", id);
                                    list_data3.add(map);
                                }
                            }
                        }
                        if (type ==1){
                            rd_list.setAdapter(new SimpleAdapter(mContent,
                                    list_data, R.layout.news_item,
                                    new String[]{"title", "synopsis","img"},
                                    new int[]{R.id.title, R.id.context}));
                            list_data_scope = list_data;
                        }
                        if (type==2){
                            ys_list.setAdapter(new SimpleAdapter(mContent,
                                    list_data2, R.layout.news_item,
                                    new String[]{"title", "synopsis","img"},
                                    new int[]{R.id.title, R.id.context}));
                            list_data2_scope = list_data2;
                        }
                        if (type==3){
                            yd_list.setAdapter(new SimpleAdapter(mContent,
                                    list_data3, R.layout.news_item,
                                    new String[]{"title", "synopsis","img"},
                                    new int[]{R.id.title, R.id.context}));
                            list_data3_scope = list_data3;
                        }
                    }
                } else {
                    ToastUtil.showToast(mContent, "获取信息失败");
                }
            }
        });
    }
}
