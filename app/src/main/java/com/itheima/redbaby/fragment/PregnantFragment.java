package com.itheima.redbaby.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.itheima.redbaby.R;
import com.itheima.redbaby.bean.CategoryBean;
import com.itheima.redbaby.protocol.Api;
import com.itheima.redbaby.utils.LogUtils;
import com.itheima.redbaby.utils.PrefUtils;
import com.itheima.redbaby.utils.UIUtils;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author hanwei
 * @time 2016/12/6  16:38
 * @des
 */
public class PregnantFragment extends BaseFragment {
    private List<CategoryBean.CategoryBeans> mCategoryData;//分类中指定元素的数据
    private List<CategoryBean.CategoryBeans> mCategoryChildData = new ArrayList<>();
    @Bind(R.id.lv_home)
    ListView mLvHome;
    @Bind(R.id.iv_back)
    TextView mIvBack;
    @Bind(R.id.categorytitle)
    TextView mCategoryTitle;
    private int mParentId;

    /**
     * 初始化视图
     *
     * @return
     */
    @Override
    public View initViews() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_pregnant, null);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        getCatgoryData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        ClassFragment fragment = new ClassFragment();
        swichToChildFragment(fragment, true);
    }

    /**
     * ListView的适配器
     */
    class PregnantListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            LogUtils.s("hdfhskdfhskdfh"+mCategoryChildData.size()+"");
            return mCategoryChildData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_pregnant_listview, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mTvTitle.setText(mCategoryChildData.get(position).name);

            return convertView;
        }

        public class ViewHolder {
            @Bind(R.id.tv_title)
            TextView mTvTitle;


            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    //碎片切换方法
    public void swichToChildFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            //添加回退栈
            transaction.addToBackStack(fragment.getTag());
        }
        transaction.replace(R.id.fl_container, fragment);
        transaction.commit();
    }

    /**
     * 当页面销毁时取消绑定
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    /**
     * 获取分类的网络数据
     */
    public void getCatgoryData() {
        if (!mCategoryChildData.isEmpty()) {
            mCategoryChildData.clear();
        }
        RequestQueue queue = Volley.newRequestQueue(UIUtils.getContext());
        queue.add(Api.getCatgoryData(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                CategoryBean categoryBean = (CategoryBean) response;
                mCategoryData = categoryBean.category;
                mParentId = PrefUtils.getInt(mActivity, "parentId", 0);//得到分类页面传过来的parentId
                //得到分类主页面传过来点击的条目 显示在标题上
                String CateGoryName = PrefUtils.getString(mActivity, "CateGoryName", "");
                    mCategoryTitle.setText(CateGoryName);
                for (int i = 0; i < mCategoryData.size(); i++) {
                    if (mCategoryData.get(i).parentId == mParentId) {//根据父类传过来的id来找到其子类 并放到新的集合里面
                        LogUtils.s("分类数据------->" + mCategoryData.get(i).name);
                        mCategoryChildData.add(mCategoryData.get(i));
                    }
                }

                /*------------------------------    add begin   ----------------------------*/
                mLvHome.setAdapter(new PregnantListViewAdapter());
                mLvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (!mCategoryChildData.get(position).isLeafNode) {//判断是否有子类 有子类就跳转到子类分类页面 没有就显示商品列表
                            /*MomNurseFragment fragment = new MomNurseFragment();
                            swichToChildFragment(fragment, true);*/
                            mCategoryTitle.setText(mCategoryChildData.get(position).name);
                            PrefUtils.putInt(mActivity, "parentId", mCategoryChildData.get(position).id);
                            PrefUtils.putString(mActivity, "CateGoryName", mCategoryChildData.get(position).name);
                            mCategoryData.clear();
                            getCatgoryData();

                        } else {
                            ShoppingFragment fragment = new ShoppingFragment();
                            swichToChildFragment(fragment, true);
                        }
                    }
                });
                /*------------------------------    add end      ----------------------------*/
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                Toast.makeText(mActivity, "获取分类数据失败", Toast.LENGTH_SHORT).show();
            }
        }));
    }
}