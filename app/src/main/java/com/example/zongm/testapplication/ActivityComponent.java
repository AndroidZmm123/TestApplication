package com.example.zongm.testapplication;

import dagger.Component;
import dagger.Module;

/**
 * @author zongm on 2018/6/20
 */
@Component(modules=ProductModule.class)
public interface ActivityComponent {
    void inject(SecondActivity secondActivity);
}
