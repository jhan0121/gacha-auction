package com.gacha_auction.user.service.dto.output;

import com.gacha_auction.user.domain.User;

public record FindUserOutput(Long id, String name, Long coin) {

    public static FindUserOutput from(final User user) {
        return new FindUserOutput(user.getId(), user.getName().getValue(), user.getCoin().getAmount());
    }
}
