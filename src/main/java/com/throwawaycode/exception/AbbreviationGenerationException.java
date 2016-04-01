package com.throwawaycode.exception;

public class AbbreviationGenerationException extends RuntimeException {
    public AbbreviationGenerationException() {
        super();
    }

    public AbbreviationGenerationException(String message) {
        super(message);
    }

    public AbbreviationGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbbreviationGenerationException(Throwable cause) {
        super(cause);
    }

    protected AbbreviationGenerationException(String message,
                                              Throwable cause,
                                              boolean enableSuppression,
                                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
