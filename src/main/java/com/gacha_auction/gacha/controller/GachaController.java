package com.gacha_auction.gacha.controller;

import com.gacha_auction.gacha.controller.dto.response.GachaResponse;
import com.gacha_auction.gacha.service.GachaService;
import com.gacha_auction.gacha.service.dto.input.GachaInput;
import com.gacha_auction.gacha.service.dto.output.GachaOutput;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gachas")
@RequiredArgsConstructor
public class GachaController {

    private final GachaService gachaService;

    @GetMapping
    public ResponseEntity<GachaResponse> findAllGachas() {
        final GachaOutput output = gachaService.findAllGachas(GachaInput.from(LocalDateTime.now()));
        return ResponseEntity.ok(GachaResponse.from(output));
    }
}
