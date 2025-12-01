package com.gacha_auction.item.service.dto.output;

import com.gacha_auction.item.domain.Item;
import com.gacha_auction.item.domain.ItemName;
import com.gacha_auction.item.domain.ItemType;
import com.gacha_auction.item.domain.Rarity;

public record ItemCreationOutput(Long id, ItemName itemName, ItemType itemType, Rarity rarity) {

    public static ItemCreationOutput from(final Item item) {
        return new ItemCreationOutput(item.getId(), item.getName(), item.getType(), item.getRarity());
    }
}
