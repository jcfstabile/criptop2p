package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

public class QuotationDTO {

    private final String name;
    private final String price;

    public QuotationDTO(String aName, String aPrice){
        this.name = aName;
        this.price = aPrice;
    }

    public String getPrice() {
        return this.price;
    }


    public String getCryptoName() {
        return this.name;
    }
}
