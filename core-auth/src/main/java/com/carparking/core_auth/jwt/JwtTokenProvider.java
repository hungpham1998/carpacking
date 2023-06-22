package com.carparking.core_auth.jwt;

import com.carparking.core_auth.exception.UnauthorizedException;
import com.carparking.core_auth.model.AuthToken;
import com.carparking.core_auth.model.UserPrincipal;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class JwtTokenProvider {
    private static final String CLAIM_ROLE = "roles";
    private static final String CLAIM_ADDITIONAL_DATA_KEYS = "additionalDataKeys";
    @Value("${application.auth.jwt.secret:licensekey}")
    private String jwtSecret;

    @Value("${application.auth.jwt.expire:86400000}")
    private long jwtExpirationInMs;

//    @Autowired
    private JwtProperties jwtProperties;

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal =
                (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(String.valueOf(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserIdFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    public AuthToken generateAuthToken(Authentication authentication) {
        return generateAuthToken(authentication, true);
    }

    public AuthToken generateAuthToken(
            Authentication authentication,
            boolean withRefreshToken
    ) {
        UserPrincipal userPrincipal =
                (UserPrincipal) authentication.getPrincipal();

        ZonedDateTime issuedAt = ZonedDateTime.now();
        ZonedDateTime accessExpiredAt =
                issuedAt.plus(jwtProperties.getAccessExpire(), ChronoUnit.MILLIS);

        Map<String, Object> claims = new HashMap<>();

        if (jwtProperties.isRefreshEnable()) {
            claims.put(
                    CLAIM_ROLE, userPrincipal.getAuthorities()
                            .stream()
                            .map(GrantedAuthority::toString)
                            .collect(Collectors.toList())
            );

//            if (Objects.nonNull(userPrincipal.getAdditionalData())) {
//                claims.putAll(userPrincipal.getAdditionalData());
//                claims.put(
//                        CLAIM_ADDITIONAL_DATA_KEYS, userPrincipal.getAdditionalData().keySet());
//            } else {
//                claims.put(CLAIM_ADDITIONAL_DATA_KEYS, Collections.emptyList());
//            }
        }

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(userPrincipal.getId()))
                .setIssuedAt(Date.from(issuedAt.toInstant()))
                .setExpiration(Date.from(accessExpiredAt.toInstant()))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getAccessSecret())
                .compact();

        AuthToken authToken = new AuthToken();
        authToken.setAccessToken(accessToken);

        if (jwtProperties.isRefreshEnable() && withRefreshToken) {
            ZonedDateTime refreshExpiredAt =
                    issuedAt.plus(jwtProperties.getRefreshExpire(), ChronoUnit.MILLIS);
            String refreshToken = Jwts.builder()
                    .setSubject(String.valueOf(userPrincipal.getId()))
                    .setIssuedAt(Date.from(issuedAt.toInstant()))
                    .setExpiration(Date.from(refreshExpiredAt.toInstant()))
                    .signWith(SignatureAlgorithm.HS512, jwtProperties.getRefreshSecret())
                    .compact();
            authToken.setRefreshToken(refreshToken);
        }

        return authToken;
    }

    public String getUserIdFromRefreshToken(String refreshToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtProperties.getRefreshSecret())
                    .parseClaimsJws(refreshToken)
                    .getBody()
                    .getSubject();
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException
                 | UnsupportedJwtException | IllegalArgumentException ex) {
            log.error(
                    "(getUserIdFromRefreshToken) --> {}: {}",
                    ex.getClass().getName(), ex.getMessage()
            );
            throw ex;
        }

    }

    public UserPrincipal getPrincipalFromJWT(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getAccessSecret())
                    .parseClaimsJws(token)
                    .getBody();
            String userId = claims.getSubject();
            if (Objects.isNull(userId)) {
                throw new UnauthorizedException("Null authorization subject");
            }

            Collection<? extends GrantedAuthority> authorities = extractAuthorities(claims);

            Map<String, Object> additionalData = extractAdditionalData(claims);

            return new UserPrincipal(
                    Long.valueOf(userId), null, null, true,
                    //additionalData,
                    authorities
            );
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException
                 | UnsupportedJwtException | IllegalArgumentException ex) {
            log.error(
                    "(getPrincipalFromJWT) --> {}: {}",
                    ex.getClass().getName(), ex.getMessage()
            );
            throw ex;
        }
    }

    private Collection<? extends GrantedAuthority> extractAuthorities(Claims claims) {
        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
        if (claims.containsKey(CLAIM_ROLE)) {
            authorities = ((List<String>) claims.get(CLAIM_ROLE, List.class))
                    .stream()
                    .map(o -> new SimpleGrantedAuthority(String.valueOf(o)))
                    .collect(Collectors.toList());
        }
        return authorities;
    }

    private Map<String, Object> extractAdditionalData(Claims claims) {
        Map<String, Object> additionalData = new HashMap<>();
        if (claims.containsKey(CLAIM_ADDITIONAL_DATA_KEYS)) {
            List<String> additionalDataKeys =
                    claims.get(CLAIM_ADDITIONAL_DATA_KEYS, List.class);

            for (String key : additionalDataKeys) {
                additionalData.put(key, claims.get(key));
            }
        }

        return additionalData;
    }

}
