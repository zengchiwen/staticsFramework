package org.zcw.staticsframework.trafficstatics;

import android.content.Context;

import org.zcw.staticsframework.utils.LogUtils;

import java.util.List;

/**
 * Created by lenovo on 2017/6/13.
 */

public class TrafficUtils {


    public static  long getTotalBytes(Context context){
        TrafficInfoProvider provider=new TrafficInfoProvider(context);
        List<TrafficInfo> trifficInfos = provider.getTrifficInfos();

        if (trifficInfos.size()>0) {
            LogUtils.printLogSD("point",trifficInfos.get(0).getRx() + trifficInfos.get(0).getTx()+"B");
            return trifficInfos.get(0).getRx() + trifficInfos.get(0).getTx();
        }
        return 0L;

   }

}
