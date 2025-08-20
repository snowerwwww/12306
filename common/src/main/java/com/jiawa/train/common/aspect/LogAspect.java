package com.jiawa.train.common.aspect;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Aspect
@Component
public class LogAspect {
    public void logAspect(){
        System.out.println("common LogAspect");
    }

    private final static Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    //定义一个切点
    @Pointcut("execution(public * com.jiawa..*Controller.*(..))")
    public void controllerPointcut(){
    }

    @Before("controllerPointcut()")
    public void doBefore(JoinPoint joinPoint){
        //增加日志流水号
        MDC.put("LOG_ID",System.currentTimeMillis()+ RandomUtil.randomString(3));
        //开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        //打印请求信息
        LOG.info("----------开始---------");
        LOG.info("请求地址:{} {}",request.getRequestURL().toString());
        LOG.info("类名方法:{}.{}",signature.getDeclaringTypeName(),name);
        LOG.info("远程地址:{}",request.getRemoteAddr());
        //打印请求参数
        Object[] args = joinPoint.getArgs();
        //排除特殊类型的参数，如文件类型
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if(args[i] instanceof ServletRequest
            || args[i] instanceof ServletResponse
                    ||args[i] instanceof MultipartFile){
                continue;
            }
            arguments[i]=args[i];
        }
        //排除字段，敏感字段太长的字段不显示：手机号，身份证号，邮箱，密码.....
        String[] excludeProperties = {"mobile"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        LOG.info("请求参数：{}", JSONObject.toJSONString(arguments,excludefilter));
    }

    //环绕
    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 1. 记录方法开始时间
        long startTime = System.currentTimeMillis();

        // 2. 执行目标方法并获取返回值
        Object result = proceedingJoinPoint.proceed();

        // 3. 敏感字段过滤（蓝色高亮部分）
        // 排除字段：手机号、身份证等（实际开发中需扩展更多字段）
        String[] excludeProperties = {"mobile"};

        // 创建JSON过滤器
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);  // 添加需排除的字段

        // 4. 日志记录（过滤后的结果 + 耗时）
        LOG.info("返回结果：{}", JSONObject.toJSONString(result, excludefilter)); // 自动跳过mobile字段
        LOG.info("-------------- 结束 耗时：{} ms------------", System.currentTimeMillis() - startTime);

        // 5. 返回原始结果（不影响实际接口返回）
        return result;
    }
}