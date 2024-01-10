package tu.social.project.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Hidden;
import tu.social.project.exception.UnauthorizedUserException;
import tu.social.project.exception.UserAlreadyExistsException;
import tu.social.project.exception.UserNotExistsException;
import tu.social.project.exception.UserPasswordIncorrectException;
import tu.social.project.payload.response.ErrorResponse;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @Hidden
    public ResponseEntity<ErrorResponse> handle(UserAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @Hidden
    public ResponseEntity<ErrorResponse> handle(UnauthorizedUserException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(UserNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Hidden
    public ResponseEntity<ErrorResponse> handle(UserNotExistsException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(UserPasswordIncorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Hidden
    public ResponseEntity<ErrorResponse> handle(UserPasswordIncorrectException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(exception.getMessage()));
    }
}
