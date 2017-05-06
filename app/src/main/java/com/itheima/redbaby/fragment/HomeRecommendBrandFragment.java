package com.itheima.redbaby.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.bean.RecommendBrandBean;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.protocol.Api;
import com.itheima.redbaby.utils.UIUtils;
import com.squareup.picasso.Picasso;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @author 王帅峰
 * @time 2016/12/6  21:38
 * @des 推荐品牌页面
 */
public class HomeRecommendBrandFragment extends BaseFragment {

    @Bind(R.id.base_title_btn_left)
    TextView mBaseTitleBtnLeft;
    @Bind(R.id.base_title_ibtn_tv)
    TextView mBaseTitleIbtnTv;
    @Bind(R.id.base_title_btn_right)
    TextView mBaseTitleBtnRight;
    @Bind(R.id.limit_lv)
    ListView mLimitLv;
    private List<RecommendBrandBean.BrandBean> mBrandBeanList;

    /**
     * 初始化视图
     *
     * @return
     */
    @Override
    public View initViews() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_limit, null);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        getBrandData();
        //将标题栏右侧按钮设置隐藏
        mBaseTitleBtnRight.setVisibility(View.GONE);
        //设置标题
        mBaseTitleIbtnTv.setText("推荐品牌");

    }

    /**
     * 当页面销毁时,解绑释放资源
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 返回按钮的点击事件,点击后返回上一页面
     */
    @OnClick(R.id.base_title_btn_left)
    public void onClick() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = FragmentInstanceManager.getInstance().getFragment(HomeFragment.class);
        transaction.replace(R.id.fl_container, fragment);
        transaction.commit();
    }




    /**
     * 适配器
     */
    class LimitAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (mBrandBeanList != null) {
                return mBrandBeanList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(UIUtils.getContext(), R.layout.item_recommentbrand_listview, null);
            ViewHolder holder = new ViewHolder(view);
            final RecommendBrandBean.BrandBean brandBean = mBrandBeanList.get(position);
            holder.mTvBrandTitle.setText(brandBean.key);
            int size = brandBean.value.size();
            if (size != 0) {
                for (int i = 0; i <= size / 3; i++) {
                    holder.mLinearLayouts.get(i).setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < brandBean.value.size(); i++) {
                    holder.mImageButtonList.get(i).setVisibility(View.VISIBLE);
                    Picasso.with(UIUtils.getContext()).load(RBConstants.URL_SERVER + brandBean.value.get(i).pic).into(holder.mImageButtonList.get(i));
                    final int finalI = i;
                    holder.mImageButtonList.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EventBus.getDefault().postSticky(brandBean.value.get(finalI));
                            swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(BrandPListFragment.class),true);
                        }
                    });
                }
            }
            return view;
        }
    }
    class ViewHolder {
        @Bind(R.id.tv_brand_title)
        TextView mTvBrandTitle;
        @Bind(R.id.ibtn_brand_icon0)
        ImageButton mIbtnBrandIcon0;
        @Bind(R.id.ibtn_brand_icon1)
        ImageButton mIbtnBrandIcon1;
        @Bind(R.id.ibtn_brand_icon2)
        ImageButton mIbtnBrandIcon2;
        @Bind(R.id.ll_brand_line1)
        LinearLayout mLlBrandLine1;
        @Bind(R.id.ibtn_brand_icon3)
        ImageButton mIbtnBrandIcon3;
        @Bind(R.id.ibtn_brand_icon4)
        ImageButton mIbtnBrandIcon4;
        @Bind(R.id.ibtn_brand_icon5)
        ImageButton mIbtnBrandIcon5;
        @Bind(R.id.ll_brand_line2)
        LinearLayout mLlBrandLine2;
        @Bind(R.id.ibtn_brand_icon6)
        ImageButton mIbtnBrandIcon6;
        @Bind(R.id.ibtn_brand_icon7)
        ImageButton mIbtnBrandIcon7;
        @Bind(R.id.ibtn_brand_icon8)
        ImageButton mIbtnBrandIcon8;
        @Bind(R.id.ll_brand_line3)
        LinearLayout mLlBrandLine3;
        List<ImageButton> mImageButtonList = new ArrayList<>();
        List<LinearLayout> mLinearLayouts = new ArrayList<>();

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            mImageButtonList.add(mIbtnBrandIcon0);
            mImageButtonList.add(mIbtnBrandIcon1);
            mImageButtonList.add(mIbtnBrandIcon2);
            mImageButtonList.add(mIbtnBrandIcon3);
            mImageButtonList.add(mIbtnBrandIcon4);
            mImageButtonList.add(mIbtnBrandIcon5);
            mImageButtonList.add(mIbtnBrandIcon6);
            mImageButtonList.add(mIbtnBrandIcon7);
            mImageButtonList.add(mIbtnBrandIcon8);
            mLinearLayouts.add(mLlBrandLine1);
            mLinearLayouts.add(mLlBrandLine2);
            mLinearLayouts.add(mLlBrandLine3);
        }
    }

    /**
     * 从网络中获取到数据
     */
    private void getBrandData() {
        Api.getBrandData(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                RecommendBrandBean recommendBrandBean = (RecommendBrandBean) response;
                mBrandBeanList = recommendBrandBean.brand;
                //设置适配器
                mLimitLv.setAdapter(new LimitAdapter());
                mLimitLv.setDividerHeight(0);
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                Toast.makeText(UIUtils.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
