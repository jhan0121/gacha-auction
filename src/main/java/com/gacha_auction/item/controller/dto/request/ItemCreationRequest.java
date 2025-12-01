package com.gacha_auction.item.controller.dto.request;

import com.gacha_auction.item.domain.ItemName;
import com.gacha_auction.item.domain.ItemType;
import com.gacha_auction.item.domain.Rarity;
import com.gacha_auction.item.service.dto.input.ItemCreationInput;

public record ItemCreationRequest(String itemName, ItemType itemType, Rarity rarity) {

    public ItemCreationInput toInput() {
        return new ItemCreationInput(ItemName.from(this.itemName), this.itemType, this.rarity);
    }
}
