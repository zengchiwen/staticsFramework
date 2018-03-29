package org.zcw.staticsframework.statics;



import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.zcw.staticsframework.StaticsManager;
import org.zcw.staticsframework.annotation.NetStaticsTrace;
import org.zcw.staticsframework.trafficstatics.TrafficUtils;
import org.zcw.staticsframework.utils.LogUtils;
import org.zcw.staticsframework.utils.Utils;
/**
 * Created by lenovo on 2018/3/27.
 */
@Aspect
public class NetStaticsTraceAspect {
    private String className;
    private String methodName;
    private String funName;
    private long beforeTotalByte;
    private long afterTotalByte;
    private long tempByte;

    //定义切面的规则
    //1.就在原来应用中哪些注释的地方放到当前切面进行处理
    //execution(注释名   注释用的地方)
    @Pointcut("execution(@ org.zcw.netstaticsframework.annotation.NetStaticsTrace * *(..))")
    public void methodAnnotatedWithNetStaticsTrace() {
        LogUtils.printLogSD("point", "aaaaaaaaaa");

    }



    @Around("methodAnnotatedWithNetStaticsTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        className = methodSignature.getDeclaringType().getSimpleName();
        methodName = methodSignature.getName();
        funName = methodSignature.getMethod().getAnnotation(NetStaticsTrace.class).value();

        //统计时间

        beforeTotalByte = TrafficUtils.getTotalBytes(StaticsManager.mContext);
        LogUtils.printLogSD("point","之前流量:"+beforeTotalByte);
        Object result = joinPoint.proceed();
        afterTotalByte = TrafficUtils.getTotalBytes(StaticsManager.mContext);
        tempByte = afterTotalByte - beforeTotalByte;
        StaticsManager.totalByte= StaticsManager.totalByte + tempByte;
        LogUtils.printLogSD("point", String.format("功能：%s,%s类的%s方法执行了，此过程中消费流量%s 总流量%s,共计消耗流量%s",
                funName, className, methodName, Utils.formatStorageSize(tempByte), StaticsManager.totalByte+"",Utils.formatStorageSize(StaticsManager.totalByte )));

        return result;
    }


}
