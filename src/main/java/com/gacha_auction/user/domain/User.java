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

    public static User withoutId(final String name, final String password) {
        return User.withoutId(name, password, DEFAULT_COIN_AMOUNT);
    }

    public static User withoutId(final String name, final String password, final Long coin) {
        validateNameAndPassword(name, password);
        final UserName userName = UserName.from(name);
        final Password userPassword = Password.from(password);
        final Coin userCoin = Coin.from(coin);
        return new User(userName, userPassword, userCoin);
    }

    private static void validateNameAndPassword(final String name, final String password) {
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
