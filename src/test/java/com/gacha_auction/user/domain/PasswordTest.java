package com.gacha_auction.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.gacha_auction.exception.InvalidValueException;
import com.gacha_auction.exception.NotNullAllowedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;

class PasswordTest {

    @Test
    @DisplayName("from 메서드를 이용해 Password를 생성할 수 있다")
    void from() {
        // given
        final String value = "password";

        // when
        final Password actual = Password.from(value);

        // then
        assertThat(actual.getValue()).isEqualTo(value);
    }

    @Test
    @DisplayName("value가 null일 시, 예외를 던진다")
    void throwIfValueIsNull() {
        //given
        //when
        //then
        assertThatThrownBy(() -> Password.from(null))
                .isInstanceOf(NotNullAllowedException.class)
                .hasMessageContaining("null이 될 수 없습니다: value");
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("value 길이가 0일 시, 예외를 던진다")
    void throwIfValueIsEmpty(final String invalidValue) {
        //given
        //when
        //then
        assertThatThrownBy(() -> Password.from(invalidValue))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("password는 빈 값일 수 없습니다");
    }
}
