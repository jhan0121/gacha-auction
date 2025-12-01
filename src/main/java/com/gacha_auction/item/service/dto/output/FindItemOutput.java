package com.gacha_auction.item.service.dto.output;

import com.gacha_auction.item.domain.Item;
import com.gacha_auction.item.domain.ItemName;
import com.gacha_auction.item.domain.ItemType;
import com.gacha_auction.item.domain.Rarity;

public record FindItemOutput(Long id, ItemName itemName, ItemType itemType, Rarity rarity) {

    public static FindItemOutput from(final Item item) {
        return new FindItemOutput(item.getId(), item.getName(), item.getType(), item.getRarity());
    }
}
