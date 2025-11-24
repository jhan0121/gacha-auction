package com.gacha_auction.user.domain;

import com.gacha_auction.common.SoftDeleteBaseEntity;
import com.gacha_auction.exception.NotNullAllowedException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User extends SoftDeleteBaseEntity {

    private static final Long DEFAULT_COIN_AMOUNT = 1000L;

    public static User withoutId(final String name, final String password) {
        validateNotNull(name, password);
        return new User(name, password, DEFAULT_COIN_AMOUNT);
    }

    private static void validateNotNull(final String name, final String password) {
        if (name == null) {
            throw new NotNullAllowedException("name must not null");
        }
        if (password == null) {
            throw new NotNullAllowedException("password must not null");
        }
    }

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "coin", nullable = false)
    private Long coin;
}
