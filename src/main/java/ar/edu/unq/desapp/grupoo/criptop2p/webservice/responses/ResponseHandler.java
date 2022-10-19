package ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses;

import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.DataIncomingConflictException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.ServerCantHandleRequestNowException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.UserConstraintViolationException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.UserNotFoundException;
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

}
