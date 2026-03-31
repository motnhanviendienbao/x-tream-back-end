package com.example.xtream.exception;
import com.example.xtream.dto.response.ResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;

@RestControllerAdvice
public class XtreamExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LogManager.getLogger(XtreamExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ResponseDTO> handleAllExceptions(Exception ex)
    {
        logger.error("System error", ex);
        ResponseDTO errorResponse =
                ResponseDTO.builder().errors(List.of(ex.getMessage())).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseDTO> handleResponseStatus(ResponseStatusException ex) {
        ResponseDTO errorResponse = ResponseDTO.builder()
                .errors(List.of(ex.getReason()))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    @ExceptionHandler(SessionExpireException.class)
    public ResponseEntity<ResponseDTO> handleResponseStatus(SessionExpireException ex) {
        ResponseDTO errorResponse = ResponseDTO.builder()
                .errors(List.of(ex.getMessage()))
                .build();
        return ResponseEntity.status(440).body(errorResponse);
    }
    @ExceptionHandler(AdminForceResetPasswordException.class)
    public ResponseEntity<ResponseDTO> handleResponseStatus(AdminForceResetPasswordException ex) {
        ResponseDTO errorResponse = ResponseDTO.builder()
                .errors(List.of(ex.getMessage()))
                .build();
        return ResponseEntity.status(409).body(errorResponse);
    }
    @ExceptionHandler(TooManyAttemptLoginException.class)
    public ResponseEntity<ResponseDTO> handleResponseStatus(TooManyAttemptLoginException ex) {
        ResponseDTO errorResponse = ResponseDTO.builder()
                .errors(List.of(ex.getMessage()))
                .build();
        return ResponseEntity.status(429).body(errorResponse);
    }
    @ExceptionHandler(InvalidUsernamePasswordAuthenticationException.class)
    public ResponseEntity<ResponseDTO> handleResponseStatus(InvalidUsernamePasswordAuthenticationException ex) {
        ResponseDTO errorResponse = ResponseDTO.builder()
                .errors(List.of(ex.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
