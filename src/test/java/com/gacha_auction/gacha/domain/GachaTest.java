package com.gacha_auction.gacha.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.gacha_auction.exception.NotNullAllowedException;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GachaTest {

    @Test
    @DisplayName("id가 없는 Gacha를 생성할 수 있다")
    void withoutId() {
        //given
        final Title title = Title.from("title");
        final LocalDateTime startAt = LocalDateTime.of(3000, 1, 1, 0, 0);
        final LocalDateTime endAt = LocalDateTime.of(3000, 12, 31, 0, 0);
        final GachaPeriod period = GachaPeriod.of(startAt, endAt);
        final GachaType type = GachaType.NORMAL;
        final int totalCount = 100;
        final int restCount = 100;

        //when
        final Gacha actual = Gacha.withoutId(title, period, type, totalCount, restCount);

        //then
        assertSoftly(softly -> {
            softly.assertThat(actual.getTitle()).isEqualTo(title);
            softly.assertThat(actual.getPeriod()).isEqualTo(period);
            softly.assertThat(actual.getGachaType()).isEqualTo(type);
            softly.assertThat(actual.getTotalCount()).isEqualTo(totalCount);
            softly.assertThat(actual.getRestCount()).isEqualTo(restCount);
        });
    }

    @ParameterizedTest
    @MethodSource("provideNullParameterCase")
    @DisplayName("필드 값이 null인 Gacha를 생성 시, 예외를 발생한다")
    void createWithInvalidValue(final Title title, final GachaPeriod period, final String target) {
        // given
        final GachaType type = GachaType.NORMAL;
        final int totalCount = 100;
        final int restCount = 100;

        // when
        // then
        assertThatThrownBy(() -> Gacha.withoutId(title, period, type, totalCount, restCount))
                .isInstanceOf(NotNullAllowedException.class)
                .hasMessageContaining("null이 될 수 없습니다: %s".formatted(target));
    }

    private static Stream<Arguments> provideNullParameterCase() {
        return Stream.of(
                Arguments.of(null, GachaPeriod.of(
                                LocalDateTime.of(3000, 1, 1, 0, 0),
                                LocalDateTime.of(3000, 12, 31, 0, 0)),
                        "title"),
                Arguments.of(Title.from("title"), null, "period")
        );
    }
}
