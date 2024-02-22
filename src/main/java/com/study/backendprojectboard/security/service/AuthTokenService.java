package com.study.backendprojectboard.security.service;

import com.study.backendprojectboard.security.factory.JwtFactory;
import com.study.backendprojectboard.security.token.JwtToken;
import com.study.backendprojectboard.security.util.ContextUtil;
import com.study.backendprojectboard.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthTokenService {

    private final JwtFactory jwtFactory;

    public JwtToken generateToken(User user) {
        JwtToken jwtToken = jwtFactory.generateToken(user);

        setResponseJwt(jwtToken);
        return jwtToken;
    }

    public JwtToken setResponseJwt(JwtToken jwtToken) {
        ContextUtil.createCookie(JwtFactory.NAME_ACCESS_TOKEN, jwtToken.getAccessToken(), jwtToken.getAccessTokenRemainingTime());

        ContextUtil.createCookie(JwtFactory.NAME_REFRESH_TOKEN, jwtToken.getRefreshToken(),
                jwtToken.getRefreshTokenRemainingTime());

        return jwtToken;
    }
}
