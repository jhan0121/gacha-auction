package com.gacha_auction.user.domain;

import com.gacha_auction.common.NullValidator;
import com.gacha_auction.common.SoftDeleteBaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldNameConstants(level = AccessLevel.PRIVATE)
@Getter
public class User extends SoftDeleteBaseEntity {

    private static final Long DEFAULT_COIN_AMOUNT = 1000L;

    public static User withoutId(final UserName name, final Password password) {
        return User.withoutId(name, password, Coin.from(DEFAULT_COIN_AMOUNT));
    }

    public static User withoutId(final UserName name, final Password password, final Coin coin) {
        validateNameAndPassword(name, password);
        return new User(name, password, coin);
    }

    private static void validateNameAndPassword(final UserName name, final Password password) {
        NullValidator.builder()
                .add(Fields.name, name)
                .add(Fields.password, password)
                .validate();
    }

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false))
    private UserName name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "password", nullable = false))
    private Password password;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "coin", nullable = false))
    private Coin coin;
}
