package org.zcw.staticsframework.trafficstatics;

import android.graphics.drawable.Drawable;

/**
 * Created by lenovo on 2017/7/14.
 */

public class TrafficInfo {


    private long Rx;
    private long Tx;
    private String packgeName;
    private String appname;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    private Drawable icon;

    public long getRx() {
        return Rx;
    }

    public void setRx(long rx) {
        Rx = rx;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPackgeName() {
        return packgeName;
    }

    public void setPackgeName(String packgeName) {
        this.packgeName = packgeName;
    }

    public long getTx() {
        return Tx;
    }

    public void setTx(long tx) {
        Tx = tx;
    }
}
