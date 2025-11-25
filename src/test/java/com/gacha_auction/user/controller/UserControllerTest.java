package com.gacha_auction.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.gacha_auction.TestcontainersConfiguration;
import com.gacha_auction.exception.dto.ErrorResponse;
import com.gacha_auction.user.controller.dto.request.UserRequest;
import com.gacha_auction.user.controller.dto.response.FindUserResponse;
import com.gacha_auction.user.controller.dto.response.UserResponse;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
class UserControllerTest {

    static final Long DEFAULT_COIN_AMOUNT = 1000L;

    @LocalServerPort
    int port;

    @Autowired
    UserController userController;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("유저 정보를 조회할 수 있다")
    void getUserInfo() {
        // given
        final String name = "test";
        final String password = "password";
        final UserRequest request = new UserRequest(name, password);
        final UserResponse response = userController.save(request).getBody();
        final URI uri = URI.create("/api/v1/users/" + response.id());

        // when
        final FindUserResponse findUserResponse = RestAssured.given().log().all()
                .when().get(uri)
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract()
                .jsonPath().getObject(".", FindUserResponse.class);

        // then
        assertSoftly(softly -> {
            softly.assertThat(findUserResponse.id()).isEqualTo(response.id());
            softly.assertThat(findUserResponse.name()).isEqualTo(response.name());
            softly.assertThat(findUserResponse.coin()).isEqualTo(DEFAULT_COIN_AMOUNT);
        });
    }

    @Test
    @DisplayName("존재하지 않는 유저 id로 조회 시도 시, Not Found를 응답한다")
    void getNotExistedUserInfo() {
        // given
        final long notExistedId = 1000L;
        final URI uri = URI.create("/api/v1/users/" + notExistedId);

        // when
        // then
        RestAssured.given().log().all()
                .when().get(uri)
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("유저 정보를 저장할 수 있다")
    void save() {
        // given
        final String name = "test";
        final String password = "password";
        final UserRequest request = new UserRequest(name, password);
        final URI uri = URI.create("/api/v1/users");

        // when
        final UserResponse userResponse = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(uri)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value()).extract()
                .jsonPath().getObject(".", UserResponse.class);

        // then
        assertSoftly(softly -> {
            softly.assertThat(userResponse.id()).isNotNull();
            softly.assertThat(userResponse.name()).isEqualTo(name);
        });
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCase")
    @DisplayName("유저 정보 저장 기능에서 누락된 값이 존재할 시, Bad Request를 응답한다")
    void saveInvalidFormat(final String name, final String password, final String errorMessage) {
        // given
        final UserRequest request = new UserRequest(name, password);
        final URI uri = URI.create("/api/v1/users");

        // when
        final ErrorResponse errorResponse = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(uri)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value()).extract()
                .jsonPath().getObject(".", ErrorResponse.class);

        // then
        assertThat(errorResponse.message()).isEqualTo(errorMessage);
    }

    static Stream<Arguments> provideInvalidCase() {
        return Stream.of(
                Arguments.of(null, "password", "null이 될 수 없습니다: name"),
                Arguments.of("name", null, "null이 될 수 없습니다: password")
        );
    }
}
