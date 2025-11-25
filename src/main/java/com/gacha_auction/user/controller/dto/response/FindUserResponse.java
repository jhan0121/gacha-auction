package com.gacha_auction.user.controller.dto.response;

import com.gacha_auction.user.service.dto.output.FindUserOutput;

public record FindUserResponse(Long id, String name, Long coin) {
    public static FindUserResponse from(final FindUserOutput output) {
        return new FindUserResponse(output.id(), output.name(), output.coin());
    }
}
