package org.zcw.staticsframework.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import org.zcw.staticsframework.StaticsManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by lenovo on 2018/3/28.
 */

public class Utils {
    public static long parseLong(String time) {
        try {
            return Long.parseLong(time);
        } catch (Exception ex) {
            return 0L;
        }
    }

    public static String dateFormatYMD(Context context) {
           if (context!=null) {
               SharedPreferences sharedPreferences = context.getSharedPreferences("config", MODE_PRIVATE);
               StaticsManager.currentTime = sharedPreferences.getLong("currentTime", 0);

               SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
               return dateformat.format(new Date(StaticsManager.currentTime + System.currentTimeMillis()));
           }else {
                throw  new  RuntimeException("you must initial context  please call StaticsManager.init(packagename,context)");
           }
    }

    // storage, G M K B
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static String formatStorageSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format(Locale.ROOT, "%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(Locale.ROOT, f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(Locale.ROOT, f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format(Locale.ROOT, "%d B", size);
    }
}
