package com.itheima.redbaby.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.base.HomePictures;
import com.itheima.redbaby.bean.HomeBean;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.protocol.Api;
import com.itheima.redbaby.utils.LogUtils;
import com.itheima.redbaby.utils.UIUtils;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author 王帅峰
 * @time 2016/12/4  15:22
 * @des 首页的Fragment
 */
public class HomeFragment extends BaseFragment {
    @Bind(R.id.lv_home)
    ListView mLvHome;
    @Bind(R.id.ll_home)
    LinearLayout mLlHome;
    private int[] mLvDrawables = new int[]{R.drawable.home_classify_01, R.drawable.home_classify_02, R.drawable.home_classify_03, R.drawable.home_classify_04, R.drawable.home_classify_05};
    private String[] mLvDes = new String[]{"限时抢购", "促销快报", "新品上架", "热门单品", "推荐品牌"};
    private FragmentManager mFragmentManager;
    private List<HomeBean.HomeTopicBean> mHomeTopicList;

    /**
     * 初始化视图
     *
     * @return
     */
    @Override
    public View initViews() {
        getHomeTopicData();
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

        mFragmentManager = getActivity().getSupportFragmentManager();
        mLvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = position - mLvHome.getHeaderViewsCount();
                switch (position) {
                    case 0:
                        swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(HomeLimitFragment.class),true);
                        break;
                    case 1:
                        swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(HomeSalesFragment.class),true);
                        break;
                    case 2:
                        swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(HomeNewProductsFragment.class),true);
                        break;
                    case 3:
                        swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(HomeHotProductsFragment.class),true);
                        break;
                    case 4:
                        swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(HomeRecommendBrandFragment.class),true);
                        break;
                }
            }
        });
        //搜索框设置点击事件,切换到搜索界面
        mLlHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup rg = (RadioGroup) getActivity().findViewById(R.id.rg_rb);
                rg.check(R.id.rb_search);
            }
        });
    }

    /**
     * 从网络中获取到数据
     */
    private void getHomeTopicData() {
        Api.getHomeData(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                HomeBean homeBean = (HomeBean) response;
                mHomeTopicList = homeBean.homeTopic;
                HomePictures pictures = new HomePictures();
                final View header = pictures.initView();
                pictures.setData(mHomeTopicList);
                if (mLvHome != null) {
                    mLvHome.addHeaderView(header);
                    mLvHome.setAdapter(new HomeListViewAdapter());
                }
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                Toast.makeText(UIUtils.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * ListView的适配器
     */
    class HomeListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 5;
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
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_home_listview, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mIvLimit.setImageResource(mLvDrawables[position]);
            holder.mTvDes.setText(mLvDes[position]);
            return convertView;
        }

    }

    class ViewHolder {
        @Bind(R.id.iv_limit)
        ImageView mIvLimit;
        @Bind(R.id.tv_des)
        TextView mTvDes;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 当页面销毁时取消绑定
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
