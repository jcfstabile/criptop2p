package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Buy;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Sell;
import ar.edu.unq.desapp.grupoo.criptop2p.model.TypeIntention;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TypeIntentionDelivery {
    Set<TypeIntention> types;
    public TypeIntentionDelivery(){
        this.types = new HashSet<>();
        this.addType(new Sell());
        this.addType(new Buy());
    }

    public TypeIntention get(String dbName){
        return this.types.stream().filter(type -> type.getName().name().equals(dbName)).findFirst().orElseThrow();
    }

    public void addType(TypeIntention aType){
        this.types.add(aType);
    }

    public void removeType(TypeIntention aType){

        this.types = this.types.stream().filter(type -> type.getName().name().equals(aType.getName().name())).collect(Collectors.toSet());
    }
}
