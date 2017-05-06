package com.itheima.redbaby.fragment;

import android.view.View;
import android.widget.Button;

import com.itheima.redbaby.R;

/**
 * .::::.
 * .::::::::.
 * :::::::::::
 * ..:::::::::::'
 * '::::::::::::'
 * .::::::::::
 * '::::::::::::::..
 * ..::::::::::::.
 * ``::::::::::::::::
 * ::::``:::::::::'        .:::.
 * ::::'   ':::::'       .::::::::.
 * .::::'      ::::     .:::::::'::::.
 * .:::'       :::::  .:::::::::' ':::::.
 * .::'        :::::.:::::::::'      ':::::.
 * .::'         ::::::::::::::'         ``::::.
 * ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 * '.:::::'                    ':'````..
 * Created by lx on 2016/12/7.
 */
public class LogisticsQueryFragment extends BaseFragment implements View.OnClickListener {
    @Override
    public View initViews() {
        View view  = View.inflate(mActivity,R.layout.fragment_logistics_query,null);
        Button back_orderdetails = (Button) view.findViewById(R.id.back_orderdetails);
        back_orderdetails.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        getFragmentManager().popBackStack();
    }
}
