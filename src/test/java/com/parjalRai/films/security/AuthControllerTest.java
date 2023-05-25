package com.parjalRai.films.security;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import com.parjalRai.films.model.Role;

import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.security.auth.AuthController;
import com.parjalRai.films.security.auth.AuthenticationResponse;
import com.parjalRai.films.security.auth.AuthenticationService;
import com.parjalRai.films.security.auth.LoginRequest;
import com.parjalRai.films.security.auth.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationService authService;

    @InjectMocks
    private AuthController authController;
    
    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private UserEntityRepository userRepository;

    @Test
    void register_ReturnsAuthenticationResponseWithHttpStatusOK() {
        // Mocking variables
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .role(Role.USER)
                .build();
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token("token")
                .refreshToken("refreshToken")
                .build();

        // Set up the mocks
        when(authService.register(registerRequest)).thenReturn(authenticationResponse);

        // Call the method to be tested
        ResponseEntity<AuthenticationResponse> responseEntity = authController.register(registerRequest);

        // Verify that the necessary method was called
        verify(authService).register(registerRequest);

        // Assert the response entity
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(authenticationResponse, responseEntity.getBody());
    }

    @Test
    void login_ReturnsAuthenticationResponseWithHttpStatusOK() {
        // Mocking variables
        LoginRequest loginRequest = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .build();
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token("token")
                .refreshToken("refreshToken")
                .build();

        // Set up the mocks
        when(authService.login(loginRequest)).thenReturn(authenticationResponse);

        // Call the method to be tested
        ResponseEntity<AuthenticationResponse> responseEntity = authController.login(loginRequest);

        // Verify that the necessary method was called
        verify(authService).login(loginRequest);

        // Assert the response entity
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(authenticationResponse, responseEntity.getBody());
    }





}
