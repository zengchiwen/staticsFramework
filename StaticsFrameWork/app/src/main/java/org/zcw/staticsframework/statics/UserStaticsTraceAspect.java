package org.zcw.staticsframework.statics;

import android.os.Handler;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.zcw.staticsframework.StaticsManager;
import org.zcw.staticsframework.annotation.UserBehaviorTrace;
import org.zcw.staticsframework.bean.UserStaticsBean;
import org.zcw.staticsframework.utils.LogUtils;

/**
 * Created by lenovo on 2018/3/27.
 */
@Aspect
public class UserStaticsTraceAspect {
    private String className;
    private String methodName;
    private String funName;
    int count=0;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    // 移除所有的msg.what为0等消息，保证只有一个循环消息队列再跑
                    handler.removeMessages(0);
                    // app的功能逻辑处理

                    count++;
                    // 再次发出msg，循环更新
                    handler.sendEmptyMessageDelayed(0, 1000);
                    break;

                case 1:
                    // 直接移除，定时器停止
                    handler.removeMessages(0);
                    UserStaticsBean staticsBean = StaticsManager.mUserStatics.get(funName);
                    staticsBean.setDuration(count);
                    LogUtils.printLogSD("UserBehaviorTrace", String.format("功能：%s,%s类的%s方法执行了，此方法点击了%d,停留了时间%d",
                            funName, className, methodName, staticsBean.getCount(),  staticsBean.getDuration()));

                    break;

                default:
                    break;
            }
        };
    };
    //定义切面的规则
    //1.就在原来应用中哪些注释的地方放到当前切面进行处理
    //execution(注释名   注释用的地方)
    @Pointcut("execution(@com.ksd.carcloud.annotation.UserBehaviorTrace * *(..))")
    public void methodAnnotatedWithUserBehaviorTrace() {
        LogUtils.printLogSD("UserBehaviorTrace", "aaaaaaaaaa");

    }


    @Around("methodAnnotatedWithUserBehaviorTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        className = methodSignature.getDeclaringType().getSimpleName();
        methodName = methodSignature.getName();
        funName = methodSignature.getMethod().getAnnotation(UserBehaviorTrace.class).value();
        //统计时间
        Object result = joinPoint.proceed();
        if (!StaticsManager.mUserStatics.containsKey(funName)) {
            UserStaticsBean userStaticsBean = new UserStaticsBean();
            if (methodName.contains("onResume")) {
                 handler.sendEmptyMessageDelayed(0,1000);
                 count=0;
            } else if (methodName.contains("onPause")) {
               handler.sendEmptyMessage(1);
            }
            userStaticsBean.setCount(1);
            StaticsManager.mUserStatics.put(funName, userStaticsBean);
        } else {
            UserStaticsBean userStaticsBean = StaticsManager.mUserStatics.get(funName);
            if (methodName.contains("onResume")) {
                handler.sendEmptyMessageDelayed(0,1000);
                count=0;
            } else if (methodName.contains("onPause")) {
                handler.sendEmptyMessage(1);
            }

            StaticsManager.mUserStatics.put(funName, userStaticsBean);
        }

        return result;
    }


}
