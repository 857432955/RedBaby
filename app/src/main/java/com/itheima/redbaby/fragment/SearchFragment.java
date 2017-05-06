package com.itheima.redbaby.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.itheima.redbaby.R;
import com.itheima.redbaby.bean.SearchBean;
import com.itheima.redbaby.bean.SearchListBean;
import com.itheima.redbaby.protocol.Api;
import com.itheima.redbaby.utils.LogUtils;
import com.itheima.redbaby.utils.PrefUtils;
import com.itheima.redbaby.utils.UIUtils;
import com.itheima.redbaby.view.SearchExplandableListView;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhangRuxing on 2016-12-04.
 */

public class SearchFragment extends BaseFragment {
    @Bind(R.id.editSearchInfo)
    EditText editSearchInfo;
    @Bind(R.id.home_searchok)
    ImageButton homeSearchok;
    @Bind(R.id.base_title_btn_left)
    TextView baseTitleBtnLeft;
    @Bind(R.id.base_title_ibtn_tv)
    TextView baseTitleIbtnTv;
    @Bind(R.id.base_title_btn_right)
    TextView baseTitleBtnRight;

    private String[] mSearchData;//热门搜索数据
    private List<SearchListBean.ProductListBean> mSearchDataList;
    private View mSearchView;
    private String[] str_group_items = new String[]{"热门搜索", "搜索历史"};
    private String[][] str_child_items;
    private String[] mHistoryData;
    private SearchExplandableListView mId_expandablelist;

    //初始化布局
    @Override
    public View initViews() {
        mSearchView = View.inflate(mActivity, R.layout.fragment_search, null);
        mId_expandablelist = (SearchExplandableListView) mSearchView.findViewById(R.id.id_expandablelist);
        ButterKnife.bind(this, mSearchView);
        baseTitleBtnLeft.setVisibility(View.INVISIBLE);
        baseTitleBtnRight.setVisibility(View.INVISIBLE);
        baseTitleIbtnTv.setText("搜索");
//        baseTitleIbtnTv.setTextSize(22);
        homeSearchok.setOnClickListener(new Search());
//        mId_expandablelist.setAdapter(new ExpandInfoAdapter());

        return mSearchView;
    }

    @Override
    protected void initData() {
        getSearchData();
    }

    class ExpandInfoAdapter extends BaseExpandableListAdapter {
        @Override
        public int getGroupCount() {
            return str_group_items.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return str_child_items[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return str_group_items[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ImageView imageView;
            TextView txt_group;
            if (null == convertView) {
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.search_group_item, null);
            }

            /*判断是否group张开，来分别设置ImageView背景图*/
            imageView = (ImageView) convertView.findViewById(R.id.id_group_img);
            if (isExpanded) {
                imageView.setImageResource(R.drawable.arrow);
            } else {
                imageView.setImageResource(R.drawable.arrow1);
            }

            txt_group = (TextView) convertView.findViewById(R.id.id_group_txt);
            txt_group.setText(str_group_items[groupPosition]);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final TextView txt_child;
            if (null == convertView) {
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.search_child_item, null);
            }
             /*判断是否是最后一项，最后一项 红线则不显示*/
            View view = convertView.findViewById(R.id.view);
            if (isLastChild) {
                view.setVisibility(View.INVISIBLE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
            txt_child = (TextView) convertView.findViewById(R.id.id_child_txt);
            txt_child.setText(str_child_items[groupPosition][childPosition]);
            txt_child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = (String) txt_child.getText();//获取当前点击条目的数据
                    saveSearchData(name);//保存搜索数据到本地

//                    Toast.makeText(mActivity, "点击了----->" + name, Toast.LENGTH_SHORT).show();

                    //跳转到商品列表
                    if (name != null && name != "") {
                        skipGoods(name);
                    }

                }
            });
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    /**
     * 跳转到商品列表
     */
    private void skipGoods(String searchData) {
        PrefUtils.putString(mActivity, "searchData", searchData);
        CommodityFragment fragment = new CommodityFragment();
        swichToChildFragment(fragment, true);
    }

    //搜索按钮点击事件
    class Search implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String name = editSearchInfo.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(mActivity, "请输入关键字", Toast.LENGTH_SHORT).show();
            } else {
                saveSearchData(name);
//                Toast.makeText(mActivity, name, Toast.LENGTH_SHORT).show();
                skipGoods(name);
            }
        }
    }

    /**
     * 保存搜索数据到本地
     */
    private void saveSearchData(String name) {
        String localData = PrefUtils.getString(mActivity, "search", "");
        if (localData.isEmpty()) {
            PrefUtils.putString(mActivity, "search", name);//将搜索历史存入到本地
        } else {
            //判断本地是否包含将要存入的字符串，如果包含就不存入
            if (!("," + localData + ",").contains("," + name + ",") && name != "") {
                PrefUtils.putString(mActivity, "search", localData + "," + name);//将本地搜索历史+搜索内容存入到本地
            }
        }
    }

    /**
     * 获取热门搜索网络数据据
     */
    public void getSearchData() {
        RequestQueue queue = Volley.newRequestQueue(UIUtils.getContext());
        queue.add(Api.getSearchData(new HttpLoader.HttpListener() {

            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                SearchBean searchBean = (SearchBean) response;
                mSearchData = searchBean.searchKeywords;

                /*------------------------------    add begin   ----------------------------*/
                String[] emptyData = {""};
                String localSearchData = PrefUtils.getString(mActivity, "search", "");
                if (localSearchData.isEmpty()) {
                    mHistoryData = new String[]{""};
                } else {
                    //从本地获取搜索历史
                    mHistoryData = localSearchData.split(",");
                }
                if (mSearchData == null) {
                    str_child_items = new String[][]{emptyData, mHistoryData};
                } else {
                    str_child_items = new String[][]{mSearchData, mHistoryData};
                }
                /*------------------------------    add end      ----------------------------*/

                mId_expandablelist.setAdapter(new ExpandInfoAdapter());
                mId_expandablelist.expandGroup(0);//设置默认展开第一组数据
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                Toast.makeText(mActivity, "获取热门搜索数据失败", Toast.LENGTH_SHORT).show();
            }
        }));
    }
}
