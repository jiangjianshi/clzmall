package com.clzmall.admin.interceptor;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by bairong on 2018/11/16.
 */
@Component
@Aspect
@Slf4j
public class LogAspect_C {

    @Resource
    private HttpServletRequest request;

    long startTime = 0;

    @Pointcut("execution(public * com.clzmall.admin.controller*..*(..))")
    public void log() {
    }

    @Before("log()")
    public void beforeMethod(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
        String className = joinPoint.getTarget().getClass().getName();//全类名
        String methodName = joinPoint.getSignature().getName();//方法名
        Object[] args = joinPoint.getArgs(); // 参数值
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames(); // 参数名
        StringBuffer sb = new StringBuffer();
        if (args.length != 0 && args.length == argNames.length) {
            for (int i = 0; i < argNames.length; i++) {
                sb.append(argNames[i]).append(" = ").append(args[i]).append(" ");
            }
        }
        log.info(className + "." + methodName + "(), " + sb.toString());
    }


    //    @AfterReturning(pointcut = "log()", returning = "returnValue")
//    public void afterReturning(JoinPoint joinPoint, Object returnValue) {
//        long endTime = System.currentTimeMillis();
//        log.info("耗时:{}ms, 返回参数：{}", endTime - startTime, JSON.toJSON(returnValue));
//    }
    @AfterReturning(pointcut = "log()", returning = "returnValue")
    public void afterReturning(JoinPoint joinPoint, Object returnValue) {

//    if (StringUtils.equals(CommonPropertyUtil.getProperty("environment"), "DEV")) {
//
//    } else {
//        productEnvironmentLog(joinPoint, returnValue);
//    }

//        devEnvironmentLog(joinPoint, returnValue);
        productEnvironmentLog(joinPoint, returnValue);
    }

    /**
     * @Description: 开发环境console打印信息
     * @Param:
     * @return:
     * @Author: 关洪昌
     * @Date: 2018/7/20
     */
    private void devEnvironmentLog(JoinPoint joinPoint, Object returnValue) {
        StringBuilder sb = new StringBuilder("\nSpringMVC action report -------- ")
                .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .append(" ------------------------------\n");
        sb.append(getController(joinPoint));
        sb.append(getMethod(joinPoint));
        sb.append(getUri());
        sb.append(getParameter(joinPoint));
        sb.append("RemoteAddr  : " + getIpAddr(request) + "\n");
        sb.append(getReturn(returnValue));
        sb.append("--------------------------------------------------------------------------------\n");
        System.out.print(sb.toString());
    }

    /**
     * @Description: 正式环境打印的信息
     * @Param:
     * @return:
     * @Author: 关洪昌
     * @Date: 2018/7/20
     */
    private void productEnvironmentLog(JoinPoint joinPoint, Object returnValue) {
        StringBuilder sb = new StringBuilder();

        sb.append(request.getRequestURI()).append(", ");
        sb.append("IP: " + getIpAddr(request)).append(", [");

        Map<String, String[]> parameters = request.getParameterMap();

        for (Map.Entry<String, String[]> entity : parameters.entrySet()) {

            sb.append(String.format("%s = %s, ", entity.getKey(), StringUtils.join(entity.getValue(), ',')));
        }

        sb.delete(sb.length() - 2, sb.length()).append("]");

        log.info(sb.toString());
    }

    private Map<String, MultipartFile> getRequestFileMap(JoinPoint joinPoint) {
        Map<String, MultipartFile> fileMap = null;
        Object[] args = joinPoint.getArgs();
        for (Object object : args) {
            if (object instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) object;
                fileMap = multipartHttpServletRequest.getFileMap();
                break;
            }
        }
        return fileMap;
    }


    private String getController(JoinPoint joinPoint) {
        return new StringBuffer().append("Controller  : ").append(joinPoint.getTarget().getClass().getName()).append(".(")
                .append(joinPoint.getTarget().getClass().getSimpleName()).append(".java:1)").toString();
    }

    private String getMethod(JoinPoint joinPoint) {
        return new StringBuffer().append("\nMethod      : ").append(joinPoint.getSignature().getName()).append("\n").toString();
    }

    private String getUri() {
        String uri = request.getRequestURI();
        if (uri != null) {
            return new StringBuffer().append("url         : ").append(uri).append("\n").toString();

        }
        return "";
    }

    private String getParameter(JoinPoint joinPoint) {
        StringBuffer sb = new StringBuffer();
        Map<String, MultipartFile> fileMap = getRequestFileMap(joinPoint);
        Enumeration<String> e = request.getParameterNames();
        if (e.hasMoreElements() || (fileMap != null && fileMap.size() > 0)) {
            sb.append("Parameter   : ");
            while (e.hasMoreElements()) {
                String name = e.nextElement();
                String[] values = request.getParameterValues(name);
                if (values.length == 1) {
                    sb.append(name).append("=").append(values[0]);
                } else {
                    sb.append(name).append("[]={");
                    for (int i = 0; i < values.length; i++) {
                        if (i > 0)
                            sb.append(",");
                        sb.append(values[i]);
                    }
                    sb.append("}");
                }
                sb.append("  ");
            }
            if (fileMap != null && fileMap.size() > 0) {
                for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                    MultipartFile file = entry.getValue();
                    sb.append(entry.getKey()).append("=").append(file.getOriginalFilename());
                    sb.append(" (contentType=" + file.getContentType() + ",size=" + file.getSize() + ")");
                    sb.append("  ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getReturn(Object returnValue) {
        StringBuffer sb = new StringBuffer();
        String returnJSON = "";
        returnJSON = JSONObject.toJSONString(returnValue);
        sb.append("return      : " + returnJSON);
        sb.append("\n");
        return sb.toString();
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}
