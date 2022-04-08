package br.com.williamrichard.rest;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class RestAssuredTests {

    @Test
    public void testRestAssured() {
        Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
        assertEquals("Ola Mundo!", response.getBody().asString());
        assertEquals(200, response.statusCode());
        assertEquals("O status code deveria ser 200", 200, response.statusCode());
        assertEquals(200, response.statusCode());

        ValidatableResponse validation = response.then();
        validation.statusCode(200);
    }

    @Test
    public void devoConhecerOutrasFormasRestAssured() {
        Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
        ValidatableResponse validation = response.then();
        validation.statusCode(200);

        get("http://restapi.wcaquino.me/ola").then().statusCode(200);

        //given -> pré condicoes
        //when -> ação
        //then -> verificação

        given()
        .when().get("http://restapi.wcaquino.me/ola")
        .then().statusCode(200);
    }

    @Test
    public void devoConhecerMatchersHamcrest() {
        assertThat("Maria", is("Maria"));
        assertThat(128, Matchers.is(128));
        assertThat(128, Matchers.isA(Integer.class));
        assertThat(128d, Matchers.isA(Double.class));
        assertThat(128d, Matchers.greaterThan(120d));
        assertThat(128d, Matchers.lessThan(130d));

        List<Integer> impares = List.of(1,3,5,7,9);
        assertThat(impares, hasSize(5));
        assertThat(impares, contains(1,3,5,7,9));
        assertThat(impares, containsInAnyOrder(1,3,5,9,7));
        assertThat(impares, hasItem(1));
        assertThat(impares, hasItems(1, 5));

        assertThat("Maria", is(not("Joao")));
        assertThat("Maria", not("Joao"));
        assertThat("Maria", anyOf(is("Maria"), is("Joaquina")));
        assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));
    }
}