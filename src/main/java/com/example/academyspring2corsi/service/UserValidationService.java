package com.example.academyspring2corsi.service;

import com.example.academyspring2corsi.data.entity.User;
import com.example.academyspring2corsi.exception.UserValidationException;
import com.example.academyspring2corsi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidationService {
    
    private final UserRepository userRepository;
    
    public void validateUser(String username, Collection<? extends GrantedAuthority> authorities) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UserValidationException("Utente non trovato nel database"));

        
        validateUserRoles(user, authorities);
    }
    
    private void validateUserRoles(User user, Collection<? extends GrantedAuthority> authorities) {
        Set<String> tokenRoles = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .map(role -> role.replace("ROLE_", ""))
            .collect(Collectors.toSet());
            
        if (!user.getRole().contains((CharSequence) tokenRoles)) {
            throw new UserValidationException("I ruoli dell'utente non corrispondono");
        }
    }



}