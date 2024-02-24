package com.study.backendprojectboard.security.factory;

import com.study.backendprojectboard.security.model.UserContext;
import com.study.backendprojectboard.security.token.JwtToken;
import com.study.backendprojectboard.user.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
public class JwtFactory {
    public static final String NAME_ACCESS_TOKEN = "ACCESS-TOKEN";
    public static final String NAME_REFRESH_TOKEN = "REFRESH-TOKEN";
    private JwtParser jwtParser;

    private String getSecret() {
        return "49ecc20941334ab2b4184c7ca851f4c4";
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(getSecret().getBytes());
    }

    private String shortUuid() { // len : 13
        UUID uuid = UUID.randomUUID();
        String id = Long.toString(
                uuid.getMostSignificantBits(),
                Character.MAX_RADIX
        ).replace("-", "");

        int limit = 13;
        if (id.length() > limit) {
            id = id.substring(0, limit);
        } else {
            while (id.length() < limit) {
                id += new Random().nextInt(9);
            }
        }

        return id;
    }

    public JwtToken generateToken(User user) {
        String tokenId = user.getUserId() + "-" + this.shortUuid();
        if (tokenId.length() > 32) {
            tokenId = tokenId.substring(0, 32);
        }

        String accessToken = generateAccessToken(tokenId, user);
        String refreshToken = generateRefreshToken(tokenId, user);
        return JwtToken.builder()
                .tokenId(tokenId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getUserId())
                .accessTokenExpiredDate(this.getAccessTokenExpirationTime())
                .refreshTokenExpiredDate(this.getRefreshTokenExpirationTime())
                .build();
    }


    public String generateAccessToken(String tokenId, User user) {
        Set<String> roles = new HashSet<>();

        Claims claims = new DefaultClaims()
                .setId(tokenId);
        claims.put("userId", user.getUserId());
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());
        claims.put("userRole", roles);

        return Jwts.builder()
                .setClaims(claims)           // 토큰 정보저장
                .setIssuedAt(new Date())    // 토큰 발생시간
                .setExpiration(asDate(this.getAccessTokenExpirationTime()))
                .signWith(this.getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String tokenId, User user) {
        Claims claims = new DefaultClaims()
                .setId(tokenId);
        claims.put("userId", user.getUserId());
        claims.put("email", user.getEmail());
        return Jwts.builder()
                .setClaims(claims)            // 토큰 정보저장
                .setIssuedAt(this.getNow())    // 토큰 발생시간
                .setExpiration(asDate(this.getRefreshTokenExpirationTime()))
                .signWith(this.getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private LocalDateTime getAccessTokenExpirationTime() {
        return LocalDateTime.now().plusHours(1);
    }

    private LocalDateTime getRefreshTokenExpirationTime() {
        return LocalDateTime.now().plusDays(1);
    }

    private Date getNow() {
        return new Date(System.currentTimeMillis());
    }

    private Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public JwtParser getJwtParser() {
        if (jwtParser == null) {
            jwtParser = Jwts.parserBuilder()
                    .setSigningKey(
                            Base64.getEncoder()
                                    .encodeToString(
                                            getSecret().getBytes()
                                    )
                    )
                    .build();
        }
        return jwtParser;
    }

    public Claims getClaimsFromToken(String token) {
        try {
            return getJwtParser()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Bad credentials 1 : " + token, e);
        }
    }

    public UserContext getUserContextFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        String tokenId = claims.getId();
        Long userId = claims.get("userId", Long.class);
        String email = claims.get("email", String.class);
        String username = claims.get("username", String.class);

        Collection<?> userRole = claims.get("userRole", Collection.class);

        // email -> username으로 변경
        UserContext userContext = new UserContext(new User(userId, username, null, email));
        return userContext;
    }

    public boolean isValidateToken(String token) {
        return !isTokenExpired(token);
    }
    public boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.before(this.getNow());
    }
    public boolean isTokenExpired(String token) {
        return isTokenExpired(
                this.getClaimsFromToken(token)
        );
    }
}
