package ar.edu.unq.desapp.grupoo.criptop2p.integrations;

import ar.edu.unq.desapp.grupoo.criptop2p.service.InitDbData;
import ar.edu.unq.desapp.grupoo.criptop2p.service.QuotationService;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.InternalErrorProcessingQuotationsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class Cache implements Runnable {

    @Autowired
    QuotationService quotationService;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Value("${cryptop2p.update.quotations.rate:10}")
    private int updateRate;

    @PostConstruct
    void startCacherOfQuotations(){
        scheduler.scheduleAtFixedRate(this , 0, updateRate, TimeUnit.MINUTES);
    }

    protected final Log logger = LogFactory.getLog(getClass());

    public void run(){
        logger.info("Caching all quotations" );
        try {
            quotationService.setCachedQuotations();
            quotationService.saveCachedQuotations();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (JsonProcessingException e) {
            throw new InternalErrorProcessingQuotationsException();
        }
        logger.info("All quotations cached" );
    }

}
