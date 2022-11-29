package ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Status;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.format.DateTimeParseException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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

    @DisplayName("When the server is interrupted the response is a body error with a 503 status code")
    @Test
    void testHandlerInterruptedErrorException() {
        InterruptedErrorException interruptedErrorException = new InterruptedErrorException();
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleInterruptedErrorException(interruptedErrorException);

        assertNotNull(responseEntity);
        assertEquals("503 SERVICE_UNAVAILABLE", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorSimple body = (ResponseErrorSimple) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return a error body of a major error",
                () -> assertEquals("005", body.getErrorCode()),
                () -> assertEquals("A major error had occurred", body.getMessage()),
                () -> assertEquals("Server operation has been interrupted", body.getError())
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

    @DisplayName("When the intention cant be found handle request the response is a not found error")
    @Test
    void testHandleNoValidActionErrorException() {
        String action = "deliver";
        NoValidActionErrorException noValidActionErrorException = new NoValidActionErrorException(action);
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleNoValidActionErrorException(noValidActionErrorException);
        assertNotNull(responseEntity);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorSimple body = (ResponseErrorSimple) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return a error body of a major error",
                () -> assertEquals("007", body.getErrorCode()),
                () -> assertEquals("Not a valid action", body.getMessage()),
                () -> assertEquals("Action not valid: " + action, body.getError())
        );
    }

    @DisplayName("When the intention cant be found handle request the response is a not found error")
    @Test
    void testStatusChangeNotAllowedRestException() {
        Status status = Status.CLOSED;
        StatusChangeNotAllowedRestException statusChangeNotAllowedRestException = new StatusChangeNotAllowedRestException(status);
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleStatusChangeNotAllowedException(statusChangeNotAllowedRestException);
        assertNotNull(responseEntity);
        assertEquals("409 CONFLICT", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorSimple body = (ResponseErrorSimple) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return a error body of a major error",
                () -> assertEquals("008", body.getErrorCode()),
                () -> assertEquals("The action can not be terminated", body.getMessage()),
                () -> assertEquals("Cant change the status to: " + Status.CLOSED , body.getError())
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
                () -> assertEquals("009", body.getErrorCode()),
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
                () -> assertEquals("010", body.getErrorCode()),
                () -> assertEquals("Incorrect user", body.getMessage()),
                () -> assertEquals("The user with ID: 1 cant accepted this intention because its is the intention offered", body.getError())
        );
    }

    @DisplayName("When the price of a intention has a incompatible diff with current price of a crypto")
    @Test
    void testHandleDifferenceOfPrice() {
        BigDecimal priceA = BigDecimal.valueOf(1.23334000);
        BigDecimal priceB = BigDecimal.valueOf(2.65430000);
        DifferenceWithCurrentPriceException differenceWithCurrentPriceException = new DifferenceWithCurrentPriceException(priceA, priceB);
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleDifferenceWithCurrentPriceException(differenceWithCurrentPriceException);
        assertNotNull(responseEntity);
        assertEquals("409 CONFLICT", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorSimple body = (ResponseErrorSimple) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return a error body of a major error",
                () -> assertEquals("011", body.getErrorCode()),
                () -> assertEquals("The action can not be terminated", body.getMessage()),
                () -> assertEquals("The expected price " + priceA + " has a difference of 20% or more with " + priceB, body.getError())
        );
    }

    @DisplayName("When crypto requested was not found a error is show")
    @Test
    void testHandleCryptoNotFound() {
        String cryptoName = "ASDASDUSDT";
        CryptoNotFoundException cryptoNotFoundException = new CryptoNotFoundException(cryptoName);
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleCryptoNotFound(cryptoNotFoundException);
        assertNotNull(responseEntity);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorSimple body = (ResponseErrorSimple) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return a error body of a major error",
                () -> assertEquals("012", body.getErrorCode()),
                () -> assertEquals("Crypto not found", body.getMessage()),
                () -> assertEquals("The crypto with name " + cryptoName + " was not found", body.getError())
        );
    }

    @DisplayName("When a error is found processing quotations a exception is trown")
    @Test
    void handleInternalError(){
        InternalErrorProcessingQuotationsException internalErrorProcessingQuotationsException = new InternalErrorProcessingQuotationsException();
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleInternalErrorProcessingQuotation(internalErrorProcessingQuotationsException);

        assertNotNull(responseEntity);
        assertEquals("500 INTERNAL_SERVER_ERROR", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorSimple body = (ResponseErrorSimple) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return a error body of a major error",
                () -> assertEquals("013", body.getErrorCode()),
                () -> assertEquals("Error on processing", body.getMessage()),
                () -> assertEquals("Internal error processing quotations", body.getError())
        );
    }


    @DisplayName("When an username with email not found, an exception is trown")
    @Test
    void handlehandleUsernameWithEmailNotFoundException(){
        UsernameWithEmailNotFoundException usernameWithEmailNotFoundException = new UsernameWithEmailNotFoundException("wrong_email@never.cl");
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleUsernameWithEmailNotFoundException(usernameWithEmailNotFoundException);

        assertNotNull(responseEntity);
        assertEquals("500 INTERNAL_SERVER_ERROR", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorSimple body = (ResponseErrorSimple) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return a error body of a major error",
                () -> assertEquals("014", body.getErrorCode()),
                () -> assertEquals("Email not found", body.getMessage()),
                () -> assertEquals("User with email: wrong_email@never.cl cant be found", body.getError())
        );
    }

    @DisplayName("IOException On Attempt Authentication Exception")
    @Test
    void handleIOExceptionOnAttemptAuthenticationException(){
        IOExceptionOnAttemptAuthenticationException iOExceptionOnAttemptAuthenticationException = new IOExceptionOnAttemptAuthenticationException();
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleIOExceptionOnAttemptAuthentication(iOExceptionOnAttemptAuthenticationException);

        assertNotNull(responseEntity);
        assertEquals("500 INTERNAL_SERVER_ERROR", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorSimple body = (ResponseErrorSimple) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return a error body of a major error",
                () -> assertEquals("015", body.getErrorCode()),
                () -> assertEquals("Error on authentication", body.getMessage()),
                () -> assertNull(body.getError())
        );
    }

    @DisplayName("IncorrectDateFormatException")
    @Test
    void handleIncorrectDateFormatException(){
        DateTimeParseException ex = mock(DateTimeParseException.class);
        IncorrectDateFormatException incorrectDateFormatException = new IncorrectDateFormatException(ex);
        ResponseHandler responseHandler = new ResponseHandler();
        ResponseEntity<Object> responseEntity = responseHandler.handleIncorrectDataFormatException(incorrectDateFormatException);

        assertNotNull(responseEntity);
        assertEquals("409 CONFLICT", responseEntity.getStatusCode().toString());
        assertTrue(responseEntity.hasBody());

        ResponseErrorSimple body = (ResponseErrorSimple) responseEntity.getBody();

        assertNotNull(body);
        assertAll("Should return a error body of a major error",
                () -> assertEquals("016", body.getErrorCode()),
                () -> assertEquals("Date/s can't be format", body.getMessage()),
                () -> assertNull(body.getError())
        );
    }
}