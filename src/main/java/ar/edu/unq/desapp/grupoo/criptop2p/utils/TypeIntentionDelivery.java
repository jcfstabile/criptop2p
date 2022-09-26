package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Buy;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Sell;
import ar.edu.unq.desapp.grupoo.criptop2p.model.TypeIntention;

import java.util.ArrayList;
import java.util.List;

public class TypeIntentionDelivery {
    List<ar.edu.unq.desapp.grupoo.criptop2p.model.TypeIntention> types;
    public TypeIntentionDelivery(){
        this.types = new ArrayList<ar.edu.unq.desapp.grupoo.criptop2p.model.TypeIntention>();
        this.types.add(new Sell());
        this.types.add(new Buy());
    }

    public TypeIntention get(String dbName){
        return (TypeIntention) this.types.stream().filter(type -> type.getName().name() == dbName);
    }

    public void addType(TypeIntention aType){
        this.types.add(aType);
    }

    public void removeType(TypeIntention aType){
        this.types.remove(aType);
    }
}
