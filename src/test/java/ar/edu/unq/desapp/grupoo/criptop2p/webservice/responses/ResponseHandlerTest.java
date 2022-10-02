package ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses;

import ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions.DataIncomingConflictException;
import ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions.UserConstraintViolationException;
import ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResponseHandlerTest {

    @DisplayName("When a user not found exception is raised the response is a error body and a 404")
    @Test
    void handleUserNotFound() {
        Long userId = 0L;
        UserNotFoundException userNotFoundException = new UserNotFoundException(userId);
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleUserNotFound(userNotFoundException);

        assertNotNull(responseEntity);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorSimple body = (ResponseErrorSimple) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return the error body of user not found",
                () -> assertEquals("001", body.getErrorCode()),
                () -> assertEquals("User not found", body.getMessage()),
                () -> assertEquals("Could not find user "+ userId, body.getError())
        );
    }

    @DisplayName("When a user constraint exception is raised the response is error body with a list of errors and a 400")
    @Test
    void handleUserConstraintViolation() {
        List<String> errors = List.of("Error 1", "Error 2");
        UserConstraintViolationException userConstraintViolationException = new UserConstraintViolationException(errors);
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleUserConstraintViolation(userConstraintViolationException);

        assertNotNull(responseEntity);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorList body = (ResponseErrorList) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return a error body with a list of user constraint violation messages",
                () -> assertEquals("002", body.getErrorCode()),
                () -> assertEquals("User data malformed", body.getMessage()),
                () -> assertArrayEquals(errors.toArray(), body.getErrors().toArray())
        );

    }

    @DisplayName("When a data incoming conflict exception is raised the response is a error body and a 409")
    @Test
    void handleDataConflictException() {
        DataIncomingConflictException dataIncomingConflictException = new DataIncomingConflictException();
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleDataConflictException(dataIncomingConflictException);

        assertNotNull(responseEntity);
        assertEquals("409 CONFLICT", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorSimple body = (ResponseErrorSimple) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return a error body of a data conflict",
                () -> assertEquals("003", body.getErrorCode()),
                () -> assertEquals("User was already registered", body.getMessage()),
                () -> assertEquals("The operation can not be completed due to data conflict", body.getError())
        );

    }
}