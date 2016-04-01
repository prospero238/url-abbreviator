package com.throwawaycode.exception;

public class AbbreviationNotFoundException extends RuntimeException {
    public AbbreviationNotFoundException() {
        super();
    }

    public AbbreviationNotFoundException(String message) {
        super(message);
    }

    public AbbreviationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbbreviationNotFoundException(Throwable cause) {
        super(cause);
    }

    protected AbbreviationNotFoundException(String message,
                                            Throwable cause,
                                            boolean enableSuppression,
                                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
