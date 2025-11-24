package com.gacha_auction.user.service.dto.output;

import com.gacha_auction.user.domain.User;

public record UserOutput(Long id, String name, Long coin) {

    public static UserOutput from(final User user) {
        return new UserOutput(user.getId(), user.getName(), user.getCoin());
    }
}
