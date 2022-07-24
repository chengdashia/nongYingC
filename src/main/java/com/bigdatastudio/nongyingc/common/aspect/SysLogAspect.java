package com.bigdatastudio.nongyingc.common.aspect;


import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;import com.bigdatastudio.nongyingc.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;


/**
 * @author 成大事
 * @since 2022/7/24 17:58
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect {


    /**
     * 统计请求的处理时间
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    //@Autowired
    //private LogInfoService logInfoService;
    //
    //@Autowired
    //private LogErrorInfoService logErrorInfoService;

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.bigdatastudio.nongyingc.common.aspect.SysLog)")
    public void logPointCut() {
    }

    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
    @Pointcut("execution(* com.bigdatastudio.nongyingc.controller..*.*(..))")
    public void exceptionLogPointCut() {
    }

    @Before("logPointCut()")
    public void doBefore() {
        // 接收到请求，记录请求开始时间
        startTime.set(System.currentTimeMillis());
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     * [joinPoint, keys]
     */
    @AfterReturning(value = "logPointCut()", returning = "keys")
    public void doAfterReturning(JoinPoint joinPoint, Object keys) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        }

        //try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();

            // 获取切入点所在的方法
            Method method = signature.getMethod();

            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();

            // 获取操作
            SysLog sysLog = method.getAnnotation(SysLog.class);
            String module = "";
            String type = "";
            String desc = "";
            if (Objects.nonNull(sysLog)) {
               module = sysLog.module();
               type = sysLog.type();
               desc = sysLog.desc();
            }
            // 请求的方法名
            String methodName = className + "." + method.getName();
            // 请求参数
            String reqParam = JSON.toJSONString(getParameter(method, joinPoint.getArgs()));
            // 返回结果
            String result = JSON.toJSONString(keys);
            // 请求用户ID
            String userId = StpUtil.getLoginIdAsString();
            // 请求用户名称
            String userName = StpUtil.getTokenName();
            // 请求IP
            String ip = null;
            if (request != null) {
                ip = IPUtil.getIpAddress(request);
            }
            // 请求URI
            String uri = null;
            if (request != null) {
                uri = request.getRemoteAddr();
            }
            long time = System.currentTimeMillis() - startTime.get();


            //logInfoService.save(
            //        LogInfo.builder()
            //                .module(module)
            //                .type(type)
            //                .message(desc)
            //                .method(methodName)
            //                .reqParam(reqParam)
            //                .resParam(result)
            //                .userId(userId)
            //                .userName(userName)
            //                .ip(ip)
            //                .uri(uri)
            //                .createTime(LocalDateTime.now())
            //                .takeUpTime(time)
            //                .build()
            //);

            log.info("请求模块：  "+ module);
            log.info("请求类型：  "+ type);
            log.info("请求描述：  "+ desc);
            log.info("请求的方法名：  "+ methodName);
            log.info("请求参数:   "+reqParam);
            log.info("请求用户ID:   "+ userId);
            log.info("请求用户名称:   "+ userName);
            log.info("返回结果：   "+result);
            log.info("请求路径:   "+uri);
            log.info("请求的ip地址： "+ip);
            log.info("耗时： "+time);
            log.info("\n");


        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     * [joinPoint, e]
     */
    //@AfterThrowing(pointcut = "exceptionLogPointCut()", throwing = "e")
    //public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
    //    // 获取RequestAttributes
    //    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    //
    //    // 从获取RequestAttributes中获取HttpServletRequest的信息
    //    HttpServletRequest request = null;
    //    if (requestAttributes != null) {
    //        request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
    //    }
    //
    //    try {
    //        // 从切面织入点处通过反射机制获取织入点处的方法
    //        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    //
    //        // 获取切入点所在的方法
    //        Method method = signature.getMethod();
    //
    //        // 获取请求的类名
    //        String className = joinPoint.getTarget().getClass().getName();
    //
    //        // 请求的方法名
    //        String methodName = className + "." + method.getName();
    //        // 请求参数
    //        String reqParam = JSON.toJSONString(getParameter(method, joinPoint.getArgs()));
    //        // 异常名称
    //        String exceptionName = e.getClass().getName();
    //
    //        // 请求用户ID
    //        String userId = StpUtil.getLoginIdAsString();
    //        // 请求用户名称
    //        String userName = StpUtil.getTokenName();
    //        // 请求IP
    //        String ip = null;
    //        if (request != null) {
    //            ip = IPUtil.getIpAddress(request);
    //        }
    //        // 请求URI
    //        String uri = null;
    //        if (request != null) {
    //            uri = request.getRemoteAddr();
    //        }
    //        // 异常信息
    //        String exceptionMsg = stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace());
    //        //logErrorInfoService.save(
    //        //        LogErrorInfo.builder()
    //        //                .reqParam(reqParam) // 请求参数
    //        //                .method(methodName) // 请求方法名
    //        //                .name(exceptionName) // 异常名称
    //        //                .message(exceptionMsg) // 异常信息
    //        //                .userId(userId) // 操作员ID
    //        //                .userName(userName) // 操作员名称
    //        //                .uri(uri) // 操作URI
    //        //                .ip(ip) // 操作员IP
    //        //                .createTime(LocalDateTime.now()) // 发生异常时间
    //        //                .build()
    //        //);
    //
    //        log.error("请求的方法名：  "+ methodName);
    //        log.error("请求参数:   "+reqParam);
    //        log.error("请求用户ID:   "+ userId);
    //        log.error("请求用户名称:   "+ userName);
    //        log.error("异常名称：   "+exceptionName);
    //        log.error("异常信息：   "+exceptionMsg);
    //        log.error("请求路径:   "+uri);
    //        log.error("请求的ip地址： "+ip);
    //        log.info("发生异常时间： "+ LocalDateTime.now());
    //        log.error("\n");
    //    } catch (Exception e2) {
    //        e2.printStackTrace();
    //    }
    //}
    //

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (StringUtils.isNotEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }

    /**
     * 转换异常信息为字符串
     * [exceptionName, exceptionMessage, elements]
     * java.lang.String
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuilder str = new StringBuilder();
        for (StackTraceElement stet : elements) {
            str.append(stet).append("<br/>");
        }
        return exceptionName + ":" + exceptionMessage + "<br/>" + str;
    }
}
