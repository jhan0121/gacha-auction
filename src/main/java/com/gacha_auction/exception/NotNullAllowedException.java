package com.gacha_auction.exception;

public class NotNullAllowedException extends RuntimeException {

    public NotNullAllowedException(final String message) {
        super(message);
    }
}
