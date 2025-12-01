package com.gacha_auction.item.controller;

import com.gacha_auction.item.controller.dto.request.ItemCreationRequest;
import com.gacha_auction.item.controller.dto.response.FindItemResponse;
import com.gacha_auction.item.controller.dto.response.ItemCreationResponse;
import com.gacha_auction.item.service.ItemService;
import com.gacha_auction.item.service.dto.input.ItemCreationInput;
import com.gacha_auction.item.service.dto.output.FindItemOutput;
import com.gacha_auction.item.service.dto.output.ItemCreationOutput;
import com.gacha_auction.user.service.dto.input.FindItemInput;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        final URI uri = URI.create("/api/v1/items/" + output.id());
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindItemResponse> findItem(@PathVariable("id") final Long id) {
        final FindItemInput input = FindItemInput.from(id);
        final FindItemOutput output = itemService.findById(input);
        final FindItemResponse response = FindItemResponse.from(output);
        return ResponseEntity.ok(response);
    }
}
