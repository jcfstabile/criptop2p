package ar.edu.unq.desapp.grupoo.criptop2p.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("WebServiceAudit test")
@SpringBootTest
class WebServiceAuditTest {

    @Autowired
    WebServiceAudit webServiceAudit;

    @DisplayName("When a PointCut match the joinPoint.proceed is called")
    @Test
    void testJoinPointIsCalled() throws Throwable {
        ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
        ResponseEntity<Object> re = mock(ResponseEntity.class);
        when(pjp.proceed()).thenReturn(re);

        Object response = webServiceAudit.logWebServiceAudit(pjp, "OP");

        verify(pjp, atLeast(1)).proceed();
        verify(pjp, atLeast(1)).getArgs()  ;
        assertEquals(re, response);
    }
}
