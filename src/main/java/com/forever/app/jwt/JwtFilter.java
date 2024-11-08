package com.forever.app.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forever.app.auth.User;
import com.forever.app.auth.UserRepo;
import com.forever.app.dto.TokenDetails;
import com.forever.app.security.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ApplicationContext applicationContext;
	private final ObjectMapper objectMapper=new ObjectMapper();
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		String token = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = jwtService.extractUsername(token);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = applicationContext.getBean(MyUserDetailsService.class)
					.loadUserByUsername(username);

			if (jwtService.validateToken(token, userDetails)) {

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
				TokenDetails tokenDetails = new TokenDetails();
				List<String> roles = new ArrayList<String>();
				
				roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
							.collect(Collectors.toList());
				User user=userRepo.findByEmail(username);
				tokenDetails.setUserId(user.getUserId());
				tokenDetails.setName(user.getName());
				tokenDetails.setEmail(username);
				tokenDetails.setValid(true);
				tokenDetails.setRoles(roles);
				String userdetailsjson=objectMapper.writeValueAsString(tokenDetails);
				
				request.setAttribute("X-User-Details", userdetailsjson); 

			}
		}

		filterChain.doFilter(request, response);

	}

}
