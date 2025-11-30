package com.gacha_auction.item.service;

import com.gacha_auction.item.domain.Item;
import com.gacha_auction.item.repository.ItemRepository;
import com.gacha_auction.item.service.dto.input.ItemCreationInput;
import com.gacha_auction.item.service.dto.output.ItemCreationOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemCreationOutput save(final ItemCreationInput input) {
        final Item item = input.toEntity();
        final Item saved = itemRepository.save(item);
        return ItemCreationOutput.from(saved);
    }
}
