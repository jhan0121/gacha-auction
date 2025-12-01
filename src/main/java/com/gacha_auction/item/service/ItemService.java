package com.gacha_auction.item.service;

import com.gacha_auction.exception.NotFoundException;
import com.gacha_auction.item.domain.Item;
import com.gacha_auction.item.repository.ItemRepository;
import com.gacha_auction.item.service.dto.input.FindItemInput;
import com.gacha_auction.item.service.dto.input.ItemCreationInput;
import com.gacha_auction.item.service.dto.output.FindItemOutput;
import com.gacha_auction.item.service.dto.output.ItemCreationOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public ItemCreationOutput save(final ItemCreationInput input) {
        final Item item = input.toEntity();
        final Item saved = itemRepository.save(item);
        return ItemCreationOutput.from(saved);
    }

    @Transactional(readOnly = true)
    public FindItemOutput findById(final FindItemInput input) {
        final Item item = itemRepository.findById(input.id())
                .orElseThrow(() -> new NotFoundException("item not found"));
        return FindItemOutput.from(item);
    }
}
