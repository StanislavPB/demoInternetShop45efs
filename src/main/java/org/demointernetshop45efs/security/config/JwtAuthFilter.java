package org.demointernetshop45efs.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.demointernetshop45efs.security.service.CustomUserDetailService;
import org.demointernetshop45efs.security.service.InvalidJwtException;
import org.demointernetshop45efs.security.service.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailService customUserDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            String jwt = getTokenFromRequest(request);

            if (jwt != null && jwtTokenProvider.validateToken(jwt)){
                String userName = jwtTokenProvider.getUsernameFromJwt(jwt);
                UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (InvalidJwtException e){
            log.error("ОШИБКА !!! " + e.getMessage());
            return;
        }

        filterChain.doFilter(request,response);
    }

    private String getTokenFromRequest(HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");
        // если в запросе есть jwt, то тогда в запросе будет присутствовать
        // строка, которая выглядит так: "Bearer jkhadfgvckajdsfgvkuweyftiusddhjsfvjahfvbjk"
        // то есть нам надо из этой строки взять все до конца начиная с первого символа после "Bearer "

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
