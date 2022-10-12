package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

public class Quotation {

    private String name;
    private String price;

    public Quotation(String aName, String aPrice){
        this.name = aName;
        this.price = aPrice;
    }

    public String getPrice() {
        return this.price;
    }


    public String getCrypto() {
        return this.name;
    }
}
