package com.gacha_auction.item.controller.dto.response;

import com.gacha_auction.item.domain.ItemType;
import com.gacha_auction.item.domain.Rarity;
import com.gacha_auction.item.service.dto.output.ItemCreationOutput;

public record ItemCreationResponse(Long id, String itemName, ItemType itemType, Rarity rarity) {

    public static ItemCreationResponse from(final ItemCreationOutput output) {
        return new ItemCreationResponse(output.id(), output.itemName().getValue(), output.itemType(), output.rarity());
    }
}
