package br.com.lucas.vieira.miniautorizadorvr.service;

import br.com.lucas.vieira.miniautorizadorvr.entity.User;
import br.com.lucas.vieira.miniautorizadorvr.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        customUserDetailsService = new CustomUserDetailsService(userRepository);
    }

    @Test
    void validandoUsuarioExistente() {
        User mockUser = User.builder()
                .username("username")
                .password("password")
                .roles("USER")
                .build();

        when(userRepository.findByUsername("username")).thenReturn(Optional.of(mockUser));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("username");

        assertNotNull(userDetails);
        assertEquals("username", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));

        verify(userRepository, times(1)).findByUsername("username");
    }

    @Test
    void validandoUsuarioNaoExistente() {
        when(userRepository.findByUsername("usernameInexistente")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("usernameInexistente");
        });

        assertEquals("User not found", exception.getMessage());

        verify(userRepository, times(1)).findByUsername("usernameInexistente");
    }
}
