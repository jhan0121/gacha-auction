package com.gacha_auction.user.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.gacha_auction.exception.NotNullAllowedException;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class UserTest {

    @Test
    @DisplayName("id가 없는 유저를 생성할 수 있다")
    void createWithoutId() {
        // given
        final UserName name = UserName.from("test");
        final Password password = Password.from("password");
        final Long defaultCoinAmount = 1000L;

        // when
        final User actual = User.withoutId(name, password);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actual.getName().getValue()).isEqualTo(name);
            softly.assertThat(actual.getPassword().getValue()).isEqualTo(password);
            softly.assertThat(actual.getCoin().getAmount()).isEqualTo(defaultCoinAmount);
        });
    }

    @ParameterizedTest
    @MethodSource("provideNullParameterCase")
    @DisplayName("필드 값이 null인 유저를 생성 시, 예외를 발생한다")
    void createWithNullPassword(final UserName name, final Password password, final String target) {
        // given
        // when
        // then
        assertThatThrownBy(() -> User.withoutId(name, password))
                .isInstanceOf(NotNullAllowedException.class)
                .hasMessageContaining("null이 될 수 없습니다: %s".formatted(target));
    }

    private static Stream<Arguments> provideNullParameterCase() {
        return Stream.of(
                Arguments.of(null, Password.from("password"), "name"),
                Arguments.of(UserName.from("name"), null, "password")
        );
    }
}
