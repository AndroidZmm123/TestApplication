package com.example.zongm.testapplication;

import javax.inject.Inject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;

public class SecondActivity extends AppCompatActivity {

    @Inject
    Product product;


    @Inject ProductFactory factory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TestFragment testFragment=TestFragment.newInstance("","");
        //DaggerActivityComponent.create().inject(this);
        DaggerActivityComponent.builder().productModule(new ProductModule())
            .build().inject(this);
        //product.setName("测试产品");
        Log.e("zmm","-------------->"+product.getName());

        Log.e("zmm","---------------->"+factory.getProduct().getName());

    }
}
