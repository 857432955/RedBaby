package com.itheima.redbaby.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.itheima.redbaby.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/4.
 */
public class ceshi extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.bt_1)
    Button bt_1;
    @Bind(R.id.bt_2)
    Button bt_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ceshi);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        bt_1.setOnClickListener(this);
        bt_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_1:
                Intent intent = new Intent(ceshi.this, Product_clothes.class);
                startActivity(intent);
                break;

            case R.id.bt_2:
                Intent intent2 = new Intent(ceshi.this, product_drug.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}
