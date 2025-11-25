package com.gacha_auction.user.controller.dto.request;

import com.gacha_auction.user.domain.Password;
import com.gacha_auction.user.domain.UserName;
import com.gacha_auction.user.service.dto.input.UserInput;

public record UserRequest(String name, String password) {

    public UserInput toInput() {
        return new UserInput(UserName.from(this.name), Password.from(this.password));
    }
}
