package com.example.zongm.testapplication;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author zongm on 2018/6/20
 */
@Module
public class ProductModule {

    @Provides
    public  Product provideProduct(){
        Product product=new Product();
        product.setName("ProductModel---->Name");
        return  product;
    }

    @Provides
    public ProductFactory provideFactory(Product product){
        return  new ProductFactory(product);
    }



}
