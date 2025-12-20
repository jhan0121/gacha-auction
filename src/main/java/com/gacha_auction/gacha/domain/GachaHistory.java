package com.gacha_auction.gacha.domain;


import com.gacha_auction.common.NullValidator;
import com.gacha_auction.common.SoftDeleteBaseEntity;
import com.gacha_auction.user.domain.User;
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
@Table(name = "gacha_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants(level = AccessLevel.PRIVATE)
@Getter
@ToString
public class GachaHistory extends SoftDeleteBaseEntity {

    public static GachaHistory withoutId(final User user, final GachaItem gachaItem) {
        validateNotNullValues(user, gachaItem);
        return new GachaHistory(user, gachaItem);
    }

    private static void validateNotNullValues(final User user, final GachaItem gachaItem) {
        NullValidator.builder()
                .add(Fields.user, user)
                .add(Fields.gachaItem, gachaItem)
                .validate();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gacha_item_id", nullable = false)
    private GachaItem gachaItem;
}
