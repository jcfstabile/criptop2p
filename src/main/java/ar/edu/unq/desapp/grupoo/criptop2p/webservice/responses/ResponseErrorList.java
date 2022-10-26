package ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class ResponseErrorList extends ResponseError{
    @ArraySchema(schema = @Schema(example = "An error description"))
    private final List<String> errors;
    public ResponseErrorList(String errorCode, String message, List<String> errors) {
        super(errorCode, message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return this.errors;
    }
}
