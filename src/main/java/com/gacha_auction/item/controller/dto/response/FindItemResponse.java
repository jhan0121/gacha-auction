package com.gacha_auction.item.controller.dto.response;

import com.gacha_auction.item.domain.ItemType;
import com.gacha_auction.item.domain.Rarity;
import com.gacha_auction.item.service.dto.output.FindItemOutput;

public record FindItemResponse(Long id, String itemName, ItemType itemType, Rarity rarity) {

    public static FindItemResponse from(final FindItemOutput output) {
        return new FindItemResponse(output.id(), output.itemName().getValue(), output.itemType(), output.rarity());
    }
}
