package ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses;

import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseHandler  extends ResponseEntityExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException exception){
        return new ResponseEntity<>(new ResponseErrorSimple("001", "User not found", exception.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({UserConstraintViolationException.class})
    public ResponseEntity<Object> handleUserConstraintViolation(UserConstraintViolationException exception){
        return new ResponseEntity<>(new ResponseErrorList("002", "User data malformed", exception.getErrors()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({DataIncomingConflictException.class})
    public ResponseEntity<Object> handleDataConflictException(DataIncomingConflictException exception){
        return new ResponseEntity<>(new ResponseErrorSimple("003", "User was already registered", exception.getError()), HttpStatus.CONFLICT);
    }
    @ExceptionHandler({ServerCantHandleRequestNowException.class})
    public ResponseEntity<Object> handleServerCantHandleRequestException(ServerCantHandleRequestNowException exception){
        return new ResponseEntity<>(new ResponseErrorSimple("004", "A major error had occurred", exception.getError()), HttpStatus.SERVICE_UNAVAILABLE);
    }
    @ExceptionHandler({InterruptedErrorException.class})
    public ResponseEntity<Object> handleInterruptedErrorException(InterruptedErrorException exception){
        return new ResponseEntity<>(new ResponseErrorSimple("005", "A major error had occurred", exception.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }
    @ExceptionHandler({IntentionNotFoundException.class})
    public ResponseEntity<Object> handleIntentionNotFoundException(IntentionNotFoundException exception){
        return new ResponseEntity<>(new ResponseErrorSimple("006", "Intention not found", exception.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({NoValidActionErrorException.class})
    public ResponseEntity<Object> handleNoValidActionErrorException(NoValidActionErrorException exception){
        return new ResponseEntity<>(new ResponseErrorSimple("007", "Not a valid action", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({StatusChangeNotAllowedRestException.class})
    public ResponseEntity<Object> handleStatusChangeNotAllowedException(StatusChangeNotAllowedRestException exception){
        return new ResponseEntity<>(new ResponseErrorSimple("008", "The action can not be terminated", exception.getMessage()), HttpStatus.CONFLICT);
    }
    @ExceptionHandler({IncorrectStatusException.class})
    public ResponseEntity<Object> handleIncorrectStatusException(IncorrectStatusException exception){
        return new ResponseEntity<>(new ResponseErrorSimple("009", "Incorrect state", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({IncorrectUserException.class})
    public ResponseEntity<Object> handleIncorrectUserException(IncorrectUserException exception){
        return new ResponseEntity<>(new ResponseErrorSimple("010", "Incorrect user", exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({DifferenceWithCurrentPriceException.class})
    public ResponseEntity<Object> handleDifferenceWithCurrentPriceException(DifferenceWithCurrentPriceException exception){
        return new ResponseEntity<>(new ResponseErrorSimple("011", "The action can not be terminated", exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({CryptoNotFoundException.class})
    public ResponseEntity<Object> handleCryptoNotFound(CryptoNotFoundException exception) {
        return new ResponseEntity<>(new ResponseErrorSimple("012", "Crypto not found", exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InternalErrorProcessingQuotationsException.class})
    public ResponseEntity<Object> handleInternalErrorProcessingQuotation(InternalErrorProcessingQuotationsException exception){
        return new ResponseEntity<>(new ResponseErrorSimple("013", "Error on processing", exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
