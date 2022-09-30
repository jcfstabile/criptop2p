package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public
class TypeIntentionConverter implements AttributeConverter<ar.edu.unq.desapp.grupoo.criptop2p.model.TypeIntention, java.lang.String> {
    @Override
    public java.lang.String convertToDatabaseColumn(ar.edu.unq.desapp.grupoo.criptop2p.model.TypeIntention attribute) {
        return attribute.getName().name();
    }

    @Override
    public ar.edu.unq.desapp.grupoo.criptop2p.model.TypeIntention convertToEntityAttribute(java.lang.String dbData) {
        return new TypeIntentionDelivery().get(dbData);
    }
}
