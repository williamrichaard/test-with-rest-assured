package br.com.williamrichard.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RestAssuredTests {

    @Test
    public void testRestAssured() {
        Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
        assertEquals("Ola Mundo!", response.getBody().asString());
        assertEquals(200, response.statusCode());

        ValidatableResponse validation = response.then();
        validation.statusCode(201);
    }
}
