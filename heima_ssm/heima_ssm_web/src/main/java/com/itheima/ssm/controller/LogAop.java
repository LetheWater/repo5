package com.itheima.ssm.controller;

import com.itheima.ssm.domain.SysLog;
import com.itheima.ssm.service.ILogAopService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Controller
@Aspect
public class LogAop {

    @Autowired
    private ILogAopService logAopService;

    @Autowired
    private HttpServletRequest request;

    private Date visitTime; // 开始时间
    private Class clazz; // 访问的类
    private Method method; // 访问的方法

    @Pointcut("execution(* com.itheima.ssm.controller.*.*(..))")
    public void serviceMethods() {
    }

    @Pointcut("execution(* com.itheima.ssm.controller.SysLogController.*(..))")
    public void serviceMethods2() {
    }

    @Pointcut("serviceMethods() && !serviceMethods2()")
    public void serviceMethods3() {
    }

    //前置通知 主要是获取开始时间，执行的类是哪一个，执行的是哪一个方法
    @Before("serviceMethods3()")
    public void doBefore(JoinPoint jp) throws NoSuchFieldException, NoSuchMethodException {
        visitTime = new Date(); // 当前时间就是开始访问的时间
        clazz = jp.getTarget().getClass(); // 具体访问的类
        String methondName = jp.getSignature().getName(); // 获取访问的方法名
        Object[] args = jp.getArgs(); // 获取访问的方法的参数
        if(args == null || args.length == 0){
            method = clazz.getMethod(methondName); //只能获取无参数的方法
        }else{
            Class[] classArgs = new Class[args.length];
            for(int i =0;i<args.length;i++){
                classArgs[i] = args[i].getClass();
            }
            clazz.getMethod(methondName,classArgs);
        }
    }

    //后置通知
    @After("serviceMethods3()")
    public void doAfter(JoinPoint jp) throws Exception {
        long time = System.currentTimeMillis()-visitTime.getTime(); //获取访问的时长

        String url = "";
        //获取url
        if(clazz!=null&&method!=null&&clazz!=LogAop.class){
            //1.获取类上的
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if(classAnnotation!=null){
                String[] classValue = classAnnotation.value();
                //2.获取方法上的
                RequestMapping methodAnnotation = (RequestMapping) method.getAnnotation(RequestMapping.class);
                if(methodAnnotation!=null){
                    String[] methodValue = methodAnnotation.value();
                    url=classValue[0]+methodValue[0];

                    //获取访问的ip
                    String ip = request.getRemoteAddr();

                    //获取当前操作者
                    SecurityContext context = SecurityContextHolder.getContext();// 从上下文中获得当前登录的用户
                    User user = (User) context.getAuthentication().getPrincipal();
                    String username = user.getUsername();

                    //将日志相关信息封装到SysLog对象
                    SysLog sysLog = new SysLog();
                    sysLog.setExecutionTime(time); //执行时长
                    sysLog.setIp(ip);// 访问ip
                    sysLog.setMethod("[类型]" + clazz.getName() + "[方法名]" + method.getName());// 访问方法
                    sysLog.setUrl(url);//执行路径
                    sysLog.setUsername(username);//操作者
                    sysLog.setVisitTime(visitTime);//开始时间

                    // 调用service完成操作
                    logAopService.save(sysLog);
                }
            }
        }
    }
}
