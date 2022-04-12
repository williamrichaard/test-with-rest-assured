package br.com.williamrichard.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static java.lang.Integer.valueOf;
import static org.hamcrest.Matchers.*;

public class UserJsonTest {

    @Test
    public void deveVerficarPrimeiroNivel() {
        given()
        .when()
            .get("http://restapi.wcaquino.me/users/1")
        .then()
            .statusCode(200)
            .body("id", is(1))
            .body("name", containsString("Silva"))
            .body("age", greaterThan(18));
    }

    @Test
    public void deveVerificarPrimeiroNivelOutrasFormas() {
        Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/users/1");

        //path
        Assert.assertEquals(valueOf(1), response.path("id"));
        Assert.assertEquals(valueOf(1), response.path("%s", "id"));

        //jsonpath
        JsonPath jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(1, jsonpath.getInt("id"));

        //from
        int id = JsonPath.from(response.asString()).getInt("id");
        Assert.assertEquals(1, id);
    }

    @Test
    public void deveVerificarSegundoNivel() {
        given()
        .when()
            .get("http://restapi.wcaquino.me/users/2")
        .then()
            .statusCode(200)
            .body("id", is(1))
            .body("name", containsString("Joaquina"))
            .body("endereco.rua", is("Rua dos Bobos"))
            .body("endereco.numero", is(0));
    }
}
