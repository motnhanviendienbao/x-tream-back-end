package com.example.xtream.exception.custom;

public class InvalidUsernamePasswordAuthenticationException extends RuntimeException {

    /**
     * Default constructor.
     * @param message
     * @param exception
     *
     */
    public InvalidUsernamePasswordAuthenticationException(final String message,
                                                          final Exception exception)
    {
        super(message, exception);
    }

    /**
     * Init exception
     *
     * @param message the detail message of the error.
     */
    public InvalidUsernamePasswordAuthenticationException(String message)
    {
        super(message);
    }

}
