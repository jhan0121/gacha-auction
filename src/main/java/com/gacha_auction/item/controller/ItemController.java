package com.gacha_auction.item.controller;

import com.gacha_auction.item.controller.dto.request.ItemCreationRequest;
import com.gacha_auction.item.controller.dto.response.ItemCreationResponse;
import com.gacha_auction.item.service.ItemService;
import com.gacha_auction.item.service.dto.input.ItemCreationInput;
import com.gacha_auction.item.service.dto.output.ItemCreationOutput;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemCreationResponse> save(@RequestBody final ItemCreationRequest request) {
        final ItemCreationInput input = request.toInput();
        final ItemCreationOutput output = itemService.save(input);
        final ItemCreationResponse response = ItemCreationResponse.from(output);

        final URI uri = URI.create("/apu/v1/items/" + output.id());
        return ResponseEntity.created(uri).body(response);
    }
}
