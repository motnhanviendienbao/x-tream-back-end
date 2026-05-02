package com.example.xtream.exception.custom;

public class SessionExpireException extends RuntimeException {
    public SessionExpireException(final String message,
                                  final Exception exception)
    {
        super(message, exception);
    }

    /**
     * Init exception
     *
     * @param message the detail message of the error.
     */
    public SessionExpireException(String message)
    {
        super(message);
    }
}
