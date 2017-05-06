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

import com.itheima.redbaby.R;
import com.itheima.redbaby.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author hanwei
 * @time 2016/12/6  16:38
 * @des
 */
public class FiltrateBrandFragment extends BaseFragment {


    @Bind(R.id.lv_home)
    ListView mLvHome;
    public String[] mLvTitle = new String[]{"雅培", "美素", "贝因美", "海王",
            "美素", "贝因美"};
    @Bind(R.id.iv_back)
    TextView mIvBack;

    /**
     * 初始化视图
     *
     * @return
     */
    @Override
    public View initViews() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_filtratebrand, null);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mLvHome.setAdapter(new PregnantListViewAdapter());
        mLvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private CommodityFragment mFragment;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://"雅培",
                        mFragment = new CommodityFragment();
                        swichToChildFragment(mFragment, true);
                        break;
                    case 1://"美素",
                        mFragment = new CommodityFragment();
                        swichToChildFragment(mFragment, true);
                        break;
                    case 2://"贝因美",
                        mFragment = new CommodityFragment();
                        swichToChildFragment(mFragment, true);
                        break;
                    case 3://"海王",
                        mFragment = new CommodityFragment();
                        swichToChildFragment(mFragment, true);
                        break;
                    case 4:// "美素",
                        mFragment = new CommodityFragment();
                        swichToChildFragment(mFragment, true);
                        break;
                    case 5://"贝因美"
                        mFragment = new CommodityFragment();
                        swichToChildFragment(mFragment, true);
                        break;
                    default:
                        break;

                }
            }
        });

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
        FiltrateFragment fragment = new FiltrateFragment();
        swichToChildFragment(fragment, true);
    }

    /**
     * ListView的适配器
     */
    class PregnantListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mLvTitle.length;
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
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_filtratebrand_listview, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mTvTitle.setText(mLvTitle[position]);

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
}

