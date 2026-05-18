package steps;

import static io.restassured.RestAssured.given;

public class RateStep {
    public  String getRatesResponse() {
        String url = "https://kurs.onliner.by/sdapi/kurs/api/bestrate?currency=EUR&type=nbrb";

       return given()
                .log().all()
                .when()
                .get(url)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .asString();
    }
}
