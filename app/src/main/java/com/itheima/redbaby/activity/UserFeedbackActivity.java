package com.itheima.redbaby.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.redbaby.R;
import com.itheima.redbaby.fragment.AddressFragment;
import com.itheima.redbaby.manager.FragmentInstanceManager;
import com.itheima.redbaby.utils.UIUtils;

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
 * Created by lx on 2016/12/4.
 */
public class UserFeedbackActivity extends Activity implements View.OnClickListener {

    private TextView mTextView;
    private TextView mCommit_tv;
    private EditText mComments_et;
    private EditText mInformation_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userfeedback);
        mTextView = (TextView) findViewById(R.id.back_more);
        mCommit_tv = (TextView) findViewById(R.id.commit_tv);
        mComments_et = (EditText) findViewById(R.id.comments_et);
        mInformation_et = (EditText) findViewById(R.id.information_et);
        mTextView.setOnClickListener(this);
        mCommit_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_more:
                finish();
                break;

            case R.id.commit_tv:
              //清空对话框
               String s1 =  mComments_et.getText().toString();
                String s2 =  mInformation_et.getText().toString();
                if (s1.equals("") || s2.equals(""))  {
                    Toast.makeText(UIUtils.getContext(),"请按要求填写反馈",Toast.LENGTH_SHORT).show();
                }else {
                    mComments_et.setText("");
                    mInformation_et.setText("");
                    Toast.makeText(UIUtils.getContext(),"谢谢您的反馈,我们会及时给您回复!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
