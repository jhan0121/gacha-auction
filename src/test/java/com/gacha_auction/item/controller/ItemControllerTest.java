package com.gacha_auction.item.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.gacha_auction.AbstractIntegrationTest;
import com.gacha_auction.exception.dto.ErrorResponse;
import com.gacha_auction.item.controller.dto.request.ItemCreationRequest;
import com.gacha_auction.item.controller.dto.response.FindItemResponse;
import com.gacha_auction.item.controller.dto.response.ItemCreationResponse;
import com.gacha_auction.item.domain.ItemType;
import com.gacha_auction.item.domain.Rarity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.net.URI;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ItemControllerTest extends AbstractIntegrationTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("아이템 정보를 조회할 수 있다")
    void getItemInfo() {
        // given
        final String name = "itemName";
        final ItemType itemType = ItemType.NORMAL;
        final Rarity rarity = Rarity.SSR;
        final ItemCreationRequest request = new ItemCreationRequest(name, itemType, rarity);
        final ItemCreationResponse response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/api/v1/items")
                .then()
                .statusCode(HttpStatus.CREATED.value()).extract()
                .jsonPath().getObject(".", ItemCreationResponse.class);
        final URI uri = URI.create("/api/v1/items/" + response.id());

        // when
        final FindItemResponse findItemResponse = RestAssured.given().log().all()
                .when().get(uri)
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract()
                .jsonPath().getObject(".", FindItemResponse.class);

        // then
        assertSoftly(softly -> {
            softly.assertThat(findItemResponse.id()).isEqualTo(response.id());
            softly.assertThat(findItemResponse.itemName()).isEqualTo(name);
            softly.assertThat(findItemResponse.itemType()).isEqualTo(itemType);
            softly.assertThat(findItemResponse.rarity()).isEqualTo(rarity);
        });
    }

    @Test
    @DisplayName("존재하지 않는 아이템 id로 조회 시도 시, Not Found를 응답한다")
    void getNotExistedUserInfo() {
        // given
        final long notExistedId = 1000L;
        final URI uri = URI.create("/api/v1/items/" + notExistedId);

        // when
        // then
        RestAssured.given().log().all()
                .when().get(uri)
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("아이템을 저장할 수 있다")
    void save() {
        // given
        final String name = "itemName";
        final ItemType itemType = ItemType.NORMAL;
        final Rarity rarity = Rarity.SSR;
        final ItemCreationRequest request = new ItemCreationRequest(name, itemType, rarity);
        final URI uri = URI.create("/api/v1/items");

        // when
        final ItemCreationResponse itemCreationResponse = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(uri)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value()).extract()
                .jsonPath().getObject(".", ItemCreationResponse.class);

        // then
        assertSoftly(softly -> {
            softly.assertThat(itemCreationResponse.id()).isNotNull();
            softly.assertThat(itemCreationResponse.itemName()).isEqualTo(name);
            softly.assertThat(itemCreationResponse.itemType()).isEqualTo(itemType);
            softly.assertThat(itemCreationResponse.rarity()).isEqualTo(rarity);
        });
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCase")
    @DisplayName("아이템 정보 저장 기능에서 누락된 값이 존재할 시, Bad Request를 응답한다")
    void saveInvalidFormat(final String name, ItemType itemType, Rarity rarity) {
        // given
        final ItemCreationRequest request = new ItemCreationRequest(name, itemType, rarity);
        final URI uri = URI.create("/api/v1/items");

        // when
        final ErrorResponse errorResponse = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(uri)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value()).extract()
                .jsonPath().getObject(".", ErrorResponse.class);

        // then
        assertThat(errorResponse.message()).contains("null이 될 수 없습니다");
    }

    static Stream<Arguments> provideInvalidCase() {
        return Stream.of(
                Arguments.of(null, ItemType.NORMAL, Rarity.SSR),
                Arguments.of("name", null, Rarity.SSR),
                Arguments.of("name", ItemType.NORMAL, null)
        );
    }
}
