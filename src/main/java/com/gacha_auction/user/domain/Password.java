package com.gacha_auction.user.domain;

import com.gacha_auction.common.NullValidator;
import com.gacha_auction.exception.InvalidValueException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldNameConstants(level = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode
public class Password {

    public static Password from(final String value) {
        validateNotNullValue(value);
        validateValue(value);
        return new Password(value);
    }

    private static void validateValue(final String value) {
        if (value.isBlank()) {
            throw new InvalidValueException("password는 빈 값일 수 없습니다");
        }
    }

    private static void validateNotNullValue(final String value) {
        NullValidator.builder()
                .add(Fields.value, value)
                .validate();
    }

    private String value;
}
