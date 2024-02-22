package com.study.backendprojectboard.security.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.StringJoiner;

public class ContextUtil {
    public static HttpServletRequest getHttpRequest() {
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (servletRequestAttributes == null) {
            return null;
        }
        return servletRequestAttributes.getRequest();
    }
    public static HttpServletResponse getResponse() {
        // log.debug("getResponse -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (servletRequestAttributes == null) {
            return null;
        }
        return servletRequestAttributes.getResponse();
    }

    public static void createCookie(String name, String value, int maxAge) {
        HttpServletRequest request = getHttpRequest();
        HttpServletResponse response = getResponse();
        boolean isSecure = request.isSecure();

        if(!isSecure) {
            StringJoiner sj = new StringJoiner(", ");
            java.util.Collections.list(request.getHeaderNames()).forEach(hn -> {
                if(hn.toLowerCase().startsWith("x-forward")){
                    sj.add(hn + ":" + request.getHeader(hn));
                }
            });
        }

        ResponseCookie cookie = ResponseCookie.from(name, value)
                .maxAge(maxAge)
                .httpOnly(true)
                .domain(getSubdomain())
                .sameSite(isSecure ? "None" : null)
                .secure(isSecure)
                .path("/")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public static String getSubdomain() {
        HttpServletRequest request = getHttpRequest();
        if (request == null) {
            return null;
        }

        return request.getServerName().replaceAll(".*\\.(?=.*\\.)", "");
    }
}
