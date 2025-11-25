package com.gacha_auction.gacha.domain;

import com.gacha_auction.common.NullValidator;
import com.gacha_auction.exception.InvalidValueException;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
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
public class GachaPeriod {

    public static GachaPeriod of(final LocalDateTime startAt, final LocalDateTime endAt) {
        validateNotNullValues(startAt, endAt);
        validateInvalidValues(startAt, endAt);
        return new GachaPeriod(startAt, endAt);
    }

    private static void validateNotNullValues(final LocalDateTime startAt, final LocalDateTime endAt) {
        NullValidator.builder()
                .add(Fields.startAt, startAt)
                .add(Fields.endAt, endAt)
                .validate();
    }

    private static void validateInvalidValues(final LocalDateTime startAt, final LocalDateTime endAt) {
        if (startAt.isEqual(endAt) || startAt.isAfter(endAt)) {
            throw new InvalidValueException(
                    "가챠 시작 시간은 종료 시간보다 이전이어야 합니다: startAt=%s, endAt=%s".formatted(startAt, endAt)
            );
        }
    }

    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
