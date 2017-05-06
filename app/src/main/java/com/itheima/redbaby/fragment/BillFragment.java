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
 * 作者：程相稳 on 2016/12/5 15:53
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
public class BillFragment extends BaseFragment {
    @Bind(R.id.tv_head_backtv_epway)
    TextView tvHeadBacktvEpway;
    @Bind(R.id.tv_addbill)
    TextView tvAddbill;
    @Bind(R.id.rl_bill_company)
    RelativeLayout rlBillCompany;
    @Bind(R.id.rl_bill_personal)
    RelativeLayout rlBillPersonal;
    RelativeLayout rlBillDatum;
    @Bind(R.id.rl_bill_other)
    RelativeLayout rlBillOther;
    @Bind(R.id.iv_bill_company)
    ImageView ivBillCompany;
    @Bind(R.id.tv_bill_personal)
    ImageView tvBillPersonal;
    @Bind(R.id.iv_bill_other)
    ImageView ivBillOther;
    @Bind(R.id.iv_bill_commodity)
    ImageView ivBillCommodity;
    @Bind(R.id.rl_bill_commodity)
    RelativeLayout rlBillCommodity;
    @Bind(R.id.iv_bill_dress)
    ImageView ivBillDress;
    @Bind(R.id.rl_bill_dress)
    RelativeLayout rlBillDress;
    private boolean mCurrent = false;
    private boolean mCurrent1 = false;
    private boolean mCurrent2 = false;
    private boolean mCurrent3 = false;
    private boolean mCurrent4 = false;
    private SharedPreferences.Editor mEditor;

    @Override
    public View initViews() {
        return View.inflate(UIUtils.getContext(), R.layout.fragment_payment_bill, null);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {
        SharedPreferences sharedPreferences = UIUtils.getContext().getSharedPreferences("oderdetail", Context.MODE_PRIVATE);
        //获取编辑器
        mEditor = sharedPreferences.edit();
        String whopay = sharedPreferences.getString("whopay", "");
        String billcontent = sharedPreferences.getString("billcontent", "");
        if (whopay.equals("单位")){
            mCurrent=true;
            mCurrent1 = false;
            ivBillCompany.setVisibility(View.VISIBLE);
        }else if(whopay.equals("个人")){
            mCurrent=false;
            mCurrent1 = true;
            tvBillPersonal.setVisibility(View.VISIBLE);
        }
        if (billcontent.equals("日用品")){
            mCurrent2 = true;
            mCurrent3 = false;
            mCurrent4 = false;
            ivBillCommodity.setVisibility(View.VISIBLE);

        }else if (billcontent.equals("服饰")){
            mCurrent2 = false;
            mCurrent3 = true;
            mCurrent4 = false;
            ivBillDress.setVisibility(View.VISIBLE);
        }
        else if (billcontent.equals("其他")){
            mCurrent2 = false;
            mCurrent3 = false;
            mCurrent4 = true;
            ivBillOther.setVisibility(View.VISIBLE);
        }
        super.initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_head_backtv_epway, R.id.tv_addbill, R.id.rl_bill_company, R.id.rl_bill_personal,
            R.id.rl_bill_dress,R.id.rl_bill_commodity,R.id.rl_bill_other
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_head_backtv_epway:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_addbill:
                break;


            case R.id.rl_bill_company:
                if (mCurrent) {
                    ivBillCompany.setVisibility(View.INVISIBLE);
                    mEditor.putString("whopay", "null");
                    mCurrent = false;
                }else {
                    ivBillCompany.setVisibility(View.VISIBLE);
                    tvBillPersonal.setVisibility(View.INVISIBLE);
                    mCurrent = true;
                    mCurrent1 = false;
                    mEditor.putString("whopay", "单位");
                }
                mEditor.commit();//提交修改
                break;
            case R.id.rl_bill_personal:
                if (!mCurrent1) {
                    ivBillCompany.setVisibility(View.INVISIBLE);
                    tvBillPersonal.setVisibility(View.VISIBLE);
                    mEditor.putString("whopay", "个人");
                    mCurrent1 = true;
                    mCurrent = false;
                    mCurrent2 = false;
                }else {
                    tvBillPersonal.setVisibility(View.INVISIBLE);
                    mCurrent1 = false;
                    mEditor.putString("whopay", "null");
                }
                mEditor.commit();//提交修改
                break;


            case R.id.rl_bill_commodity:
                if (mCurrent2) {
                    ivBillCommodity.setVisibility(View.INVISIBLE);
                    mEditor.putString("billcontent", "null");
                    mCurrent2 = false;
                }else {
                    ivBillCommodity.setVisibility(View.VISIBLE);
                    ivBillDress.setVisibility(View.INVISIBLE);
                    ivBillOther.setVisibility(View.INVISIBLE);
                    mCurrent2 = true;
                    mCurrent3 = false;
                    mCurrent4 = false;
                    mEditor.putString("billcontent", "日用品");
                }
                mEditor.commit();//提交修改
                break;
            case R.id.rl_bill_dress:
                if (mCurrent3) {
                    ivBillDress.setVisibility(View.INVISIBLE);
                    mEditor.putString("billcontent", "null");
                    mCurrent3 = false;
                }else {
                    ivBillCommodity.setVisibility(View.INVISIBLE);
                    ivBillDress.setVisibility(View.VISIBLE);
                    ivBillOther.setVisibility(View.INVISIBLE);
                    mCurrent2 = false;
                    mCurrent3 = true;
                    mCurrent4 = false;
                    mEditor.putString("billcontent", "服饰");
                }
                mEditor.commit();//提交修改
                break;
            case R.id.rl_bill_other:
                if (mCurrent4) {
                    ivBillOther.setVisibility(View.INVISIBLE);
                    mEditor.putString("billcontent", "null");
                    mCurrent4 = false;
                }else {
                    ivBillCommodity.setVisibility(View.INVISIBLE);
                    ivBillDress.setVisibility(View.INVISIBLE);
                    ivBillOther.setVisibility(View.VISIBLE);
                    mCurrent2 = false;
                    mCurrent3 = false;
                    mCurrent4 = true;
                    mEditor.putString("billcontent", "其他");
                }
                mEditor.commit();//提交修改
                break;

        }
    }


}
