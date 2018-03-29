package org.zcw.staticsframework.trafficstatics;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.text.TextUtils;

import org.zcw.staticsframework.StaticsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15-8-31.
 */
public class TrafficInfoProvider {
    private PackageManager pm;
    private Context context;

    public TrafficInfoProvider(Context context) {
        this.context = context;
        if (context != null) {
            this.pm = context.getPackageManager();
        } else {
            throw new RuntimeException("you must initial context  please call StaticsManager.init(packagename,context)");
        }
    }

    /**
     * 返回所有有互联网权限的应用的流量信息
     */

    public List<TrafficInfo> getTrifficInfos() {

        //获取到配置权限信息的应用程序
        synchronized (this) {
            if (pm != null) {
                List<PackageInfo> packageInfos = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS);
                //存放有Internet权限信息的应
                List<TrafficInfo> trifficInfos = new ArrayList<TrafficInfo>();
                for (PackageInfo info : packageInfos) {
                    String[] permissions = info.requestedPermissions;
                    // RLogUtils.printLogSD(info.applicationInfo.loadLabel(pm).toString());
                    if (!TextUtils.isEmpty(StaticsManager.mPackageName)) {
                        if (permissions != null && permissions.length > 0) {
                            for (String permission : permissions) {
                                if ("android.permission.INTERNET".equals(permission) && info.packageName.equals(StaticsManager.mPackageName)) { //找到应用程序里面又网络权限
                                    TrafficInfo trifficInfo = new TrafficInfo();
                                    trifficInfo.setPackgeName(info.packageName);
                                    trifficInfo.setAppname(info.applicationInfo.loadLabel(pm).toString());
                                    trifficInfo.setIcon(info.applicationInfo.loadIcon(pm));
                                    int uid = info.applicationInfo.uid;
                                    trifficInfo.setRx(TrafficStats.getUidRxBytes(uid));
                                    trifficInfo.setTx(TrafficStats.getUidTxBytes(uid));
                                    trifficInfos.add(trifficInfo);
                                    trifficInfo = null;
                                    break;
                                }
                            }
                        } else {
                            throw new RuntimeException("you must request personal permissions");
                        }
                    }else {
                        throw new RuntimeException("you must initial packageName  please call StaticsManager.init(packagename,context)");
                    }
                }

                return trifficInfos;
            }
            return new ArrayList<TrafficInfo>();
        }
    }
}
