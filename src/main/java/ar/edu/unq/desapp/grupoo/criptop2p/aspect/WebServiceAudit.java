package ar.edu.unq.desapp.grupoo.criptop2p.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;


@Aspect
@Component
public class WebServiceAudit {
    static Logger logger = LoggerFactory.getLogger(WebServiceAudit.class);


    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Object logGetWebServiceAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        return logWebServiceAudit(joinPoint, "GET");
    }

    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public Object logPostWebServiceAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        return logWebServiceAudit(joinPoint, "POST");
    }
    @Around("@annotation(org.springframework.web.bind.annotation.PatchMapping)")
    public Object logPatchWebServiceAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        return logWebServiceAudit(joinPoint, "PATCH");
    }
    @Around("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object logDeleteWebServiceAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        return logWebServiceAudit(joinPoint, "DELETE");
    }
    public Object logWebServiceAudit(ProceedingJoinPoint joinPoint, String operation) throws Throwable {
        Long start = System.currentTimeMillis();
        Object proceed = new Object();
        try {
            proceed = joinPoint.proceed();
        } finally {
            if (logger.isInfoEnabled()) {
                Long now = System.currentTimeMillis();
                Timestamp timestamp = new Timestamp(now);
                var auth = SecurityContextHolder.getContext().getAuthentication();
                logger.info("Timestamp={} User={} Operation={} Parameter(s)={} Execution time={}ms",
                        timestamp,
                        auth == null ? "---" : auth.getPrincipal(),
                        operation,
                        Arrays.toString(joinPoint.getArgs()),
                        now - start);
            }
        }

        return proceed;
    }
}
