package br.com.williamrichard.rest;

import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class UserXMLTest {

    @Test
    public void devoTrabalharComXML() {
        given()
        .when()
            .get("https://restapi.wcaquino.me/usersXML/3")
        .then()
            .statusCode(200)
            .body("user.name", Matchers.is("Ana Julia"))
        ;
    }
}
