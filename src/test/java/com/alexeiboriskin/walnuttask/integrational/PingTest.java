package com.alexeiboriskin.walnuttask.integrational;

import com.alexeiboriskin.walnuttask.domain.PingResponse;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PingTest {

    private static final String PING_ENDPOINT = "/api/ping";

    private MockMvc mockMvc;

    @BeforeAll
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        RestAssuredMockMvc.basePath = PING_ENDPOINT;
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void test_get_successful_ping() {

        PingResponse response = given()
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(PingResponse.class);

        assertThat(response).isEqualTo(new PingResponse());
    }
}