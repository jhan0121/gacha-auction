package com.gacha_auction.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@Getter
public abstract class SoftDeleteBaseEntity extends BaseEntity {

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;
}
