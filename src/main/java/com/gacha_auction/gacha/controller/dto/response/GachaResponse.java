package com.gacha_auction.gacha.controller.dto.response;

import com.gacha_auction.gacha.service.dto.output.GachaOutput;
import com.gacha_auction.gacha.service.dto.output.GachaOutput.GachaElementOutput;
import java.time.LocalDateTime;
import java.util.List;

public record GachaResponse(List<GachaElementResponse> gachas) {

    public static GachaResponse from(GachaOutput output) {
        final List<GachaElementResponse> elementResponses = output.gachas()
                .stream()
                .map(GachaElementResponse::from)
                .toList();
        return new GachaResponse(elementResponses);
    }

    public record GachaElementResponse(
            String title,
            String type,
            int totalCount,
            int restCount,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        private static GachaElementResponse from(final GachaElementOutput elementOutput) {
            return new GachaElementResponse(elementOutput.title().getValue(),
                    elementOutput.type().name(), elementOutput.totalCount(), elementOutput.restCount(),
                    elementOutput.period().getStartAt(), elementOutput.period().getEndAt());
        }
    }
}
