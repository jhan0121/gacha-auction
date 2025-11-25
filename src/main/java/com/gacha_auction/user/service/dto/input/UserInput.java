package com.gacha_auction.user.service.dto.input;

import com.gacha_auction.user.domain.Password;
import com.gacha_auction.user.domain.User;
import com.gacha_auction.user.domain.UserName;

public record UserInput(UserName name, Password password) {

    public User toEntity() {
        return User.withoutId(this.name, this.password);
    }
}
