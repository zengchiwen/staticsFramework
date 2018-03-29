package org.zcw.staticsframework.bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/3/29.
 */

public class UserStaticsBean implements Serializable {

    private String userStaticsName;
    private long duration;
    private int count;

    public String getUserStaticsName() {
        return userStaticsName;
    }

    public void setUserStaticsName(String userStaticsName) {
        this.userStaticsName = userStaticsName;
    }


    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
