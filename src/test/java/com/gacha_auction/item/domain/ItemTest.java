package com.gacha_auction.item.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.gacha_auction.exception.NotNullAllowedException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ItemTest {

    @Test
    @DisplayName("withoutId 메서드를 통해 id가 없는 Item을 생성할 수 있다")
    void withoutId() {
        // given
        final ItemName itemName = ItemName.from("itemName");
        final ItemType itemType = ItemType.NORMAL;
        final Rarity rarity = Rarity.SSR;

        // when
        final Item actual = Item.withoutId(itemName, itemType, rarity);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.getName()).isEqualTo(itemName);
            softly.assertThat(actual.getType()).isEqualTo(itemType);
            softly.assertThat(actual.getRarity()).isEqualTo(rarity);
        });
    }

    @ParameterizedTest
    @MethodSource("provideNullParameterCase")
    @DisplayName("필드 값이 null인 Item을 생성 시, 예외를 발생한다")
    void createWithNullPassword(final ItemName name, final ItemType itemType, final Rarity rarity,
                                final String target) {
        // given
        // when
        // then
        assertThatThrownBy(() -> Item.withoutId(name, itemType, rarity))
                .isInstanceOf(NotNullAllowedException.class)
                .hasMessageContaining("null이 될 수 없습니다: %s".formatted(target));
    }

    private static Stream<Arguments> provideNullParameterCase() {
        return Stream.of(
                Arguments.of(null, ItemType.NORMAL, Rarity.SSR, "name"),
                Arguments.of(ItemName.from("itemName"), null, Rarity.SSR, "type"),
                Arguments.of(ItemName.from("itemName"), ItemType.NORMAL, null, "rarity")
        );
    }
}
