package com.gacha_auction.gacha.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.gacha_auction.exception.InvalidValueException;
import com.gacha_auction.exception.NotNullAllowedException;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class GachaPeriodTest {

    @Test
    @DisplayName("of 메서드를 이용해 GachaPeriod를 생성할 수 있다")
    void of() {
        // given
        final LocalDateTime startAt = LocalDateTime.of(3000, 1, 1, 0, 0);
        final LocalDateTime endAt = LocalDateTime.of(3000, 12, 31, 0, 0);

        // when
        final GachaPeriod actual = GachaPeriod.of(startAt, endAt);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.getStartAt()).isEqualTo(startAt);
            softly.assertThat(actual.getEndAt()).isEqualTo(endAt);
        });
    }

    @ParameterizedTest
    @MethodSource("provideNullCase")
    @DisplayName("startAt 또는 endAt이 null일 시, 예외를 던진다")
    void throwIfValueIsNull(final LocalDateTime startAt, final LocalDateTime endAt, final String target) {
        //given
        //when
        //then
        assertThatThrownBy(() -> GachaPeriod.of(startAt, endAt))
                .isInstanceOf(NotNullAllowedException.class)
                .hasMessageContaining("null이 될 수 없습니다: %s".formatted(target));
    }

    static Stream<Arguments> provideNullCase() {
        return Stream.of(
                Arguments.of(null, LocalDateTime.of(3000, 1, 1, 0, 0), "startAt"),
                Arguments.of(LocalDateTime.of(3000, 1, 1, 0, 0), null, "endAt")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"3000-01-01T00:00,3000-01-01T00:00", "3000-01-01T00:00,2999-01-02T00:00"})
    @DisplayName("startAt이 endAt보다 같거나 클시, 예외를 던진다")
    void throwIfStartAtIsLargerAndEqualsThanEndAt(final LocalDateTime startAt, final LocalDateTime endAt) {
        // given
        // when
        // then
        assertThatThrownBy(() -> GachaPeriod.of(startAt, endAt))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("가챠 시작 시간은 종료 시간보다 이전이어야 합니다: startAt=%s, endAt=%s".formatted(startAt, endAt));
    }
}
