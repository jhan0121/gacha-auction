package com.gacha_auction.gacha.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.gacha_auction.AbstractIntegrationTest;
import com.gacha_auction.gacha.controller.dto.response.GachaResponse;
import com.gacha_auction.gacha.domain.Gacha;
import com.gacha_auction.gacha.domain.GachaPeriod;
import com.gacha_auction.gacha.domain.GachaType;
import com.gacha_auction.gacha.domain.Title;
import com.gacha_auction.gacha.repository.GachaRepository;
import io.restassured.RestAssured;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GachaControllerTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private GachaRepository gachaRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        gachaRepository.deleteAll();
    }

    @Test
    @DisplayName("현재 진행 중인 가챠 목록을 조회할 수 있다")
    void findAllGachas() {
        // given
        final Title title = Title.from("title");
        final LocalDateTime startAt = LocalDateTime.now().minusDays(1);
        final LocalDateTime endAt = LocalDateTime.now().plusDays(1);
        final GachaPeriod period = GachaPeriod.of(startAt, endAt);
        final GachaType type = GachaType.NORMAL;
        final int totalCount = 100;
        final int restCount = 100;

        final Gacha gacha = Gacha.withoutId(title, period, type, totalCount, restCount);
        gachaRepository.save(gacha);

        // when
        final GachaResponse response = RestAssured.given().log().all()
                .when().get("/api/v1/gachas")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(GachaResponse.class);

        // then
        assertSoftly(softly -> {
            softly.assertThat(response.gachas()).hasSize(1);
            softly.assertThat(response.gachas().get(0).title()).isEqualTo(title.getValue());
            softly.assertThat(response.gachas().get(0).type()).isEqualTo(type.name());
            softly.assertThat(response.gachas().get(0).totalCount()).isEqualTo(totalCount);
            softly.assertThat(response.gachas().get(0).restCount()).isEqualTo(restCount);
        });
    }
}
