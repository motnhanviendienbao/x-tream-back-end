package com.example.xtream.exception;

public class UsernameExistException extends RuntimeException{
    public UsernameExistException(final String message,
                                        final Exception exception)
    {
        super(message, exception);
    }

    /**
     * Init exception
     *
     * @param message the detail message of the error.
     */
    public UsernameExistException(String message)
    {
        super(message);
    }
}
