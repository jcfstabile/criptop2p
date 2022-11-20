package ar.edu.unq.desapp.grupoo.criptop2p.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;


@Aspect
@Component
public class WebServiceAudit {
    static Logger logger = LoggerFactory.getLogger(WebServiceAudit.class);

    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PatchMapping)"
    )
    public Object logWebServiceAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        // Loguear <timestamp,user,operación/metodo, parámetros, tiempoDeEjecicion>
        Long start = System.currentTimeMillis();
        Object proceed = new Object();
        try {
            proceed = joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            Long now = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(now);
            // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            logger.info("Timestamp={} User={} Operation={} Parameter(s)={} Execution time={}ms",
                    timestamp,
                    "WAITING JWT",
                    joinPoint.getSignature(),
                    Arrays.toString(joinPoint.getArgs()),
                    now - start );
        }

        return proceed;
    }
}
