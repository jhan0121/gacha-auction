package com.gacha_auction.gacha.repository;

import com.gacha_auction.gacha.domain.Gacha;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GachaRepository extends JpaRepository<Gacha, Long> {

    @Query(value = """
            select g
            from Gacha g
            where :dateTime between g.period.startAt and g.period.endAt
            """)
    List<Gacha> findAllGachasBetweenPeriod(@Param("dateTime") LocalDateTime targetDateTime);
}
