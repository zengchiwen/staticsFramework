package org.zcw.staticsframework;

import android.app.Application;

/**
 * Created by lenovo on 2018/3/28.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
         StaticsManager.init(this.getPackageName(),this);
    }
}
