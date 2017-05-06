package com.itheima.redbaby.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.bean.IndentEventBusbean;
import com.itheima.redbaby.bean.MyIndentListBean;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.utils.UIUtils;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;
import org.senydevpkg.utils.ALog;

import de.greenrobot.event.EventBus;

/**
 * Created by lenovo on 2016/12/5.
 */
public class IndentFragment extends BaseFragment implements View.OnClickListener {

    private static final int STATE_EMPTY = 0;
    private static final int STATE_FULL = 1;
    private int mCurState = STATE_FULL;//默认是有数据的情况

    private View mFullView;
    private View mEmptyView;
    private TextView mHead_indent_back;
    private ListView mListView;
    private RadioButton mNow_month;
    private RadioButton mBefore_month;
    private RadioButton mCancle_indent;
    private RadioGroup mInident_full_rg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ALog.e("onCreate");
    }

    @Override
    public View initViews() {
        mFullView = View.inflate(UIUtils.getContext(), R.layout.pager_indent_full, null);
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_indent_empty, null);
        mInident_full_rg = (RadioGroup) mFullView.findViewById(R.id.inident_full_rg);
        mListView = (ListView) mFullView.findViewById(R.id.lv_indent_list);
        ALog.e("onCreateView");
       // mInident_full_rg.check(R.id.now_month);
        ALog.e("onCreateView");
        requestNetData("1");
        ALog.e("onCreateView");
        mInident_full_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.now_month:
                        requestNetData("1");
                        break;
                    case R.id.before_month:
                        requestNetData("2");
                        break;
                    case R.id.cancle_indent:
                        requestNetData("3");
                        break;
                }
            }
        });
       /*  mNow_month = (RadioButton) mFullView.findViewById(R.id.now_month);
       mBefore_month = (RadioButton) mFullView.findViewById(R.id.before_month);
        mCancle_indent = (RadioButton) mFullView.findViewById(R.id.cancle_indent);*/
        mHead_indent_back = (TextView) mFullView.findViewById(R.id.head_indent_back);
        mHead_indent_back.setOnClickListener(this);
        /*-------------监听listview条目的点击事件实现跳转到订单详情----------*/
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String orderId = mMyIndentListBean.orderList.get(position).orderId;
                IndentEventBusbean indentEventBusbean = new IndentEventBusbean();
                indentEventBusbean.orderId = orderId;
                swichToChildFragment(FragmentInstanceManager.getInstance().getFragment(OrderDetailsFragment.class), true);
                EventBus.getDefault().postSticky(indentEventBusbean);
                ALog.e("-------indentEventBusbean-------------------" + indentEventBusbean.orderId);
               /* EventBus.getDefault().registerSticky(this);
                EventBus.getDefault().unregister();*/
                ALog.e("--------------------------" + orderId);
                mInident_full_rg.check(R.id.now_month);

            }
        });

//        /*-------------至今没用----------*/
//        if (mCurState == STATE_EMPTY) {
//            return mEmptyView;
//        }
         /*----------至今没用-------------*/
        return mFullView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ALog.e("onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        ALog.e("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        ALog.e("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        ALog.e("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        ALog.e("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ALog.e("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ALog.e("onDestroy");
    }
 /*  @Override
    public void onDestroyView() {
        super.onDestroyView();
        //通过清空回退栈实现
        OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        // android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //循环的的pop回退栈
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        while (backStackEntryCount > 0) {
            fragmentManager.popBackStack();
            backStackEntryCount--;
        }
        fragmentTransaction.replace(R.id.fl_container,orderDetailsFragment);
        fragmentTransaction.commit();
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_indent_back:
                getFragmentManager().popBackStack();
                break;
       /*     case R.id.now_month:

                break;
            case R.id.before_month:

                break;
            case R.id.cancle_indent:

                break;*/
        }
    }
    //获取网络数据,其中type为变量;需要外部设置
    public void requestNetData(String type) {
        String url = RBConstants.URL_SERVER + "orderlist";
        HttpParams params = new HttpParams();
        params.put("type",type).put("page", "1").put("pageNum", "10");
        params.addHeader("userid",RBConstants.USERID);
        Class<MyIndentListBean> myIndentListBeanClass = MyIndentListBean.class;
        int requestCode = 108;
        HttpLoader.getInstance(mActivity).get(url, params, myIndentListBeanClass, requestCode, new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
               MyIndentListBean myIndentListBean =  (MyIndentListBean) response;
                mListView.setAdapter(new MyListAdapter(myIndentListBean)); //为listView建立适配器
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                Toast.makeText(UIUtils.getContext(),"网络请求异常",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private MyIndentListBean mMyIndentListBean;
    //-----------------listView的适配器-------------------------------------------
    class MyListAdapter extends BaseAdapter {


        public MyListAdapter(MyIndentListBean myIndentListBean) {
            mMyIndentListBean = myIndentListBean;
        }

        @Override
        public int getCount() {
            return mMyIndentListBean.orderList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            IndentViewHolder holder ;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.item_indent, null);
                holder = new IndentViewHolder();
                holder.indent_number_tv = (TextView) convertView.findViewById(R.id.indent_number_tv);
                holder.indent_sum_moeny = (TextView) convertView.findViewById(R.id.indent_sum_moeny);
                holder.state = (TextView) convertView.findViewById(R.id.state);
                holder.indent_time_tv = (TextView) convertView.findViewById(R.id.indent_time_tv);
                convertView.setTag(holder);
            }else {
                holder = (IndentViewHolder) convertView.getTag();
            }
            MyIndentListBean.OrderListBean orderListBean = mMyIndentListBean.orderList.get(position);
            holder.indent_number_tv.setText(orderListBean.orderId);
            holder.indent_sum_moeny.setText(orderListBean.price);
            holder.state.setText(orderListBean.status);
            holder.indent_time_tv.setText(orderListBean.time);

            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }

    private class IndentViewHolder {
        TextView indent_number_tv;
        TextView indent_sum_moeny;
        TextView state;
        TextView indent_time_tv;

    }


    /*//初始化常规视图（有数据的，没数据的
    public void initCommonView() {

        //有数据的视图
        mFullView = View.inflate(UIUtils.getContext(), R.layout.pager_indent_full, null);
        this.addView(mFullView);

        //没数据的视图
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_indent_empty, null);
        this.addView(mEmptyView);

        //根據當前頁面刷新ui
        refreshViewByState();

    }

    *//**
     * 根据状态刷新UI
     *//*
    private void refreshViewByState() {

        //没数据的视图显隐
        if (mCurState == STATE_EMPTY) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }

        //有数据的视图显隐
        if (mCurState == STATE_FULL) {
            mFullView.setVisibility(View.VISIBLE);
        } else {
            mFullView.setVisibility(View.GONE);
        }

    }*/


}
