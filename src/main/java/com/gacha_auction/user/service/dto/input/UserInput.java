package com.gacha_auction.user.service.dto.input;

import com.gacha_auction.user.domain.User;

public record UserInput(String name, String password) {

    public User toEntity() {
        return User.withoutId(this.name, this.password);
    }
}
