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
public class MomNurseFragment extends BaseFragment {


    @Bind(R.id.lv_home)
    ListView mLvHome;
    public String[] mLvTitle = new String[]{"面部护理(这个可以点)", "洁牙护齿", "身体护理", "卫生巾",
            "产褥/护理垫", "眼部护理", "洗手液", "洗护套装"};
    @Bind(R.id.iv_back)
    TextView mIvBack;

    /**
     * 初始化视图
     *
     * @return
     */
    @Override
    public View initViews() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_momnurse, null);
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
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://面部护理"
                        CommodityFragment fragment = new CommodityFragment();
                        swichToChildFragment(fragment, true);
                        break;
                    case 1://"洁牙护齿"
                        break;
                    case 2://"身体护理"
                        break;
                    case 3://"卫生巾"
                        break;
                    case 4://产褥/护理垫"
                        break;
                    case 5://"眼部护理"
                        break;
                    case 6://"洗手液"
                        break;
                    case 7://"洗护套装"
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
        PregnantFragment fragment = new PregnantFragment();
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
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_momnurse_listview, null);
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

