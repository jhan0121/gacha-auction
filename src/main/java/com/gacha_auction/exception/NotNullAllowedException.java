package com.gacha_auction.exception;

public class NotNullAllowedException extends DomainException {

    public NotNullAllowedException(final String message) {
        super(message);
    }
}
