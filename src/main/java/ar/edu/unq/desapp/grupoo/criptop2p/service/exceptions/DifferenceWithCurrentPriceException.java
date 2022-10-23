package ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions;

import java.math.BigDecimal;

public class DifferenceWithCurrentPriceException extends RuntimeException{
    public DifferenceWithCurrentPriceException(BigDecimal expectedPrice, BigDecimal currentPrice){
        super("The expected price:" + expectedPrice.toString() + " has a diffenence of 20% or more with " + currentPrice.toString());
    }
}
