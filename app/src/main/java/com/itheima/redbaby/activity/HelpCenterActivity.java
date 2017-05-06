package com.itheima.redbaby.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.redbaby.R;
import com.itheima.redbaby.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
 * Created by tqm on 2016/12/4.
 */
public class HelpCenterActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.back_to_more)
    TextView mBackToMore;
    private RelativeLayout mHelp_center_shoping;
    private RelativeLayout mHelp_center_shell;
    private RelativeLayout mHelp_center_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        ButterKnife.bind(this);
        mHelp_center_shoping = (RelativeLayout) findViewById(R.id.help_center_shoping);
        mHelp_center_shell = (RelativeLayout) findViewById(R.id.help_center_shell);
        mHelp_center_send = (RelativeLayout) findViewById(R.id.help_center_send);
        mHelp_center_shoping.setOnClickListener(this);
        mHelp_center_shell.setOnClickListener(this);
        mHelp_center_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.help_center_shoping:
                startActivity(new Intent(UIUtils.getContext(), ShopingGuidActivity.class));

                break;
            case R.id.help_center_shell:
                startActivity(new Intent(UIUtils.getContext(), AftersaleServiceActivity.class));
                break;
            case R.id.help_center_send:
                startActivity(new Intent(UIUtils.getContext(), SendModeActivity.class));
                break;
        }
        finish();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @OnClick(R.id.back_to_more)
    public void onClick() {
        finish();
    }
}
