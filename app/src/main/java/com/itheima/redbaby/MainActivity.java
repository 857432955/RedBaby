package com.itheima.redbaby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.fragment.CarLoginFragment;
import com.itheima.redbaby.fragment.HomeFragment;
import com.itheima.redbaby.fragment.LoginFragment;
import com.itheima.redbaby.manager.FragmentFactory;
import com.itheima.redbaby.manager.FragmentInstanceManager;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.rg_rb)
    RadioGroup mRgRb;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();
        ButterKnife.bind(this);
        initData();

    }


    /**
     * 初始化数据
     */
    private void initData() {
        //RadioGroup设置选择监听器
        mRgRb.setOnCheckedChangeListener(new RadioGroupListener());
        //手动设置显示首页
        switchFragment(FragmentInstanceManager.getInstance().getFragment(HomeFragment.class));
    }

    /**
     * RadioGroup的监听器
     */
    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_home:
                    switchFragment(FragmentFactory.createFragment(0));
                    break;
                case R.id.rb_search:
                    switchFragment(FragmentFactory.createFragment(1));
                    break;
                case R.id.rb_class:
                    switchFragment(FragmentFactory.createFragment(2));
                    break;
                case R.id.rb_shopping:
                    if (RBConstants.USERID.isEmpty()){
                        Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                        CarLoginFragment login = new CarLoginFragment();
                        switchFragment(login);
                    }else{
                        switchFragment(FragmentFactory.createFragment(3));
                    }

                    break;
                case R.id.rb_more:
                    switchFragment(FragmentFactory.createFragment(4));
                    break;
            }
        }
    }

    //提供方法切换Fragment，点击RadioButton的时候需要清空回退栈
    public void switchFragment(Fragment fragment) {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.fl_container, fragment);
        transaction.commit();
    }
}
