/*
 * @(#)JwtRequestFilter.java 2023. 04. 16.
 *
 * Copyright 2023 Inlab Corp. All rights Reserved.
 * Inlab PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.study.backendprojectboard.security.filter;

import com.study.backendprojectboard.security.factory.JwtFactory;
import com.study.backendprojectboard.security.model.UserContext;
import com.study.backendprojectboard.security.service.AuthTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticateFilter extends OncePerRequestFilter {

    private final AuthTokenService authTokenService;
    private final JwtFactory jwtFactory;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = authTokenService.getAccessTokenFromHttpHeader();
        if (StringUtils.hasText(accessToken)
                && jwtFactory.isValidateToken(accessToken)
        ) {
            UserContext userContext = jwtFactory.getUserContextFromToken(accessToken);
            // 현재 SecurityContextHolder 에 인증객체가 있는지 확인
            if (userContext != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}