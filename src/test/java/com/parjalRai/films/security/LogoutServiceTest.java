package com.parjalRai.films.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parjalRai.films.security.token.Token;
import com.parjalRai.films.security.token.TokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class LogoutServiceTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private LogoutService logoutService;

    @Test
    void logout_ValidToken_TokenRevokedAndExpired() {
        // Mocking variables
        String jwt = "valid-jwt-token";

        // Mocking method invocations
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(tokenRepository.findByToken(jwt)).thenReturn(Optional.of(new Token()));

        // Call the method to be tested
        logoutService.logout(request, response, null);

        // Verify that the necessary methods were called
        verify(tokenRepository, times(1)).findByToken(jwt);

        // Verify that the stored token is updated
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void logout_InvalidToken_NoActionTaken() {
        // Mocking variables
        String jwt = "invalid-jwt-token";

        // Mocking method invocations
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(tokenRepository.findByToken(jwt)).thenReturn(Optional.empty());

        // Call the method to be tested
        logoutService.logout(request, response, null);

        // Verify that the necessary methods were called
        verify(tokenRepository, times(1)).findByToken(jwt);

        // Verify that no token updates were made
        verifyNoMoreInteractions(tokenRepository);
    }

    @Test
    void logout_NoToken_NoActionTaken() {
        // Mocking method invocations
        when(request.getHeader("Authorization")).thenReturn(null);

        // Call the method to be tested
        logoutService.logout(request, response, null);

        // Verify that the necessary methods were called
        verify(request, times(1)).getHeader("Authorization");

        // Verify that no token repository interactions were made
        verifyNoInteractions(tokenRepository);
    }
}
