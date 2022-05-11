package br.com.williamrichard.rest;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserXMLTest {

    @Test
    public void devoTrabalharComXML() {
        given()
        .when()
            .get("https://restapi.wcaquino.me/usersXML/3")
        .then()
            .statusCode(200)

            .rootPath("user")
            .body("name", is("Ana Julia"))
            .body("@id", is("3"))
            .rootPath("user.filhos")
            .body("filhos.name.size()", is(2))
            .body("filhos.name[0]", is("Zezinho"))
            .body("filhos.name[1]", is("Luizinho"))
            .body("filhos.name", hasItem("Luizinho"))
            .body("filhos.name", hasItems("Luizinho", "Zezinho"));
    }
}
