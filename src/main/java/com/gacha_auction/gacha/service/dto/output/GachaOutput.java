package com.gacha_auction.gacha.service.dto.output;

import com.gacha_auction.gacha.domain.Gacha;
import com.gacha_auction.gacha.domain.GachaPeriod;
import com.gacha_auction.gacha.domain.GachaType;
import com.gacha_auction.gacha.domain.Title;
import java.util.List;

public record GachaOutput(List<GachaElementOutput> gachas) {

    public static GachaOutput from(final List<Gacha> gachas) {
        final List<GachaElementOutput> gachaElements = gachas.stream()
                .map(gacha -> GachaElementOutput.of(gacha.getTitle(), gacha.getGachaType(), gacha.getTotalCount(),
                        gacha.getRestCount(), gacha.getPeriod()))
                .toList();
        return new GachaOutput(gachaElements);
    }

    public record GachaElementOutput(
            Title title,
            GachaType type,
            int totalCount,
            int restCount,
            GachaPeriod period
    ) {
        private static GachaElementOutput of(
                final Title title,
                final GachaType type,
                final int totalCount,
                final int restCount,
                final GachaPeriod period
        ) {
            return new GachaElementOutput(title, type, totalCount, restCount, period);
        }
    }
}
