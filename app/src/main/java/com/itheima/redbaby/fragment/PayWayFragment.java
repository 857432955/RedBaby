package com.itheima.redbaby.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.redbaby.R;
import com.itheima.redbaby.utils.UIUtils;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：程相稳 on 2016/12/4 15:26
 * /                       .::::.
 * /                     .::::::::.
 * /                    :::::::::::
 * /                 ..:::::::::::'
 * /              '::::::::::::'
 * /                .::::::::::
 * /           '::::::::::::::..
 * /                ..::::::::::::.
 * /              ``::::::::::::::::
 * /               ::::``:::::::::'        .:::.
 * /              ::::'   ':::::'       .::::::::.
 * /            .::::'      ::::     .:::::::'::::.
 * /           .:::'       :::::  .:::::::::' ':::::.
 * /          .::'        :::::.:::::::::'      ':::::.
 * /         .::'         ::::::::::::::'         ``::::.
 * /     ...:::           ::::::::::::'              ``::.
 * /    ```` ':.          ':::::::::'                  ::::..
 * /                       '.:::::'                    ':'````..
 */
public class PayWayFragment extends BaseFragment {

    @Bind(R.id.tv_head_backtv_epway)
    TextView tvHeadBacktvEpway;
    @Bind(R.id.rl_pay_cash)
    RelativeLayout rlPayCash;
    @Bind(R.id.rl_pay_postage)
    RelativeLayout rlPayPostage;
    @Bind(R.id.rl_pay_alipay)
    RelativeLayout rlPayAlipay;
    @Bind(R.id.iv_pay_cash)
    ImageView ivPayCash;
    @Bind(R.id.iv_pay_postage)
    ImageView ivPayPostage;
    @Bind(R.id.tv_pay_alipay)
    ImageView tvPayAlipay;
    private boolean mCurrent = false;
    private boolean mCurrent1 = false;
    private boolean mCurrent2 = false;
    private SharedPreferences.Editor mEditor;

    @Override
    public View initViews() {
        return View.inflate(UIUtils.getContext(), R.layout.fragment_payment_payway, null);
    }

    @Override
    protected void initData() {
        SharedPreferences sharedPreferences = UIUtils.getContext().getSharedPreferences("oderdetail", Context.MODE_PRIVATE);
        //获取编辑器
        mEditor = sharedPreferences.edit();
        String paytype = sharedPreferences.getString("paytype", "");
        if (paytype.equals("到付-现金")){
            mCurrent=true;
            mCurrent1 = false;
            mCurrent2 = false;
            ivPayCash.setVisibility(View.VISIBLE);
        }else if(paytype.equals("到付-pos")){
            mCurrent=false;
            mCurrent1 = true;
            mCurrent2 = false;
            ivPayPostage.setVisibility(View.VISIBLE);
        }else if(paytype.equals("支付宝")){
            mCurrent=false;
            mCurrent1 = false;
            mCurrent2 = true;
            tvPayAlipay.setVisibility(View.VISIBLE);
        }
        super.initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_head_backtv_epway, R.id.rl_pay_cash, R.id.rl_pay_postage, R.id.rl_pay_alipay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_head_backtv_epway:
                getFragmentManager().popBackStack();
               // getFragmentManager().beginTransaction().hide(this).commit();
                break;
            case R.id.rl_pay_cash:
                if (mCurrent) {
                    ivPayCash.setVisibility(View.INVISIBLE);
                    mEditor.putString("paytype", "null");
                    mCurrent = false;
                }else {
                    ivPayCash.setVisibility(View.VISIBLE);
                    tvPayAlipay.setVisibility(View.INVISIBLE);
                    ivPayPostage.setVisibility(View.INVISIBLE);
                    mCurrent = true;
                    mCurrent1 = false;
                    mCurrent2 = false;
                    mEditor.putString("paytype", "到付-现金");
                }
                mEditor.commit();//提交修改
                break;
            case R.id.rl_pay_postage:
                if (!mCurrent1) {
                    tvPayAlipay.setVisibility(View.INVISIBLE);
                    ivPayCash.setVisibility(View.INVISIBLE);
                    ivPayPostage.setVisibility(View.VISIBLE);
                    mEditor.putString("paytype", "到付-pos");
                    mCurrent1 = true;
                    mCurrent = false;
                    mCurrent2 = false;
                }else {
                    ivPayPostage.setVisibility(View.INVISIBLE);
                    mCurrent1 = false;
                    mEditor.putString("paytype", "null");
                }
                mEditor.commit();//提交修改
                break;
            case R.id.rl_pay_alipay:
                if (!mCurrent2) {
                    ivPayCash.setVisibility(View.INVISIBLE);
                    ivPayPostage.setVisibility(View.INVISIBLE);
                    tvPayAlipay.setVisibility(View.VISIBLE);
                    mEditor.putString("paytype", "支付宝");
                    mCurrent2 = true;
                    mCurrent = false;
                    mCurrent1 = false;
                }else {
                    tvPayAlipay.setVisibility(View.INVISIBLE);
                    mCurrent2 = false;
                    mEditor.putString("paytype", "null");
                }
                mEditor.commit();//提交修改
                break;
        }
    }
}
