package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("UserDetailsServiceImpl Test")
@SpringBootTest(classes = { UserDetailsServiceImpl.class, UserRepository.class })
@ExtendWith(MockitoExtension.class)
@Import(UserRepository.class)
class UserDetailsServiceImplTest {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    UserRepository userRepository;

    @DisplayName("UserDetailsService Test")
    @Test
    void loadUserByUsername() {
        User user = mock(User.class);
        when(userRepository.findByEmail("admin@here.data")).thenReturn(Optional.ofNullable(user));

        userDetailsService.loadUserByUsername("admin@here.data");

        verify(userRepository, atLeastOnce()).findByEmail("admin@here.data");
    }
}