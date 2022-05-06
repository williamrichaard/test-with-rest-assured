package br.com.williamrichard.rest;

import org.junit.Test;

import static io.restassured.RestAssured.given;

public class UserXMLTest {

    @Test
    public void devoTrabalharComXML() {
        given()
        .when()
            .get("https://restapi.wcaquino.me/usersXML/3")
        .then()
        ;
    }
}
