package io.quarkiverse.mongodb.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class MongodbResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/mongodb")
                .then()
                .statusCode(200)
                .body(is("Hello mongodb"));
    }
}
