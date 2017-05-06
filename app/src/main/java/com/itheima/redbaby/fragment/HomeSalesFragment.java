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
import com.itheima.redbaby.bean.TopicResponse;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.protocol.Api;
import com.itheima.redbaby.utils.LogUtils;
import com.itheima.redbaby.utils.UIUtils;
import com.squareup.picasso.Picasso;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @author 王帅峰
 * @time 2016/12/6  21:38
 * @des 促销快报页面
 */
public class HomeSalesFragment extends BaseFragment {
    @Bind(R.id.base_title_btn_left)
    TextView mBaseTitleBtnLeft;
    @Bind(R.id.base_title_ibtn_tv)
    TextView mBaseTitleIbtnTv;
    @Bind(R.id.base_title_btn_right)
    TextView mBaseTitleBtnRight;
    @Bind(R.id.limit_lv)
    ListView mLimitLv;
    private List<TopicResponse.TopicBean> mTopicBeanList;

    /**
     * 初始化视图
     *
     * @return
     */
    @Override
    public View initViews() {
        getTopicData();
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_limit, null);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        //将标题栏右侧按钮设置隐藏
        mBaseTitleBtnRight.setVisibility(View.GONE);
        //设置标题
        mBaseTitleIbtnTv.setText("促销快报");

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
            if (mTopicBeanList != null) {
                return mTopicBeanList.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            SalesViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_sales_listview, null);
                holder = new SalesViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (SalesViewHolder) convertView.getTag();
            }
            Picasso.with(UIUtils.getContext()).load(RBConstants.URL_SERVER + mTopicBeanList.get(position).pic).into(holder.mIvItemSalesIcon);
            holder.mTvItemSalesTitle.setText(mTopicBeanList.get(position).name);
            return convertView;
        }
    }

    class SalesViewHolder {
        @Bind(R.id.tv_item_sales_title)
        TextView mTvItemSalesTitle;
        @Bind(R.id.iv_item_sales_icon)
        ImageView mIvItemSalesIcon;

        SalesViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 从网络中获取到数据
     */
    private void getTopicData() {
        Api.getTopicData(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                TopicResponse topicResponse = (TopicResponse) response;
                mTopicBeanList = topicResponse.topic;
                //设置适配器
                mLimitLv.setAdapter(new LimitAdapter());
                mLimitLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        EventBus.getDefault().postSticky(mTopicBeanList.get(position));
                        swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(TopicPListFragment.class),true);
                    }
                });
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                Toast.makeText(UIUtils.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
