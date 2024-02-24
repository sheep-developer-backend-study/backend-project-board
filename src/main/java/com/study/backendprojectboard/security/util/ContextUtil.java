package com.study.backendprojectboard.security.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class ContextUtil {
    public static void deleteCookie(Cookie cookie) {
        HttpServletResponse response = getResponse();
        cookie.setDomain(getSubdomain()); // 과정에 속하지 않은 사용자 접속 시 쿠키 삭제
        cookie.setValue("");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static void deleteCookie(String name) {
        HttpServletRequest request = getHttpRequest();

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    deleteCookie(cookie);
                }
            }
        }
    }
    public static Cookie getCookie(String name) {
        HttpServletRequest request = getHttpRequest();
        return getCookie(request, name);
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        if(request == null){
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static List<Cookie> getCookies(HttpServletRequest request, String name) {
        if(request == null){
            return Collections.emptyList();
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(name))
                .collect(Collectors.toList());
    }

    public static List<Cookie> getCookies(String name) {
        HttpServletRequest request = getHttpRequest();
        return getCookies(request, name);
    }
    public static HttpServletRequest getHttpRequest() {
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (servletRequestAttributes == null) {
            return null;
        }
        return servletRequestAttributes.getRequest();
    }
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (servletRequestAttributes == null) {
            return null;
        }
        return servletRequestAttributes.getResponse();
    }

    public static String getHeader(String headerName) {
        HttpServletRequest request = getHttpRequest();
        if (null == request) {
            return null;
        }
        return request.getHeader(headerName);
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

        // Client -> Server -> Client
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
