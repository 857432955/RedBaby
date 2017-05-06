package com.itheima.redbaby.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.itheima.redbaby.R;
import com.itheima.redbaby.bean.CategoryBean;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.protocol.Api;
import com.itheima.redbaby.utils.PrefUtils;
import com.itheima.redbaby.utils.UIUtils;
import com.squareup.picasso.Picasso;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author hanwei
 * @time 2016/12/6
 * @des
 */
public class ClassFragment extends BaseFragment {

    @Bind(R.id.lv_home)
    ListView mLvHome;
    @Bind(R.id.base_title_btn_left)
    TextView baseTitleBtnLeft;
    @Bind(R.id.base_title_ibtn_tv)
    TextView baseTitleIbtnTv;
    @Bind(R.id.base_title_btn_right)
    TextView baseTitleBtnRight;

    private List<CategoryBean.CategoryBeans> mCategoryData;//分类数据

    /**
     * 初始化视图
     *
     * @return
     */
    @Override
    public View initViews() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_class, null);
        ButterKnife.bind(this, view);
        baseTitleBtnLeft.setVisibility(View.INVISIBLE);
        baseTitleBtnRight.setVisibility(View.INVISIBLE);
        baseTitleIbtnTv.setText("分类浏览");
//        baseTitleIbtnTv.setTextSize(22);
        return view;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        getCatgoryData();
    }

    /**
     * ListView的适配器
     */
    class ClassListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            int count = 0;
            for (int i = 0; i < mCategoryData.size(); i++) {
                if (mCategoryData.get(i).parentId == 0) {
                    count++;
                }
            }
            return count;
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
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_class_listview, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Picasso.with(mActivity).load(RBConstants.URL_SERVER + mCategoryData.get(position).pic).into(holder.mIvIcon);
            holder.mTvTitle.setText(mCategoryData.get(position).name);
            holder.mTvSubtitle.setText(mCategoryData.get(position).tag);
            return convertView;
        }

        public class ViewHolder {
            @Bind(R.id.iv_icon)
            ImageView mIvIcon;
            @Bind(R.id.tv_title)
            TextView mTvTitle;
            @Bind(R.id.tv_subtitle)
            TextView mTvSubtitle;

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
        RequestQueue queue = Volley.newRequestQueue(UIUtils.getContext());
        queue.add(Api.getCatgoryData(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                CategoryBean categoryBean = (CategoryBean) response;
                mCategoryData = categoryBean.category;

                /*------------------------------    add begin   ----------------------------*/

                mLvHome.setAdapter(new ClassListViewAdapter());
                mLvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (!mCategoryData.get(position).isLeafNode) {
                            PrefUtils.putInt(mActivity, "parentId", mCategoryData.get(position).id);
                            PrefUtils.putString(mActivity, "CateGoryName", mCategoryData.get(position).name);
                            PregnantFragment fragment = new PregnantFragment();
                            swichToChildFragment(fragment, false);
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

