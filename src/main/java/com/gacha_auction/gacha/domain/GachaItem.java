package com.gacha_auction.gacha.domain;

import com.gacha_auction.common.NullValidator;
import com.gacha_auction.common.SoftDeleteBaseEntity;
import com.gacha_auction.item.domain.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Entity
@Table(name = "gacha_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldNameConstants(level = AccessLevel.PRIVATE)
@Getter
@ToString
public class GachaItem extends SoftDeleteBaseEntity {

    public static GachaItem withoutId(final Gacha gacha, final Item item, final Boolean isMain) {
        validateNotNullValues(gacha, item, isMain);
        return new GachaItem(gacha, item, isMain);
    }

    private static void validateNotNullValues(final Gacha gacha, final Item item, final Boolean isMain) {
        NullValidator.builder()
                .add(Fields.gacha, gacha)
                .add(Fields.item, item)
                .add(Fields.isMain, isMain)
                .validate();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gacha_id", nullable = false)
    private Gacha gacha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "is_main", nullable = false)
    private Boolean isMain;
}
