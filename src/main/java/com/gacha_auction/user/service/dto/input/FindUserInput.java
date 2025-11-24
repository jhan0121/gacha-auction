package com.gacha_auction.user.service.dto.input;

public record FindUserInput(Long id) {

    public static FindUserInput from(final Long id) {
        return new FindUserInput(id);
    }
}
