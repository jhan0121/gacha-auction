package com.gacha_auction.gacha.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.gacha_auction.exception.NotNullAllowedException;
import com.gacha_auction.item.domain.Item;
import com.gacha_auction.item.domain.ItemName;
import com.gacha_auction.item.domain.ItemType;
import com.gacha_auction.item.domain.Rarity;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GachaItemTest {

    @Test
    @DisplayName("id가 없는 GachaItem을 생성할 수 있다")
    void withoutId() {
        // given
        final Title title = Title.from("title");
        final LocalDateTime startAt = LocalDateTime.of(3000, 1, 1, 0, 0);
        final LocalDateTime endAt = LocalDateTime.of(3000, 12, 31, 0, 0);
        final GachaPeriod gachaPeriod = GachaPeriod.of(startAt, endAt);
        final Gacha gacha = Gacha.withoutId(title, gachaPeriod);

        final ItemName itemName = ItemName.from("itemName");
        final Item item = Item.withoutId(itemName, ItemType.LIMITED, Rarity.SSR);

        final Boolean isMain = true;

        // when
        final GachaItem actual = GachaItem.withoutId(gacha, item, isMain);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.getGacha().getTitle()).isEqualTo(title);
            softly.assertThat(actual.getGacha().getPeriod()).isEqualTo(gachaPeriod);
            softly.assertThat(actual.getIsMain()).isEqualTo(isMain);
        });
    }

    @ParameterizedTest
    @MethodSource("provideInvalidParameter")
    @DisplayName("필드 값이 null인 GachaItem을 생성 시, 예외를 발생한다")
    void createWithInvalidValue(final Gacha gacha, final Item item, final Boolean isMain, final String target) {
        // given
        // when
        // then
        assertThatThrownBy(() -> GachaItem.withoutId(gacha, item, isMain))
                .isInstanceOf(NotNullAllowedException.class)
                .hasMessageContaining("null이 될 수 없습니다: %s".formatted(target));
    }

    private static Stream<Arguments> provideInvalidParameter() {
        final Title title = Title.from("title");
        final LocalDateTime startAt = LocalDateTime.of(3000, 1, 1, 0, 0);
        final LocalDateTime endAt = LocalDateTime.of(3000, 12, 31, 0, 0);
        final GachaPeriod gachaPeriod = GachaPeriod.of(startAt, endAt);
        final Gacha gacha = Gacha.withoutId(title, gachaPeriod);

        final ItemName itemName = ItemName.from("itemName");
        final Item item = Item.withoutId(itemName, ItemType.LIMITED, Rarity.SSR);

        final Boolean isMain = true;

        return Stream.of(
                Arguments.of(null, item, isMain, "gacha"),
                Arguments.of(gacha, null, isMain, "item"),
                Arguments.of(gacha, item, null, "isMain")
        );
    }
}
