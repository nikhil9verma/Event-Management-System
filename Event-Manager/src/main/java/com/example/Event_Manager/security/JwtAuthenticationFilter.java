package com.example.Event_Manager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().startsWith("/auth/**");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        System.out.println(">>> ENTERING JWT FILTER");
        String authHeader = request.getHeader("Authorization");
        System.out.println("Auth header: "+authHeader);
        if(authHeader==null || !authHeader.startsWith("Bearer")){
            System.out.println("Returning from first if condition in dofilterInternal"+authHeader);
            filterChain.doFilter(request,response);
            return;
        }
        System.out.println("We got the header");
        String token = authHeader.substring(7);
        System.out.println("Now running try block");
        try{
            Claims claims= Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("Ran till now");
            String email = claims.getSubject();
            String role = claims.get("role",String.class);
            System.out.println("Ran till now");
            UsernamePasswordAuthenticationToken authentication=
                    new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_"+role))
                    );
            System.out.println("Verified Username password authentication token");
            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)

            );
            System.out.println("JWT Verification Passed: " );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            System.out.println("JWT Verification Failed: " + e.getMessage());
            e.printStackTrace(); // This helps pinpoint the exact line
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request,response);
    }

}
