package com.gacha_auction.gacha.service;

import com.gacha_auction.gacha.domain.Gacha;
import com.gacha_auction.gacha.repository.GachaRepository;
import com.gacha_auction.gacha.service.dto.input.GachaInput;
import com.gacha_auction.gacha.service.dto.output.GachaOutput;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GachaService {

    private final GachaRepository gachaRepository;

    public GachaOutput findAllGachas(final GachaInput input) {
        final List<Gacha> gachas = gachaRepository.findAllGachasBetweenPeriod(input.currentDateTime());
        return GachaOutput.from(gachas);
    }
}
