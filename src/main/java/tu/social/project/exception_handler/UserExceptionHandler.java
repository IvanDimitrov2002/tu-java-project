package tu.social.project.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tu.social.project.exception.UserAlreadyExistsException;
import tu.social.project.exception.UserNotExistsException;
import tu.social.project.payload.response.UserErrorResponse;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<UserErrorResponse> handle(UserAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserErrorResponse(exception.getMessage()));
    }

}
