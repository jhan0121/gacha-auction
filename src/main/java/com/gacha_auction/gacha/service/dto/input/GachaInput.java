package com.gacha_auction.gacha.service.dto.input;

import java.time.LocalDateTime;

public record GachaInput(LocalDateTime currentDateTime) {

    public static GachaInput from(final LocalDateTime currentDateTime) {
        return new GachaInput(currentDateTime);
    }
}
