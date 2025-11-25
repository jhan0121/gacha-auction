package com.gacha_auction.gacha.domain;

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
@ToString
@Getter
@EqualsAndHashCode
public class Title {

    public static Title from(final String value) {
        validateNotNullValue(value);
        validateTitleLength(value);
        return new Title(value);
    }

    private static void validateTitleLength(final String value) {
        if (value.isBlank()) {
            throw new InvalidValueException("가챠 타이틀은 빈 값일 수 없습니다");
        }
    }

    private static void validateNotNullValue(final String value) {
        NullValidator.builder()
                .add(Fields.value, value)
                .validate();
    }

    private String value;
}
