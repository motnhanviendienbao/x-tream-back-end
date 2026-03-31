package com.example.xtream.exception;

public class TooManyAttemptLoginException extends RuntimeException {
    public TooManyAttemptLoginException(final String message,
                                  final Exception exception)
    {
        super(message, exception);
    }

    /**
     * Init exception
     *
     * @param message the detail message of the error.
     */
    public TooManyAttemptLoginException(String message)
    {
        super(message);
    }
}
