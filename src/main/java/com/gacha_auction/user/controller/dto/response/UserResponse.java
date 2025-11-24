package com.gacha_auction.user.controller.dto.response;

import com.gacha_auction.user.service.dto.output.UserOutput;

public record UserResponse(Long id, String name) {

    public static UserResponse from(final UserOutput output) {
        return new UserResponse(output.id(), output.name());
    }
}
