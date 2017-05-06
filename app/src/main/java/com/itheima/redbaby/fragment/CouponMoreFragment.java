package com.itheima.redbaby.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.redbaby.R;
import com.itheima.redbaby.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：汤 on 2016/12/5 12:41
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
public class CouponMoreFragment extends BaseFragment implements View.OnClickListener {
    private TextView mBack_usercenter;
    private RelativeLayout mSp9_50_rl;
    private RelativeLayout mNationday_80_rl;
    private RelativeLayout mChristmas_80_rl;
    private ImageView mSp9_50_iv;
    private ImageView mNationday_80_iv;
    private ImageView mChristmas_80_iv;
    private boolean mCurrent = false;
    private boolean mCurrent1 = false;
    private boolean mCurrent2 = false;
    private View mView;
    private TextView mAddmore_tv;

    @Override
    public View initViews() {
        mView = View.inflate(UIUtils.getContext(), R.layout. fragment_more_coupon, null);

        return mView;
    }

    @Override
    protected void initData() {
        mBack_usercenter = (TextView) mView.findViewById(R.id.back_usercenter);
        mSp9_50_rl = (RelativeLayout) mView.findViewById(R.id.sp9_50_rl);
        mNationday_80_rl = (RelativeLayout) mView.findViewById(R.id.nationday_80_rl);
        mChristmas_80_rl = (RelativeLayout) mView.findViewById(R.id.christmas_80_rl);
        mSp9_50_iv = (ImageView) mView.findViewById(R.id.sp9_50_iv);
        mNationday_80_iv = (ImageView) mView.findViewById(R.id.nationday_80_iv);
        mChristmas_80_iv = (ImageView) mView.findViewById(R.id.christmas_80_iv);
        mAddmore_tv = (TextView) mView.findViewById(R.id.addmore_tv);
        mBack_usercenter.setOnClickListener(this);
        mSp9_50_rl.setOnClickListener(this);
        mNationday_80_rl.setOnClickListener(this);
        mChristmas_80_rl.setOnClickListener(this);
        mAddmore_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_usercenter:
               getFragmentManager().popBackStack();
              break;
            case R.id.sp9_50_rl:
                if (!mCurrent) {
                    mSp9_50_iv.setVisibility(View.INVISIBLE);
                    mCurrent = true;
                }else {
                    mSp9_50_iv.setVisibility(View.VISIBLE);
                    mChristmas_80_iv.setVisibility(View.INVISIBLE);
                    mNationday_80_iv.setVisibility(View.INVISIBLE);
                    mCurrent = false;
                    mCurrent1 = false;
                    mCurrent2 = false;

                }

                break;
            case R.id.nationday_80_rl:
                if (!mCurrent1) {
                    mNationday_80_iv.setVisibility(View.VISIBLE);
                    mSp9_50_iv.setVisibility(View.INVISIBLE);
                    mChristmas_80_iv.setVisibility(View.INVISIBLE);
                    mCurrent1 = true;
                    mCurrent = false;
                    mCurrent2 = false;
                }else {
                    mNationday_80_iv.setVisibility(View.INVISIBLE);
                   /* mSp9_50_iv.setVisibility(View.INVISIBLE);
                    mChristmas_80_iv.setVisibility(View.INVISIBLE);*/
                    mCurrent1 = false;
                }
                break;
            case R.id.christmas_80_rl:
                if (!mCurrent2) {
                    mChristmas_80_iv.setVisibility(View.VISIBLE);
                    mNationday_80_iv.setVisibility(View.INVISIBLE);
                    mSp9_50_iv.setVisibility(View.INVISIBLE);
                    mCurrent2 = true;
                    mCurrent = false;
                    mCurrent1 = false;
                }else {
                    mChristmas_80_iv.setVisibility(View.INVISIBLE);
                   /* mNationday_80_iv.setVisibility(View.INVISIBLE);
                    mSp9_50_iv.setVisibility(View.INVISIBLE);*/
                    mCurrent2 = false;
                }
                break;

            case R.id.addmore_tv:
                Toast.makeText(UIUtils.getContext(),"亲,您没有更多的优惠券了哦~~~",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
