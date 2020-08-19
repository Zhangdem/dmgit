package com.dm.DGCat.util;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * AOP
 * 执行顺序
 * 环绕通知 >> 调用方法 >> 方法执行成功,方法执行失败
 * 调用方法之前 执行 前置通知
 * 方法执行成功 执行 后置通知
 * 方法执行失败 执行 异常通知
 **/
public class DGCatAOP implements MethodInterceptor,ThrowsAdvice,MethodBeforeAdvice, AfterReturningAdvice {

    public  Object getValueByKey(Object obj, String key) {
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            try {

                if (f.getName().endsWith(key)) {
                    return f.get(obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 没有查到时返回空字符串
        return "";
    }

    /**
     * 环绕通知
     **/
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable{
        System.out.println("===========进入around环绕方法！=========== \n");

        // 调用目标方法之前执行的动作
        System.out.println("调用方法之前: 执行！\n");

        // 调用方法的参数
        Object[] args = invocation.getArguments();
        // 调用的方法
        Method method = invocation.getMethod();
        //获取返回值类型
        Type type=method.getAnnotatedReturnType().getType();
        // 获取目标对象
        Object target = invocation.getThis();
        // 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
        Object returnValue="";
        try{
            returnValue = invocation.proceed();

//            // 得到类对象
//            Class userCla = (Class) returnValue.getClass();
//            /* 得到类中的所有属性集合 */
//            Field[] fs = userCla.getDeclaredFields();
//            for (int i = 0; i < fs.length; i++) {
//                Field f = fs[i];
//                f.setAccessible(true); // 设置些属性是可以访问的
//                try {
//
//                    if (f.getName().endsWith("count")) {
//                        System.out.println(f.get(returnValue));
//                        f.set(returnValue,"123");
//                    }
//                } catch (IllegalArgumentException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }

            System.out.println("===========结束进入around环绕方法！=========== \n");
            System.out.println("输出：" + args[0] + ";" + method + ";" + target + ";" + returnValue + "\n");
            System.out.println("around环绕方法结束：之后执行！\n");

        }catch (Exception e){ }

        return returnValue;
    }

    /**
     * 前置通知
     * 执行方法前调用
     **/
    @Override
    public void before(Method method, Object[] args, Object target)
            throws Throwable {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        System.out.println(method.getName()+":--"+df.format(new Date())+"--"+method.getClass());
    }

    /**
     * 后置通知
     * 处理完请求、返回内容
     **/
    @Override
    public void afterReturning(Object returnValue, Method method,
                               Object[] args, Object target) throws Throwable {
        // TODO Auto-generated method stub
        System.out.println("执行完毕");
    }

    /**
     * 异常通知
     * 通知方法，需要按照这种格式书写
     * @param method      可选：切入的方法
     * @param args        可选：切入的方法的参数
     * @param target      可选：目标对象
     * @param throwable   必填 : 异常子类，出现这个异常类的子类，则会进入这个通知。
     **/
    public void afterThrowing(Method method, Object[] args, Object target, Throwable throwable) {
        System.out.println("删除出错啦");
    }
}
