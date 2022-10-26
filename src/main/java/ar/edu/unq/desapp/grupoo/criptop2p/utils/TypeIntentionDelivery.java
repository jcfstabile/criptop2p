package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Buy;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Sell;
import ar.edu.unq.desapp.grupoo.criptop2p.model.TypeIntention;
import java.util.*;

public class TypeIntentionDelivery {
    List<TypeIntention> types;
    public TypeIntentionDelivery(){
        this.types = Arrays.asList(new Buy(), new Sell());
    }

    public TypeIntention get(String dbName){
        return this.types.stream().filter(type -> type.getName().name().equals(dbName)).findFirst().orElseThrow();
    }
}
