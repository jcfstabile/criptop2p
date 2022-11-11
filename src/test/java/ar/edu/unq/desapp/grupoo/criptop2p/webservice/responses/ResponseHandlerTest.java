package ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses;

import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.*;
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
    @DisplayName("When the server cant handle request the response is a error body and a 503")
    @Test
    void handleServerException() {
        ServerCantHandleRequestNowException serverCantHandleRequestNowException= new ServerCantHandleRequestNowException();
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleServerCantHandleRequestException(serverCantHandleRequestNowException);

        assertNotNull(responseEntity);
        assertEquals("503 SERVICE_UNAVAILABLE", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorSimple body = (ResponseErrorSimple) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return a error body of a major error",
                () -> assertEquals("004", body.getErrorCode()),
                () -> assertEquals("A major error had occurred", body.getMessage()),
                () -> assertEquals("The server can't handle the request now", body.getError())
        );
    }

    @DisplayName("When the intention cant be found handle request the response is a not found error")
    @Test
    void handleIntentionNotFound() {
        IntentionNotFoundException intentionNotFoundException = new IntentionNotFoundException(123456789L);
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleIntentionNotFoundException(intentionNotFoundException);
        assertNotNull(responseEntity);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorSimple body = (ResponseErrorSimple) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return a error body of a major error",
                () -> assertEquals("006", body.getErrorCode()),
                () -> assertEquals("Intention not found", body.getMessage()),
                () -> assertEquals("Could not find intention 123456789", body.getError())
        );
    }

    @DisplayName("When the status of intentions is incorrect handle request")
    @Test
    void handleIncorrectStatus() {
        IncorrectStatusException incorrectStatusException = new IncorrectStatusException("ASD");
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleIncorrectStatusException(incorrectStatusException);
        assertNotNull(responseEntity);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorSimple body = (ResponseErrorSimple) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return a error body of a major error",
                () -> assertEquals("007", body.getErrorCode()),
                () -> assertEquals("Incorrect state", body.getMessage()),
                () -> assertEquals("The next status is a incorrect status for an intention: ASD", body.getError())
        );
    }

    @DisplayName("When user is incorrect handle request")
    @Test
    void handleIncorrectUser() {
        IncorrectUserException incorrectUserException = new IncorrectUserException(1L);
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleIncorrectUserException(incorrectUserException);
        assertNotNull(responseEntity);
        assertEquals("409 CONFLICT", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorSimple body = (ResponseErrorSimple) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return a error body of a major error",
                () -> assertEquals("007", body.getErrorCode()),
                () -> assertEquals("Incorrect user", body.getMessage()),
                () -> assertEquals("The user with ID: 1 cant accepted this intention because its is the intention offered", body.getError())
        );
    }
}