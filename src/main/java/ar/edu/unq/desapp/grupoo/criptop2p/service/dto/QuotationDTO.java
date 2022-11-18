package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class QuotationDTO {

    @Schema(example = "BTCUSDT")
    private final String name;
    @Schema(example = "19.12300784")
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
