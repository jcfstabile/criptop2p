package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.model.User;

public class UserFactory {

    public User createUser(String aName, String aSurname, String anEmail, String anAddress, String aPassword, String aWalletAddress, String aCvu){
        User anUser = new User(aName, aSurname, anEmail, anAddress, aPassword, aWalletAddress, aCvu);
        return anUser;
    }
}
