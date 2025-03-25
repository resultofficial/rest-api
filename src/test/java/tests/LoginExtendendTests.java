package tests;

import models.LoginBodyModel;
import models.LoginResponseLombokModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginExtendendTests {

    /*
   1. Make request (POST) to https://reqres.in/api/login
       with body { "email": "eve.holt@reqres.in", "password": "cityslicka" }
   2. Get response { "token": "QpwL5tke4Pnpja7X4" }
   3. Check "token" is "QpwL5tke4Pnpja7X4" and status code 200
 */
    @Test
    void MissingPasswordTest() { //нет пароля
        String authData = "{\"email\": \"111eve.holt@reqres.in\"}";
        given()// Если
                .body(authData)
                .contentType(JSON)
                .log().uri() //логи запроса
                .when()//Когда
                .post("https://reqres.in/api/login")

                .then()//Тогда
                .log().status()
                .log().body() // логи ответа
                .statusCode(400) // проверяем что статус код 200
                .body("error", is("Missing password"));
    }

    @Test
    void unsuccessfulUserNotFoundTest() { //не верный email
        String authData = "{\"email\": \"111eve.holt@reqres.in\",\"password\": \"pistol\"}";
        given()// Если
                .body(authData)
                .contentType(JSON)
                .log().uri() //логи запроса
                .when()//Когда
                .post("https://reqres.in/api/login")

                .then()//Тогда
                .log().status()
                .log().body() // логи ответа
                .statusCode(400) // проверяем что статус код 200
                .body("error", is("user not found"));
    }

    @Test
    void unsuccessful400LoginTest() { // удалаили contentType
        String authData = "{\"email\": \"eve.holt@reqres.in\",\"password\": \"pistol\"}";
        given()// Если
                .body(authData)
                .log().uri() //логи запроса
                .when()//Когда
                .post("https://reqres.in/api/login")

                .then()//Тогда
                .log().status()
                .log().body() // логи ответа
                .statusCode(400) // проверяем что статус код 200
                .body("error", is("Missing email or username"));
    }

    @Test
    void successfulLoginTest() { // 200 ок + токен ок
       // String authData = "{\"email\": \"eve.holt@reqres.in\",\"password\": \"pistol\"}";
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = given()// Если
                .body(authData)
                .contentType(JSON)
                .log().uri() //логи запроса

                .when()//Когда
                .post("https://reqres.in/api/login")

                .then()//Тогда
                .log().status()
                .log().body() // логи ответа
                .statusCode(200) // проверяем что статус код 200
                .extract().as(LoginResponseLombokModel.class)  ;
        assertEquals("QpwL5tke4Pnpja7X4" , response .getToken());
    }

}
