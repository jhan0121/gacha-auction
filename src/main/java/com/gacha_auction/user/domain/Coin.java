package com.gacha_auction.user.domain;

import com.gacha_auction.common.NullValidator;
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
public class Coin {

    public static Coin from(final Long amount) {
        validateNotNullAmount(amount);
        validateAmount(amount);
        return new Coin(amount);
    }

    private static void validateAmount(final Long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("coin의 값은 양수여야 합니다");
        }
    }

    private static void validateNotNullAmount(final Long amount) {
        NullValidator.builder()
                .add(Fields.amount, amount)
                .validate();
    }

    private Long amount;
}
