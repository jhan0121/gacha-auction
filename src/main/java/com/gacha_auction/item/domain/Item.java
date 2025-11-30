package com.gacha_auction.item.domain;

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
import lombok.experimental.FieldNameConstants;

@Entity
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldNameConstants(level = AccessLevel.PRIVATE)
@Getter
public class Item extends SoftDeleteBaseEntity {

    public static Item withoutId(final ItemName name, final ItemType type, final Rarity rarity) {
        validateNotNullValues(name, type, rarity);
        return new Item(name, type, rarity);
    }

    private static void validateNotNullValues(final ItemName name, final ItemType type, final Rarity rarity) {
        NullValidator.builder()
                .add(Fields.name, name)
                .add(Fields.type, type)
                .add(Fields.rarity, rarity)
                .validate();
    }

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false))
    private ItemName name;

    @Enumerated(value = EnumType.STRING)
    private ItemType type;

    @Enumerated(value = EnumType.STRING)
    private Rarity rarity;
}
