package com.itheima.redbaby.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itheima.redbaby.R;
import com.itheima.redbaby.bean.Clothes_bean;
import com.itheima.redbaby.utils.UIUtils;
import com.itheima.redbaby.utils.ll_Api;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 刘亮 商品详情页面
 */
public class Product_clothes extends AppCompatActivity {

    @Bind(R.id.ll_vp)
    ViewPager viewpager;
    @Bind(R.id.vp2)
    ViewPager vp;

    @Bind(R.id.spinner_huowu)
    Spinner spinnerhuowu;
    @Bind(R.id.indicator)
    LinearLayout indicator;
    @Bind(R.id.product_back)
    TextView product_back;
    private ImageView iv;


    private ExpandableListView expandableListView;
    private List<List<String>> body_news;
    private List<List<Integer>> body_image;
    private List<Integer> head_image;
    private List<String> head_news;
    private List<List<String>> body_names;
    private List<String> huowu;


    private String product_name;
    private String product_market_money;
    private String product_vip_money;
    private float star;
    public Clothes_bean clothesBean;
    public List<String> product_vp;
    public String getId;
    public SharedPreferences product_peng;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pd_clothes);
        initGetPid();//获取地址id
        initRequest();//请求数据
        initData();//数据绑定
        //initOnclick();//购物车、收藏点击事件
        initSpinnerWuLiu();
        initExpandableListView();//评论收缩列表
    }

    private void initGetPid() {
        getId = getIntent().getStringExtra("pId");
        System.out.println("getIdaaaaaaaaaaaaaaaaaaa------------------ "+getId);
    }

    private void initData() {
        ButterKnife.bind(this);
        product_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**详情页面请求数据*/
    private void initRequest(){
       ll_Api.getClothesData(new HttpLoader.HttpListener() {
            @Override
            public void onGetResponseSuccess(int requestCode, IResponse response) {
                clothesBean = (Clothes_bean) response;
                product_name = clothesBean.product.name;
                product_market_money = clothesBean.product.limitPrice;
                product_vip_money = clothesBean.product.buyLimit;
                star = clothesBean.product.score;
                product_vp = clothesBean.product.pics;
                System.out.println("product_name="+product_name+" product_market_money="+product_market_money
                        +" product_vip_money="+product_vip_money+" star="+star);
                for(String s : product_vp){
                    System.out.println("s="+s);
                }
                initDog();//初始化小圆点
                vp.setAdapter((new ClothesVP(getSupportFragmentManager())));
                viewpager.setAdapter(new ClothesAdapter(product_vp,getId,Product_clothes.this));
            }

            @Override
            public void onGetResponseError(int requestCode, VolleyError error) {
                Toast.makeText(UIUtils.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        },getId);
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
                indicator.addView(iv, params);

                initList();//监听页面切换
            }
    }

    private void initList() {
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getApplicationContext(),"position="+position,Toast.LENGTH_SHORT).show();
                for(int i =0;i<product_vp.size();i++){
                    iv = (ImageView) indicator.getChildAt(i);
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

    private void initExpandableListView(){
        //虚拟数据
        head_news = new ArrayList<String>();
        head_news.add("用户评论:  共有388人评论");

        //名字
        List<String> name = new ArrayList<String>();
        name.add("李大宝");
        name.add("张伟");
        name.add("颜景攀");
        body_names = new ArrayList<List<String>>();
        body_names.add(name);
        //放入信息
        List<String> newss = new ArrayList<String>();
        newss.add("商品太好了!!!");
        newss.add("商品实在是不错!!!");
        newss.add("商品很棒!!!");
        body_news = new ArrayList<List<String>>();
        body_news.add(newss);

        //虚拟图片
        List<Integer> tmp_list = new ArrayList<Integer>();
        tmp_list.add(R.drawable.lltouxiang);
        tmp_list.add(R.drawable.lltouxiang);
        tmp_list.add(R.drawable.lltouxiang);

        //放入图片
        body_image = new ArrayList<List<Integer>>();
        body_image.add(tmp_list);
        body_image.add(tmp_list);
        body_image.add(tmp_list);

        expandableListView = (ExpandableListView) findViewById(R.id.expendlist);
        expandableListView.setGroupIndicator(null);
        expandableListView.setAdapter(new MyExpandableListViewAdapter(head_news,body_news,body_image,body_names));
    }

    public void initSpinnerWuLiu(){
        huowu = new ArrayList<String>();
        huowu.add("北京仓(有货)");
        huowu.add("济南仓(有货)");
        huowu.add("天津仓(无货)");
        //使用自定义的ArrayAdapter
        ArrayAdapter<String> mAdapter  = new TestArrayAdapter(Product_clothes.this,huowu);

        //设置下拉列表风格(这句不些也行)
        //mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerhuowu.setAdapter(mAdapter);
        //监听Item选中事件
        spinnerhuowu.setOnItemSelectedListener(new ItemSelectedListenerImpl());

    }

    private class ItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position,long arg3) {
            System.out.println("选中了:"+huowu.get(position));
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }

}
