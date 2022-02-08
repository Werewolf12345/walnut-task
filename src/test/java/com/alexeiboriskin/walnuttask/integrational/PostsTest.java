package com.alexeiboriskin.walnuttask.integrational;

import com.alexeiboriskin.walnuttask.domain.PostResponse;
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
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostsTest {

    private static final String POSTS_ENDPOINT = "/api/posts";

    private MockMvc mockMvc;

    @BeforeAll
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        RestAssuredMockMvc.basePath = POSTS_ENDPOINT;
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void test_get_posts_tag_only() {

        PostResponse response = given()
                .queryParam("tags", "tech")
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(PostResponse.class);

        assertThat(response.getPosts()).isNotEmpty();
    }

    @Test
    void test_get_posts_no_tag() {

        given()
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body(containsString("Tags parameter is required"));
    }

    @Test
    void test_get_posts_tag_and_sort() {

        PostResponse response = given()
                .queryParam("tags", "tech")
                .queryParam("sortBy", "id")
                .queryParam("direction", "asc")
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(PostResponse.class);

        assertThat(response.getPosts()).isNotEmpty();
    }

    @Test
    void test_get_posts_bad_sortBy() {

        given()
                .queryParam("tags", "tech")
                .queryParam("sortBy", "nonExistingField")
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body(containsString("sortBy parameter is invalid"));
    }

    @Test
    void test_get_posts_bad_direction() {

        given()
                .queryParam("tags", "tech")
                .queryParam("direction", "nonExistingField")
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body(containsString("direction parameter is invalid"));
    }
}