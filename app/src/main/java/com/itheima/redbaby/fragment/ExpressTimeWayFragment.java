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
 * 作者：程相稳 on 2016/12/5 11:52
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
public class ExpressTimeWayFragment extends BaseFragment {
    @Bind(R.id.tv_head_backtv_eptime)
    TextView tvHeadBacktvEptime;
    @Bind(R.id.rl_express_alltime)
    RelativeLayout rlExpressAlltime;
    @Bind(R.id.rl_express_resttime)
    RelativeLayout rlExpressResttime;
    @Bind(R.id.rl_express_workttime)
    RelativeLayout rlExpressWorkttime;
    @Bind(R.id.iv_express_alltime)
    ImageView ivExpressAlltime;
    @Bind(R.id.iv_express_resttime)
    ImageView ivExpressResttime;
    @Bind(R.id.iv_express_workttime)
    ImageView ivExpressWorkttime;
    private boolean mCurrent = false;
    private boolean mCurrent1 = false;
    private boolean mCurrent2 = false;
    private SharedPreferences.Editor mEditor;

    @Override
    public View initViews() {
        return View.inflate(UIUtils.getContext(), R.layout.fragment_express_time, null);
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
        String paytype = sharedPreferences.getString("paytime", "");
        if (paytype.equals("双休日 、工作日与节假日均可送货")){
            mCurrent=true;
            mCurrent1 = false;
            mCurrent2 = false;
            ivExpressAlltime.setVisibility(View.VISIBLE);
        }else if(paytype.equals("只双休日与节假日可以送货")){
            mCurrent=false;
            mCurrent1 = true;
            mCurrent2 = false;
            ivExpressResttime.setVisibility(View.VISIBLE);
        }else if(paytype.equals("只工作日送货")){
            mCurrent=false;
            mCurrent1 = false;
            mCurrent2 = true;
            ivExpressWorkttime.setVisibility(View.VISIBLE);
        }
        super.initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_head_backtv_eptime, R.id.rl_express_alltime, R.id.rl_express_resttime, R.id.rl_express_workttime})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_head_backtv_eptime:
                getFragmentManager().popBackStack();
                break;
            case R.id.rl_express_alltime:
                if (mCurrent) {
                    ivExpressAlltime.setVisibility(View.INVISIBLE);
                    mEditor.putString("paytime", "null");
                    mCurrent = false;
                }else {
                    ivExpressAlltime.setVisibility(View.VISIBLE);
                    ivExpressResttime.setVisibility(View.INVISIBLE);
                    ivExpressWorkttime.setVisibility(View.INVISIBLE);
                    mCurrent = true;
                    mCurrent1 = false;
                    mCurrent2 = false;
                    mEditor.putString("paytime", "双休日 、工作日与节假日均可送货");
                }
                mEditor.commit();//提交修改
                break;
            case R.id.rl_express_resttime:
                if (!mCurrent1) {
                    ivExpressResttime.setVisibility(View.VISIBLE);
                    ivExpressAlltime.setVisibility(View.INVISIBLE);
                    ivExpressWorkttime.setVisibility(View.INVISIBLE);
                    mEditor.putString("paytime", "只双休日与节假日可以送货");
                    mCurrent1 = true;
                    mCurrent = false;
                    mCurrent2 = false;
                }else {
                    ivExpressResttime.setVisibility(View.INVISIBLE);
                    mCurrent1 = false;
                    mEditor.putString("paytime", "null");
                }
                mEditor.commit();//提交修改
                break;
            case R.id.rl_express_workttime:
                if (!mCurrent2) {
                    ivExpressWorkttime.setVisibility(View.VISIBLE);
                    ivExpressResttime.setVisibility(View.INVISIBLE);
                    ivExpressAlltime.setVisibility(View.INVISIBLE);
                    mEditor.putString("paytime", "只工作日送货");
                    mCurrent2 = true;
                    mCurrent = false;
                    mCurrent1 = false;
                }else {
                    ivExpressWorkttime.setVisibility(View.INVISIBLE);
                    mCurrent2 = false;
                    mEditor.putString("paytime", "null");
                }
                mEditor.commit();//提交修改
                break;
        }
    }
}
