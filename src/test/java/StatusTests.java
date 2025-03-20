import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class StatusTests {
  /*
    1. Make request to https://selenoid.autotests.cloud/status
    2. Get response { total: 5, used: 0, queued: 0, pending: 0, browsers: ...
    3. Check total is 20
     */

    @Test
    void checkTotal20() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));
    }


    @Test
    void checkTotalWitchLogsResponse() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .body("total", is(20));
    }


    @Test
    void checkTotalWitchLogs() {
        given()
                .log().all() //логи запроса
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all() // логи ответа
                .body("total", is(20));
    }
}

