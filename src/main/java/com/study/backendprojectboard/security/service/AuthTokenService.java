package com.study.backendprojectboard.security.service;

import com.study.backendprojectboard.security.factory.JwtFactory;
import com.study.backendprojectboard.security.token.JwtToken;
import com.study.backendprojectboard.security.util.ContextUtil;
import com.study.backendprojectboard.user.model.User;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthTokenService {

    private final JwtFactory jwtFactory;
    public JwtToken generateToken(User user) {
        // 1. 인증된 사용자의 정보로 Token을 만든다.
        JwtToken jwtToken = jwtFactory.generateToken(user);

        // 2. Token을 Cookie에 저장한다.
        setResponseJwt(jwtToken);
        return jwtToken;
    }

    public JwtToken setResponseJwt(JwtToken jwtToken) {
        ContextUtil.createCookie(JwtFactory.NAME_ACCESS_TOKEN, jwtToken.getAccessToken(), jwtToken.getAccessTokenRemainingTime());

        ContextUtil.createCookie(JwtFactory.NAME_REFRESH_TOKEN, jwtToken.getRefreshToken(),
                jwtToken.getRefreshTokenRemainingTime());

        return jwtToken;
    }

    public String getAccessTokenFromHttpHeader() {
        // header: bearer
        {
            String bearerToken = ContextUtil.getHeader("Authorization");
            String bearerPrefix = "Bearer ";
            if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(bearerPrefix)){
                return bearerToken.substring(bearerPrefix.length());
            }
        }

        // header: custom
        {
            String accessToken = ContextUtil.getHeader(JwtFactory.NAME_ACCESS_TOKEN);
            if(StringUtils.hasText(accessToken)){
                return accessToken;
            }
        }

        // cookie
        {
            List<Cookie> cookies = ContextUtil.getCookies(JwtFactory.NAME_ACCESS_TOKEN);
            if(cookies.size() > 1){
                cookies.forEach(cookie -> ContextUtil.deleteCookie(cookie));
                throw new RuntimeException();
            }

            Cookie cookie = cookies == null || cookies.isEmpty()
                    ? null
                    : cookies.get(0);

            if(cookie == null){
                return null;
            }
            String accessToken = cookie.getValue();
            return accessToken;
        }
    }
}
