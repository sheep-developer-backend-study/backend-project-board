package com.study.backendprojectboard.security.token;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JwtToken implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    private String tokenId;
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private LocalDateTime accessTokenExpiredDate;
    private LocalDateTime refreshTokenExpiredDate;

    public int getAccessTokenRemainingTime() {
        return (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), accessTokenExpiredDate);
    }

    public int getRefreshTokenRemainingTime() {
        return (int)ChronoUnit.SECONDS.between(LocalDateTime.now(), refreshTokenExpiredDate);
    }
}