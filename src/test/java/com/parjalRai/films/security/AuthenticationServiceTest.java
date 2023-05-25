package com.parjalRai.films.security;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.parjalRai.films.model.Role;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.security.auth.AuthenticationResponse;
import com.parjalRai.films.security.auth.AuthenticationService;
import com.parjalRai.films.security.auth.LoginRequest;
import com.parjalRai.films.security.auth.RegisterRequest;
import com.parjalRai.films.security.token.Token;
import com.parjalRai.films.security.token.TokenRepository;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserEntityRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testRegister_CreateANewUser_IfSuccess() {
        // Mocking the necessary objects and data
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setEmail("testuser@example.com");
        request.setPassword("password");

        UserEntity savedUser = new UserEntity();
        savedUser.setUsername("testuser");
        savedUser.setEmail("testuser@example.com");
        savedUser.setPassword("hashedPassword");
        savedUser.setRole(Role.USER);

        String jwtToken = "generatedJwtToken";
        String refreshToken = "generatedRefreshToken";

        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);
        when(jwtService.generateToken(savedUser)).thenReturn(jwtToken);
        when(jwtService.generateRefreshToken(savedUser)).thenReturn(refreshToken);

        // Call the register method
        AuthenticationResponse response = authenticationService.register(request);

        // Verify the result
        assertNotNull(response);
        assertEquals(jwtToken, response.getToken());
        assertEquals(refreshToken, response.getRefreshToken());

        // Verify that the user and token were saved
        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userCaptor.capture());
        verify(tokenRepository).save(any(Token.class));

        UserEntity capturedUser = userCaptor.getValue();
        assertNotNull(capturedUser);
        assertEquals("testuser", capturedUser.getUsername());
        assertEquals("testuser@example.com", capturedUser.getEmail());
        assertEquals("hashedPassword", capturedUser.getPassword());
        assertEquals(Role.USER, capturedUser.getRole());
        // Add additional assertions for the Token entity if needed
    }

    @Test
    public void testLogin_GeneratesToken_ReturnsLoginSuccessful() {
        // Mocking the necessary objects and data
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("hashedPassword");
        user.setRole(Role.USER);

        String jwtToken = "generatedJwtToken";
        String refreshToken = "generatedRefreshToken";

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(jwtToken);
        when(jwtService.generateRefreshToken(user)).thenReturn(refreshToken);

        AuthenticationResponse response = authenticationService.login(request);

        // Verify the result
        assertNotNull(response);
        assertEquals(jwtToken, response.getToken());
        assertEquals(refreshToken, response.getRefreshToken());

        verify(tokenRepository).save(any(Token.class));

        verify(tokenRepository).findAllValidTokensByUsername("testuser");
    }

    @Test
    void getCurrentUser_AuthenticatedUser_ReturnsUserEntity() {
        // Mocking variables
        String username = "testuser";
        UserEntity userEntity = new UserEntity();

        // Mocking SecurityContextHolder and Authentication
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mocking getUsername() and findByUsername() methods
        when(authentication.getName()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        // Call the method to be tested
        Optional<UserEntity> result = authenticationService.getCurrentUser();

        // Verify that the necessary methods were called
        verify(authentication, times(1)).getName();
        verify(userRepository, times(1)).findByUsername(username);

        // Assert the result
        assertEquals(Optional.of(userEntity), result);
    }

   

 


}
