package com.gacha_auction.item.service.dto.input;

import com.gacha_auction.item.domain.Item;
import com.gacha_auction.item.domain.ItemName;
import com.gacha_auction.item.domain.ItemType;
import com.gacha_auction.item.domain.Rarity;

public record ItemCreationInput(ItemName itemName, ItemType itemType, Rarity rarity) {

    public Item toEntity() {
        return Item.withoutId(itemName, itemType, rarity);
    }
}
