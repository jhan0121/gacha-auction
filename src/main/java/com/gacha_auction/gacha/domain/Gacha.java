package com.gacha_auction.gacha.domain;

import com.gacha_auction.common.NullValidator;
import com.gacha_auction.common.SoftDeleteBaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Entity
@Table(name = "gacha")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldNameConstants(level = AccessLevel.PRIVATE)
@Getter
@ToString
public class Gacha extends SoftDeleteBaseEntity {

    public static Gacha withoutId(
            final Title title,
            final GachaPeriod period,
            final GachaType type,
            final int totalCount,
            final int restCount
    ) {
        validateNotNullValues(title, period);
        return new Gacha(title, period, type, totalCount, restCount);
    }

    private static void validateNotNullValues(final Title title, final GachaPeriod period) {
        NullValidator.builder()
                .add(Fields.title, title)
                .add(Fields.period, period)
                .validate();
    }

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "title", nullable = false))
    private Title title;

    @Embedded
    @AttributeOverride(name = "startAt", column = @Column(name = "start_at", nullable = false))
    @AttributeOverride(name = "endAt", column = @Column(name = "end_at", nullable = false))
    private GachaPeriod period;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gacha_type", nullable = false)
    private GachaType gachaType;

    @Column(name = "total_count", nullable = false)
    private int totalCount;

    @Column(name = "rest_count", nullable = false)
    private int restCount;
}
