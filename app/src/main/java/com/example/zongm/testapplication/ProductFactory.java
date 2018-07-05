package com.example.zongm.testapplication;

import javax.inject.Inject;

import android.util.Log;

/**
 * @author zongm on 2018/6/20
 */
public class ProductFactory {

    Product product;
    @Inject
    public ProductFactory(Product  product){
        this.product=product;
        Log.e("zmm","ProductFactory------------------>"+product.getName());
        this.product.setName("factory-->name");
    }

    public Product getProduct() {
        return product;
    }
}
