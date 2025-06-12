package com.example.academyspring2corsi.service;

import com.example.academyspring2corsi.data.entityBasicAuth.Users;
import com.example.academyspring2corsi.repository.basicAuth.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("User not found "+ username));
        return User.builder()
                .username(users.getUsername())
                .password(users.getPassword())
                .roles(users.getRole())
                .build();
    }
}