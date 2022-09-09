package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.exceptions.NullParameterException;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;

import java.lang.reflect.Array;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

public class UserFactory {

    public User createUser(String aName, String aSurname, String anEmail, String anAddress, String aPassword, String aWalletAddress, String aCvu){
        if(this.meetAllValidations(aName, aSurname, anEmail, anAddress, aPassword, aWalletAddress, aCvu)){
            User anUser = new User(aName, aSurname, anEmail, anAddress, aPassword, aWalletAddress, aCvu);
            return anUser;
        }
        else{
            throw new NullParameterException();
        }

    }

    private boolean meetAllValidations(String aName, String aSurname, String anEmail, String anAddress, String aPassword, String aWalletAddress, String aCvu){
        return this.noParameterIsNull(aName, aSurname, anEmail, anAddress, aPassword, aWalletAddress, aCvu);
    }

    private boolean noParameterIsNull(String aName, String aSurname, String anEmail, String anAddress, String aPassword, String aWalletAddress, String aCvu) {
        ArrayList parameters = new ArrayList();
        parameters.add(aName);
        parameters.add(aSurname);
        parameters.add(anEmail);
        parameters.add(anAddress);
        parameters.add(aPassword);
        parameters.add(aWalletAddress);
        parameters.add(aCvu);
        return !parameters.contains(null);
    }
}
