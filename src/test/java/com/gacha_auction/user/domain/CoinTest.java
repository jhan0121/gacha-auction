package com.gacha_auction.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.gacha_auction.exception.NotNullAllowedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CoinTest {

    @Test
    @DisplayName("from 메서드를 이용해 Coin을 생성할 수 있다")
    void from() {
        // given
        final Long amount = 100L;

        // when
        final Coin coin = Coin.from(amount);

        // then
        assertThat(coin.getAmount()).isEqualTo(amount);
    }

    @Test
    @DisplayName("value가 null일 시, 예외를 던진다")
    void throwIfValueIsNull() {
        //given
        //when
        //then
        assertThatThrownBy(() -> Coin.from(null))
                .isInstanceOf(NotNullAllowedException.class)
                .hasMessageContaining("null이 될 수 없습니다: amount");
    }

    @Test
    @DisplayName("value 길이가 0일 시, 예외를 던진다")
    void throwIfValueIsNegative() {
        //given
        final Long invalidAmount = -1000L;
        //when
        //then
        assertThatThrownBy(() -> Coin.from(invalidAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("coin의 값은 양수여야 합니다");
    }
}
