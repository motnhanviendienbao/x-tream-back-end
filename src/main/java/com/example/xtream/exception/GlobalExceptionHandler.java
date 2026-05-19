package com.example.xtream.exception;
import com.example.xtream.dto.response.ResponseDTO;
import com.example.xtream.exception.custom.AdminForceResetPasswordException;
import com.example.xtream.exception.custom.TooManyAttemptLoginException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.websocket.AuthenticationException;
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
        logger.info("System Error: ", ex);
        ResponseDTO errorResponse = ResponseDTO.builder()
                        .error(ex.getMessage())
                        .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        // 500 abstract error
    }

    /**
     * Format error response for all exception
     * @param ex ex
     * @return response with ResponseDTO at body and status code
     */
    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<ResponseDTO> handleAuthenticationException(Exception ex) {
        logger.info("System Error: ", ex);
        ResponseDTO errorResponse = ResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        // 401 unauthorized
    }

    /**
     * Format error response for AccessDeniedException
     * @param ex ex
     * @return response with ResponseDTO at body and status code
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDTO> handleResponseStatus(AccessDeniedException ex) {
        logger.info("AccessDeniedException", ex);
        ResponseDTO errorResponse = ResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        // 403 do not have permission
    }


    /**
     * Format error response for AdminForceResetPasswordException
     * @param ex ex
     * @return response with ResponseDTO at body and status code
     */
    @ExceptionHandler(AdminForceResetPasswordException.class)
    public ResponseEntity<ResponseDTO> handleResponseStatus(AdminForceResetPasswordException ex) {
        logger.info("AdminForceResetPasswordException", ex);
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
        logger.info("TooManyAttemptLoginException", ex);
        ResponseDTO errorResponse = ResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        return ResponseEntity.status(429).body(errorResponse);
        // 429 Too Many Requests
    }
}
