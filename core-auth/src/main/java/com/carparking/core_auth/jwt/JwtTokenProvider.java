package com.carparking.core_auth.jwt;

import com.carparking.core_auth.model.AuthToken;
import com.carparking.core_auth.model.UserPrincipal;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class JwtTokenProvider {


	private JwtProperties jwtProperties;
	@Value("${application.auth.jwt.secret:licensekey}")
	private String jwtSecret;

	@Value("${application.auth.jwt.expire:86400000}")
	private long jwtExpirationInMs;
	private static final String CLAIM_ROLE = "roles";
	private static final String CLAIM_ADDITIONAL_DATA_KEYS = "additionalDataKeys";
	public String generateToken(Authentication authentication) {
		UserPrincipal userPrincipal =
				(UserPrincipal) authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
		return Jwts.builder()
				.setSubject(userPrincipal.getId().toString())
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

	public String getUsernameFromToken(String token) {
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

	public List<String> getRolesFromJwt(String jwt) {
		return (List<String>) Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().get("authorities");
	}
	public AuthToken generateAuthToken(Authentication authentication) {
		return generateAuthToken(authentication, true);
	}
	public AuthToken generateAuthToken(
			Authentication authentication,
			boolean withRefreshToken
	) {
		log.info("(generateAuthToken) principal: {}", authentication.getPrincipal());
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

			if (Objects.nonNull(userPrincipal.getAdditionalData())) {
				claims.putAll(userPrincipal.getAdditionalData());
				claims.put(
						CLAIM_ADDITIONAL_DATA_KEYS,
						userPrincipal.getAdditionalData().keySet());
			} else {
				claims.put(CLAIM_ADDITIONAL_DATA_KEYS, Collections.emptyList());
			}
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
}
