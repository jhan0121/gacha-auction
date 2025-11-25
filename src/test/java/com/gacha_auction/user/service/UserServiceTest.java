package com.gacha_auction.user.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.gacha_auction.exception.NotFoundException;
import com.gacha_auction.user.domain.Password;
import com.gacha_auction.user.domain.User;
import com.gacha_auction.user.domain.UserName;
import com.gacha_auction.user.repository.UserRepository;
import com.gacha_auction.user.service.dto.input.FindUserInput;
import com.gacha_auction.user.service.dto.input.UserInput;
import com.gacha_auction.user.service.dto.output.FindUserOutput;
import com.gacha_auction.user.service.dto.output.UserOutput;
import java.lang.reflect.Field;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ReflectionUtils;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    static final Long DEFAULT_COIN_AMOUNT = 1000L;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("유저를 저장한다")
    void save() {
        // given
        final UserName name = UserName.from("name");
        final Password password = Password.from("password");
        final UserInput input = new UserInput(name, password);
        User user = User.withoutId(name, password);
        setUserId(user, 1L);
        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        // when
        final UserOutput actual = userService.save(input);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.id()).isNotNull();
            softly.assertThat(actual.name()).isEqualTo(name.getValue());
        });
    }

    @Test
    @DisplayName("id를 기반으로 유저를 탐색하여 유저 정보를 제공한다")
    void findById() {
        // given
        final long targetId = 1L;
        final UserName name = UserName.from("name");
        final Password password = Password.from("password");
        User user = User.withoutId(name, password);
        setUserId(user, targetId);
        final FindUserInput findUserInput = FindUserInput.from(targetId);

        when(userRepository.findById(targetId))
                .thenReturn(Optional.of(user));

        // when
        final FindUserOutput actual = userService.findById(findUserInput);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.id()).isEqualTo(targetId);
            softly.assertThat(actual.name()).isEqualTo(name.getValue());
            softly.assertThat(actual.coin()).isEqualTo(DEFAULT_COIN_AMOUNT);
        });
    }

    @Test
    @DisplayName("존재하지 않는 id를 기반으로 유저를 탐색 시도 시, 예외를 던진다")
    void findByNotExistedId() {
        // given
        final Long notSavedUserId = 100L;
        final FindUserInput findUserInput = FindUserInput.from(notSavedUserId);
        when(userRepository.findById(notSavedUserId))
                .thenReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(() -> userService.findById(findUserInput))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("user not found");
    }


    private void setUserId(final User user, final Long id) {
        final Field idField = ReflectionUtils.findField(user.getClass().getSuperclass(), "id");
        if (idField != null) {
            idField.setAccessible(true);
            ReflectionUtils.setField(idField, user, id);
        }
    }
}
