package com.example.xtream.exception;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.exception.custom.AdminForceResetPasswordException;
import com.example.xtream.exception.custom.InvalidUsernamePasswordAuthenticationException;
import com.example.xtream.exception.custom.SessionExpireException;
import com.example.xtream.exception.custom.TooManyAttemptLoginException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler for all runtime exception error
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * Format error response for all exception
     * @param ex ex
     * @return response with ResponseDTO at body and status code
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ResponseDTO> handleAllExceptions(Exception ex) {
        logger.info("[NGOC TU SERVER] System Error: ", ex);
        ResponseDTO errorResponse = ResponseDTO.builder()
                        .error(ex.getMessage())
                        .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        // 500 abstract error
    }

    /**
     * Format error response for AccessDeniedException
     * @param ex ex
     * @return response with ResponseDTO at body and status code
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDTO> handleResponseStatus(AccessDeniedException ex) {
        logger.info("[NGOC TU SERVER]: AccessDeniedException", ex);
        ResponseDTO errorResponse = ResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        // 403 do not have permission
    }

    /**
     * Format error response for SessionExpireException
     * @param ex ex
     * @return response with ResponseDTO at body and status code
     */
    @ExceptionHandler(SessionExpireException.class)
    public ResponseEntity<ResponseDTO> handleResponseStatus(SessionExpireException ex) {
        logger.info("[NGOC TU SERVER]: SessionExpireException", ex);
        ResponseDTO errorResponse = ResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        return ResponseEntity.status(440).body(errorResponse);
        // 440 Login Time-out
    }

    /**
     * Format error response for AdminForceResetPasswordException
     * @param ex ex
     * @return response with ResponseDTO at body and status code
     */
    @ExceptionHandler(AdminForceResetPasswordException.class)
    public ResponseEntity<ResponseDTO> handleResponseStatus(AdminForceResetPasswordException ex) {
        logger.info("[NGOC TU SERVER]: AdminForceResetPasswordException", ex);
        ResponseDTO errorResponse = ResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        return ResponseEntity.status(426).body(errorResponse);
        // 426 Upgrade Required
    }

    /**
     * Format error response for TooManyAttemptLoginException
     * @param ex ex
     * @return response with ResponseDTO at body and status code
     */
    @ExceptionHandler(TooManyAttemptLoginException.class)
    public ResponseEntity<ResponseDTO> handleResponseStatus(TooManyAttemptLoginException ex) {
        logger.info("[NGOC TU SERVER]: TooManyAttemptLoginException", ex);
        ResponseDTO errorResponse = ResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        return ResponseEntity.status(429).body(errorResponse);
        // 429 Too Many Requests
    }

    /**
     * Format error response for InvalidUsernamePasswordAuthenticationException
     * @param ex ex
     * @return response with ResponseDTO at body and status code
     */
    @ExceptionHandler(InvalidUsernamePasswordAuthenticationException.class)
    public ResponseEntity<ResponseDTO> handleResponseStatus(InvalidUsernamePasswordAuthenticationException ex) {
        logger.info("[NGOC TU SERVER]: InvalidUsernamePasswordAuthenticationException", ex);
        ResponseDTO errorResponse = ResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        // 401 Unauthorized
    }

}
