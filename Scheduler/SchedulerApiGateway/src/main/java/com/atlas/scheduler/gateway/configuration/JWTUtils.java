package com.atlas.scheduler.gateway.configuration;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.atlas.scheduler.gateway.model.UserDetail;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtils {

	@Value("${jwt.secret.key}")
	private String jwtSecretKey;

	@Value("${jwt.token.expiration}")
	private long tokenExpiration;
	
	Function<Claims, Object> getRolesFromClaims =  c -> c.get("Roles") ;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public String extractRoles(String token){
		return  (String) extractClaim(token, getRolesFromClaims);
	}
	
	

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
	}

	public String generateToken(UserDetail user) {
		Claims claims=Jwts.claims().setSubject(user.getUsername());
		claims.put("Roles",user.getRoles());
		return createToken(claims);
	}

	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private String createToken(Map<String,Object> claims) {
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + tokenExpiration * 1000))
				.signWith(SignatureAlgorithm.HS256, jwtSecretKey).compact();
	}

	
}
