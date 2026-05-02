package com.example.xtream.exception.custom;

public class AdminForceResetPasswordException extends RuntimeException{
    public AdminForceResetPasswordException(final String message,
                                  final Exception exception)
    {
        super(message, exception);
    }

    /**
     * Init exception
     *
     * @param message the detail message of the error.
     */
    public AdminForceResetPasswordException(String message)
    {
        super(message);
    }
}
