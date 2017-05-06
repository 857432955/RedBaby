package com.itheima.redbaby.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.bean.Clothes_bean;
import com.itheima.redbaby.utils.UIUtils;
import com.itheima.redbaby.utils.ll_Api;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2016/12/10.
 */
public class ClothesFragmentActivity2 extends Activity {

    public ViewPager viewPager;
    private LinearLayout indicator2;
    private ImageView iv;
    public String getId;
    private String product_name;
    private String product_market_money;
    private String product_vip_money;
    private float star;
    public Clothes_bean clothesBean;
    public List<String> product_vp;


    public TextView tv_vipmoney;
    public TextView tv_markmoney;
    public RatingBar rb_star;
    public TextView name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clothesfragmentactivity);
        initRequest();
       // product_back = (Button) findViewById(R.id.product_back);
        indicator2 = (LinearLayout) findViewById(R.id.indicator2);
        viewPager = (ViewPager) findViewById(R.id.bg_vp);
        tv_markmoney = (TextView) findViewById(R.id.tv_markmoney2);
        tv_vipmoney = (TextView) findViewById(R.id.tv_vipmoney2);
        rb_star = (RatingBar) findViewById(R.id.rb_star2);
        name = (TextView) findViewById(R.id.tv_12);

        /*product_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
    }

    private void initRequest() {
        getId = getIntent().getStringExtra("pId");
        System.out.println("getId22222222222222222------------------ "+getId);
        initData();
    }

    /**详情页面请求数据*/
    private void initData() {
        ll_Api.getClothesData(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                clothesBean = (Clothes_bean) response;
                product_name = clothesBean.product.name;
                product_market_money = clothesBean.product.limitPrice;
                product_vip_money = clothesBean.product.buyLimit;
                star = clothesBean.product.score;
                product_vp = clothesBean.product.pics;
                System.out.println("product_name=" + product_name + " product_market_money=" + product_market_money
                        + " product_vip_money=" + product_vip_money + " star=" + star);
                for (String s : product_vp) {
                    System.out.println("s=" + s);
                }
                initDog();//初始化小圆点
                viewPager.setAdapter(new bigPicture(product_vp));

                tv_markmoney.setText(product_market_money);
                tv_vipmoney.setText(product_vip_money);
                name.setText(product_name);
                rb_star.setRating(star);
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                Toast.makeText(UIUtils.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        }, getId);
    }

    private void initDog() {
        for (int i = 0; i < product_vp.size(); i++) {
            iv = new ImageView(this);
            iv.setImageResource(R.drawable.slide_adv_normal);//默认点
            if (i == 0) {
                iv.setImageResource(R.drawable.slide_adv_selected);//选择默认第一个点
            }
            //初始化布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.leftMargin = 18;
            }
            indicator2.addView(iv, params);

            initList();//监听页面切换
        }
    }

    private void initList() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getApplicationContext(),"position="+position,Toast.LENGTH_SHORT).show();
                for(int i =0;i<product_vp.size();i++){
                    iv = (ImageView) indicator2.getChildAt(i);
                    iv.setImageResource(R.drawable.slide_adv_normal);
                    if(position ==i){
                        iv.setImageResource(R.drawable.slide_adv_selected);
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
}
