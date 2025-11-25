package com.gacha_auction.user.domain;

import com.gacha_auction.common.NullValidator;
import com.gacha_auction.exception.InvalidValueException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
public class UserName {

    public static UserName from(final String value) {
        validateNotNullValue(value);
        if (value.isBlank() || value.length() > 10) {
            throw new InvalidValueException("유저명은 1자 이상 10자 이하여야 합니다");
        }
        return new UserName(value);
    }

    private static void validateNotNullValue(final String value) {
        NullValidator.builder()
                .add(Fields.value, value);
    }

    private String value;
}
