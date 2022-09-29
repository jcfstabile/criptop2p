package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Buy;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Sell;
import ar.edu.unq.desapp.grupoo.criptop2p.model.TypeIntention;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TypeIntentionDelivery {
    Set<TypeIntention> types;
    public TypeIntentionDelivery(){
        this.types = new HashSet<TypeIntention>();
        this.addType(new Sell());
        this.addType(new Buy());
    }

    public TypeIntention get(String dbName){
        return (TypeIntention) this.types.stream().filter(type -> type.getName().name() == dbName).findFirst().get();
    }

    public void addType(TypeIntention aType){
        this.types.add(aType);
    }

    public void removeType(TypeIntention aType){

        this.types = this.types.stream().filter(type -> type.getName().name() == aType.getName().name()).collect(Collectors.toSet());
    }
}
