import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RegisterUserTests {
    @Test
    void CyrilliсWordsTest() { //
        String registereuser = "{\"email\": \"Привет\",\"password\" : \"Автотесты\"}";
        given()// Если
                .body(registereuser)
                .contentType(JSON)
                .log().uri() //логи запроса
                .when()//Когда
                .post("https://reqres.in/api/register")

                .then()//Тогда
                .log().status()
                .log().body() // логи ответа
                .statusCode(400) // проверяем что статус код 400
                .body("error",is("Note: Only defined users succeed registration"));
        }

    @Test
    void emptyFieldsTest() { //
        String registereuser = "{\"email\": \"\",\"password\" : \"\"}";
        given()// Если
                .body(registereuser)
                .contentType(JSON)
                .log().uri() //логи запроса
                .when()//Когда
                .post("https://reqres.in/api/register")

                .then()//Тогда
                .log().status()
                .log().body() // логи ответа
                .statusCode(400) // проверяем что статус код 400
                .body("error",is("Missing email or username"));
    }

    @Test
    void newFieldRegisterTest() { //
        String registereuser = "{\"email\": \"eve.holt@reqres.in\",\"full name\" : \"Anastasiia\"}";
        given()// Если
                .body(registereuser)
                .contentType(JSON)
                .log().uri() //логи запроса
                .when()//Когда
                .post("https://reqres.in/api/register")

                .then()//Тогда
                .log().status()
                .log().body() // логи ответа
                .statusCode(400) // проверяем что статус код 400
                .body("error",is("Missing password"));
    }

    @Test
    void anotherUserTest() { //
        String registereuser = "{\"email\": \"anastasiia.rever\",\"password\": \"pochta\"}";
        given()// Если
                .body(registereuser)
                .contentType(JSON)
                .log().uri() //логи запроса
                .when()//Когда
                .post("https://reqres.in/api/register")

                .then()//Тогда
                .log().status()
                .log().body() // логи ответа
                .statusCode(400) // проверяем что статус код 400
                .body("error",is("Note: Only defined users succeed registration"));
    }

    @Test
    void unsuccessfulRegisterTest() { //
        String registereuser = "{\"email\": \"eve.holt@reqres.in\",\"password\": \"\"}";
        given()// Если
                .body(registereuser)
                .contentType(JSON)
                .log().uri() //логи запроса
                .when()//Когда
                .post("https://reqres.in/api/register")

                .then()//Тогда
                .log().status()
                .log().body() // логи ответа
                .statusCode(400) // проверяем что статус код 400L
                .body("error",is("Missing password"));
    }

    @Test
    void successfulRegisterTest() { //
        String registereuser = "{\"email\": \"eve.holt@reqres.in\",\"password\": \"pistol\"}";
        given()// Если
                .body(registereuser)
                .contentType(JSON)
                .log().uri() //логи запроса
        .when()//Когда
                .post("https://reqres.in/api/register")

        .then()//Тогда
                .log().status()
                .log().body() // логи ответа
                .statusCode(200) // проверяем что статус код 200
                .body("id", is(4), "token"
                        ,is("QpwL5tke4Pnpja7X4"));
    }
}
