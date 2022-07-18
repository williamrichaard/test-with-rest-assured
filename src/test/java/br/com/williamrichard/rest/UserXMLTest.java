package br.com.williamrichard.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.internal.path.xml.NodeImpl;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserXMLTest {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;


    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://restapi.wcaquino.me";
        //RestAssured.port = 443;
        //RestAssured.basePath = "/v2";

        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.log(LogDetail.ALL);
        reqSpec = reqBuilder.build();

        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectStatusCode(200);
        resSpec = resBuilder.build();

        RestAssured.requestSpecification = reqSpec;
        RestAssured.responseSpecification = resSpec;
    }

    @Test
    public void devoTrabalharComXML() {

        given()
        .when()
            .get("/usersXML/3")
        .then()
            //.statusCode(200)

            .rootPath("user")
            .body("name", is("Ana Julia"))
            .body("@id", is("3"))

            .rootPath("user.filhos")
            .body("name.size()", is(2))

            .detachRootPath("filhos")
            .body("filhos.name[0]", is("Zezinho"))
            .body("filhos.name[1]", is("Luizinho"))
            .appendRootPath("filhos")
            .body("name", hasItem("Luizinho"))
            .body("name", hasItems("Luizinho", "Zezinho"));
    }

    @Test
    public void devoFazerPesquisasAvancadasComXML() {
        given()
        .when()
            .get("/usersXML")
        .then()
            .body("users.user.size()", is(3))
            .body("users.user.findAll{it.age.toInteger() <= 25}.size()", is(2))
            .body("users.user.@id", hasItems("1", "2", "3"))
            .body("users.user.find{it.age == 25}.name", is("Maria Joaquina"))
            .body("users.user.findAll{it.name.toString().contains('n')}.name", hasItems("Maria Joaquina", "Ana Julia"))
            .body("users.user.salary.find{it != null}", is("1234.5678"))
            .body("users.user.salary.find{it != null}.toDouble()", is(1234.5678d))
            .body("users.user.age.collect{it.toInteger() * 2}", hasItems(40, 50, 60))
            .body("users.user.name.findAll{it.toString().startsWith('Maria')}.collect{it.toString().toUpperCase()}", is("MARIA JOAQUINA"));
    }

    @Test
    public void devoFazerPesquisasAvancadasComXMLEJava() {
        ArrayList<NodeImpl> nomes = given()
        .when()
        .get("/usersXML")
        .then()
            .extract().path("users.user.name.findAll{it.toString().contains('n')}");
        Assert.assertEquals(2, nomes.size());
        Assert.assertEquals("Maria Joaquina".toUpperCase(), nomes.get(0).toString().toUpperCase());
        Assert.assertTrue("ANA JULIA".equalsIgnoreCase(nomes.get(1).toString()));;;;
    }
}
