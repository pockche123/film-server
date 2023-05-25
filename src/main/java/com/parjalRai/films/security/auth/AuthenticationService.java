package com.parjalRai.films.security.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.parjalRai.films.model.Role;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.security.JwtService;
import com.parjalRai.films.security.token.Token;
import com.parjalRai.films.security.token.TokenRepository;
import com.parjalRai.films.security.token.TokenType;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserEntityRepository userRepository;

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Role role = determineUserRole(request);

        var user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        var savedUser= userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    private Role determineUserRole(RegisterRequest request) {
        // Logic to determine the user's role based on conditions or criteria
        // For example:
        if (request.getEmail().endsWith("@admin.com")) {
            return Role.ADMIN;
        } else {
            return Role.USER;
        }
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(UserEntity user){
        var validUserTokens = tokenRepository.findAllValidTokensByUsername(user.getUsername());
         if(validUserTokens.isEmpty()){
             return;
         }else{
             validUserTokens.forEach(t ->{
                 t.setExpired(true);
                 t.setRevoked(true);
             });
             tokenRepository.saveAll(validUserTokens);
         }
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        var token = Token.builder()
                .userEntity(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    
    public void logout() {
        Optional<UserEntity> currentUser = getCurrentUser();
        currentUser.ifPresent(this::revokeAllUserTokens);
    }

    public void refreshToken(
        HttpServletRequest request,
        HttpServletResponse response) throws  IOException  {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {//after && means that the user is not yet authenticated
            var user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
            if (jwtService.isTokenValid(refreshToken, user)) {
              var accessToken = jwtService.generateToken(user);
              revokeAllUserTokens(user);
              saveUserToken(user, accessToken);
              var authResponse = AuthenticationResponse.builder()
                                  .token(accessToken)
                                  .refreshToken(refreshToken)
                                 .build();

               new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }

    }
    
}

    public Optional<UserEntity> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }



    public String getUsernameFromToken(String token) {
        return jwtService.extractUsername(token);
    }

}