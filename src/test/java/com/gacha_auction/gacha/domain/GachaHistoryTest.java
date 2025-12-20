package com.gacha_auction.gacha.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.gacha_auction.exception.NotNullAllowedException;
import com.gacha_auction.item.domain.Item;
import com.gacha_auction.item.domain.ItemName;
import com.gacha_auction.item.domain.ItemType;
import com.gacha_auction.item.domain.Rarity;
import com.gacha_auction.user.domain.Password;
import com.gacha_auction.user.domain.User;
import com.gacha_auction.user.domain.UserName;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GachaHistoryTest {

    @Test
    @DisplayName("id가 없는 GachaHistory를 생성할 수 있다")
    void withoutId() {
        // given
        final User user = User.withoutId(UserName.from("userName"), Password.from("password1234"));
        final Gacha gacha = Gacha.withoutId(
                Title.from("title"),
                GachaPeriod.of(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)),
                GachaType.NORMAL,
                100,
                100
        );
        final Item item = Item.withoutId(ItemName.from("itemName"), ItemType.NORMAL, Rarity.SSR);
        final GachaItem gachaItem = GachaItem.withoutId(gacha, item, true);

        // when
        final GachaHistory actual = GachaHistory.withoutId(user, gachaItem);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.getUser()).isEqualTo(user);
            softly.assertThat(actual.getGachaItem()).isEqualTo(gachaItem);
        });
    }

    @ParameterizedTest
    @MethodSource("provideInvalidParameter")
    @DisplayName("필드 값이 null인 GachaHistory를 생성 시, 예외를 발생한다")
    void createWithInvalidValue(final User user, final GachaItem gachaItem, final String target) {
        // given
        // when
        // then
        assertThatThrownBy(() -> GachaHistory.withoutId(user, gachaItem))
                .isInstanceOf(NotNullAllowedException.class)
                .hasMessageContaining("null이 될 수 없습니다: %s".formatted(target));
    }

    private static Stream<Arguments> provideInvalidParameter() {
        final User user = User.withoutId(UserName.from("userName"), Password.from("password1234"));
        final Gacha gacha = Gacha.withoutId(
                Title.from("title"),
                GachaPeriod.of(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)),
                GachaType.NORMAL,
                100,
                100
        );
        final Item item = Item.withoutId(ItemName.from("itemName"), ItemType.NORMAL, Rarity.SSR);
        final GachaItem gachaItem = GachaItem.withoutId(gacha, item, true);

        return Stream.of(
                Arguments.of(null, gachaItem, "user"),
                Arguments.of(user, null, "gachaItem")
        );
    }
}
