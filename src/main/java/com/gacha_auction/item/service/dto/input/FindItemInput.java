package com.gacha_auction.item.service.dto.input;

public record FindItemInput(Long id) {
    public static FindItemInput from(final Long id) {
        return new FindItemInput(id);
    }
}
