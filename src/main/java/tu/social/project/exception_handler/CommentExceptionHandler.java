package tu.social.project.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import tu.social.project.exception.CommentCannotBeDeletedException;
import tu.social.project.exception.CommentCannotBeEditedException;
import tu.social.project.payload.response.ErrorResponse;

@ControllerAdvice
public class CommentExceptionHandler {
  @ExceptionHandler(CommentCannotBeEditedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<ErrorResponse> handle(CommentCannotBeEditedException exception) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(exception.getMessage()));
  }

  @ExceptionHandler(CommentCannotBeDeletedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<ErrorResponse> handle(CommentCannotBeDeletedException exception) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(exception.getMessage()));
  }
}
