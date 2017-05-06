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
 * 作者：程相稳 on 2016/12/5 12:41
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
public class CouponFragment extends BaseFragment {
    @Bind(R.id.head_back_paycenter)
    TextView headBackPaycenter;
    @Bind(R.id.tv_addgiftcard)
    TextView tvAddgiftcard;
    @Bind(R.id.rl_sp9_50)
    RelativeLayout rlSp950;
    @Bind(R.id.rl_nationday_80)
    RelativeLayout rlNationday80;
    @Bind(R.id.rl_christmas_80)
    RelativeLayout rlChristmas80;
    @Bind(R.id.iv_sp9_50)
    ImageView ivSp950;
    @Bind(R.id.iv_nationday_80)
    ImageView ivNationday80;
    @Bind(R.id.iv_christmas_80)
    ImageView ivChristmas80;
    private boolean mCurrent = false;
    private boolean mCurrent1 = false;
    private boolean mCurrent2 = false;
    private SharedPreferences.Editor mEditor;

    @Override
    public View initViews() {
        return View.inflate(UIUtils.getContext(), R.layout.fragment_payment_giftcard, null);
    }

    @Override
    protected void initData() {
        SharedPreferences sharedPreferences = UIUtils.getContext().getSharedPreferences("oderdetail", Context.MODE_PRIVATE);
        //获取编辑器
        mEditor = sharedPreferences.edit();
        String coupontype = sharedPreferences.getString("coupontype", "");
        if (coupontype.equals("9月惊喜50元礼券")) {
            mCurrent = true;
            mCurrent1 = false;
            mCurrent2 = false;
            ivSp950.setVisibility(View.VISIBLE);
        } else if (coupontype.equals("国庆80元礼券")) {
            mCurrent = false;
            mCurrent1 = true;
            mCurrent2 = false;
            ivNationday80.setVisibility(View.VISIBLE);
        } else if (coupontype.equals("圣诞节大放送80元礼券")) {
            mCurrent = false;
            mCurrent1 = false;
            mCurrent2 = true;
            ivChristmas80.setVisibility(View.VISIBLE);
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

    @OnClick({R.id.head_back_paycenter, R.id.tv_addgiftcard,R.id.rl_sp9_50,R.id.rl_nationday_80,R.id.rl_christmas_80})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_back_paycenter:
                getFragmentManager().popBackStack();
                break;
            case R.id.rl_sp9_50:
                if (mCurrent) {
                    ivSp950.setVisibility(View.INVISIBLE);
                    mEditor.putString("coupontype", "null");
                    mCurrent = false;
                }else {
                    ivSp950.setVisibility(View.VISIBLE);
                    ivChristmas80.setVisibility(View.INVISIBLE);
                    ivNationday80.setVisibility(View.INVISIBLE);
                    mCurrent = true;
                    mCurrent1 = false;
                    mCurrent2 = false;
                    mEditor.putString("coupontype", "9月惊喜50元礼券");
                }
                mEditor.commit();//提交修改
                break;
            case R.id.rl_nationday_80:
                if (!mCurrent1) {
                    ivNationday80.setVisibility(View.VISIBLE);
                    ivChristmas80.setVisibility(View.INVISIBLE);
                    ivSp950.setVisibility(View.INVISIBLE);
                    mEditor.putString("coupontype", "国庆80元礼券");
                    mCurrent1 = true;
                    mCurrent = false;
                    mCurrent2 = false;
                }else {
                    ivNationday80.setVisibility(View.INVISIBLE);
                    mCurrent1 = false;
                    mEditor.putString("coupontype", "null");
                }
                mEditor.commit();//提交修改
                break;
            case R.id.rl_christmas_80:
                if (!mCurrent2) {
                    ivChristmas80.setVisibility(View.VISIBLE);
                    ivNationday80.setVisibility(View.INVISIBLE);
                    ivSp950.setVisibility(View.INVISIBLE);
                    mEditor.putString("coupontype", "圣诞节大放送80元礼券");
                    mCurrent2 = true;
                    mCurrent = false;
                    mCurrent1 = false;
                }else {
                    ivChristmas80.setVisibility(View.INVISIBLE);
                    mCurrent2 = false;
                    mEditor.putString("coupontype", "null");
                }
                mEditor.commit();//提交修改
                break;

        }
    }
}
