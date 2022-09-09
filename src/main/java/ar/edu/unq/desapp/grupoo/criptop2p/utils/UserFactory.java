package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.exceptions.IncorrectFormatMail;
import ar.edu.unq.desapp.grupoo.criptop2p.exceptions.NoExtensionsParameter;
import ar.edu.unq.desapp.grupoo.criptop2p.exceptions.NullParameterException;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;

import java.lang.reflect.Array;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserFactory {

    public User createUser(String aName, String aSurname, String anEmail, String anAddress, String aPassword, String aWalletAddress, String aCvu){
        if(this.parameterIsNullOrEmpty(aName, aSurname, anEmail, anAddress, aPassword, aWalletAddress, aCvu)){
            throw new NullParameterException();
        }
        if(this.nameOrSurnameHas3charsLess(aName, aSurname)){
            throw new NoExtensionsParameter();
        }
        if(this.emailIsIncorrect(anEmail)){
            throw new IncorrectFormatMail();
        }
        User anUser = new User(aName, aSurname, anEmail, anAddress, aPassword, aWalletAddress, aCvu);
        return anUser;


    }

    private boolean parameterIsNullOrEmpty(String aName, String aSurname, String anEmail, String anAddress, String aPassword, String aWalletAddress, String aCvu) {
        ArrayList parameters = new ArrayList();
        parameters.add(aName);
        parameters.add(aSurname);
        parameters.add(anEmail);
        parameters.add(anAddress);
        parameters.add(aPassword);
        parameters.add(aWalletAddress);
        parameters.add(aCvu);
        return parameters.contains(null) || parameters.contains("");
    }

    private boolean nameOrSurnameHas3charsLess(String aName, String aSurname){
        return aName.length() < 3 || aSurname.length() < 3;
    }

    private boolean emailIsIncorrect(String anEmail){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(anEmail);
        return !matcher.matches();
    }
}
