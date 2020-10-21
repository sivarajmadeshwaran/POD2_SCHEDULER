package com.atlas.scheduler.gateway.configuration;

import java.io.IOException;
import java.util.StringJoiner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Value("${authorization.token.header.prefix}")
	private String headerPrefix;

	@Autowired
	private JWTUtils jwtUtils;

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		final String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader == null) {
			return null;
		}
		String token = authorizationHeader.replace(headerPrefix, "");
		String userId = jwtUtils.extractUsername(token);
		
		if (userId == null || jwtUtils.isTokenExpired(token)) {
			return null;
		}
		
		return new UsernamePasswordAuthenticationToken(userId, null,
				AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUtils.extractRoles(token)));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader == null || !authorizationHeader.startsWith(headerPrefix)) {
			filterChain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		System.out.println("Authentication -- " + authentication.getPrincipal());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

}
	