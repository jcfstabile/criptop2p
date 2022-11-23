package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.service.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@DisplayName("InitDbData Tests")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class InitDbDataTest {

    @Mock
    UserService userService;

    @DisplayName("InitDb data is created")
    @Test
    void testInit() {
        (new InitDbData(userService)).init();
        verify(userService, times(4)).addUser(any());
    }

}
