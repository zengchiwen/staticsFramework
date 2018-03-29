package org.zcw.staticsframework;

import android.content.Context;

import org.zcw.staticsframework.bean.UserStaticsBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2018/3/28.
 */

public class StaticsManager {


    /**
     * 上下文对象
     */
    public  static Context mContext;
    /**
     * 当前时间
     */
    public static long currentTime;
    /**
     * 包名
     */
    public  static String mPackageName;
    /**
     * 总计流量
     */
    public static long totalByte=0;

    /**
     * 统计每个activity与framgrament的map
     */
    public static Map<String,UserStaticsBean> mUserStatics=new HashMap<>();

    /**
     * 初始化这个流量控制框架
     * @param packageName
     * @param context
     */
    public  static void init(String packageName,Context  context){
        mContext=context;
        mPackageName=packageName;
    }

}
