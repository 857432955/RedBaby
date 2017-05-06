package com.itheima.redbaby.fragment;

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
 * 作者：程相稳 on 2016/12/5 16:10
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
public class ExpressFragment extends BaseFragment {
    @Bind(R.id.tv_head_backtv_epway)
    TextView tvHeadBacktvEpway;
    @Bind(R.id.rl_deliverway_te)
    RelativeLayout rlDeliverwayTe;
    @Bind(R.id.rl_deliverway_oe)
    RelativeLayout rlDeliverwayOe;
    @Bind(R.id.rl_deliverway_o)
    RelativeLayout rlDeliverwayO;
    @Bind(R.id.iv_deliverway_te)
    ImageView ivDeliverwayTe;
    @Bind(R.id.iv_deliverway_oe)
    ImageView ivDeliverwayOe;
    @Bind(R.id.iv_deliverway_o)
    ImageView ivDeliverwayO;

    @Override
    public View initViews() {
        return View.inflate(UIUtils.getContext(), R.layout.fragment_payment_deliveryway, null);
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

    @OnClick({R.id.tv_head_backtv_epway, R.id.rl_deliverway_te, R.id.rl_deliverway_oe, R.id.rl_deliverway_o})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_head_backtv_epway:
                getFragmentManager().popBackStack();
                break;
            case R.id.rl_deliverway_te:
                ivDeliverwayTe.setVisibility(View.VISIBLE);
                ivDeliverwayOe.setVisibility(View.INVISIBLE);
                ivDeliverwayO.setVisibility(View.INVISIBLE);
                break;
            case R.id.rl_deliverway_oe:
                ivDeliverwayOe.setVisibility(View.VISIBLE);
                ivDeliverwayTe.setVisibility(View.INVISIBLE);
                ivDeliverwayO.setVisibility(View.INVISIBLE);
                break;
            case R.id.rl_deliverway_o:
                ivDeliverwayO.setVisibility(View.VISIBLE);
                ivDeliverwayTe.setVisibility(View.INVISIBLE);
                ivDeliverwayOe.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
