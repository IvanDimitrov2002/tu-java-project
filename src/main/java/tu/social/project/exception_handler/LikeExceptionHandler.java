package tu.social.project.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import tu.social.project.exception.AlreadyLikedPostException;
import tu.social.project.payload.response.ErrorResponse;

@ControllerAdvice
public class LikeExceptionHandler {
    @ExceptionHandler(AlreadyLikedPostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(AlreadyLikedPostException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(exception.getMessage()));
    }
}
