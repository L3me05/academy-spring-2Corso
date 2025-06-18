package com.example.academyspring2corsi.filter;

import com.example.academyspring2corsi.service.UserValidationService;
import com.example.academyspring2corsi.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Collection;
import com.example.academyspring2corsi.exception.UserValidationException;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;
    private final UserValidationService userValidationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        
        try {
            if (jwtUtil.validateToken(jwt)) {
                String username = jwtUtil.extractUsername(jwt);
                Collection<? extends GrantedAuthority> authorities = 
                    jwtUtil.extractAuthorities(jwt);
                
                // Validazione database
                userValidationService.validateUser(username, authorities);

                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
                    
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (UserValidationException e) {
            log.warn("Validazione utente fallita: {}", e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Validazione utente fallita: " + e.getMessage());
            return;
        } catch (Exception e) {
            log.error("Errore durante la validazione del token: {}", e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        filterChain.doFilter(request, response);
    }
}