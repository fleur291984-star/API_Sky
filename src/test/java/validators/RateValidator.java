package validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.core.StringContains.containsString;
import static org.testng.Assert.assertTrue;

public class RateValidator {
    String url = "https://kurs.onliner.by/sdapi/kurs/api/bestrate?currency=EUR&type=nbrb";

    public void validateSchema(Integer correctResponse) {
        given()
                .when()
                .get(url)
                .then()
                .log().all()
                .statusCode(correctResponse)
                .body(matchesJsonSchemaInClasspath("schemas/rate_schema.json"));
    }

    public void validateHeaders() {
        given()
                .when()
                .get(url)
                .then()
                .log().all()
                .header("Content-type", containsString("application/json"));
    }

    public void validateKeys(String key) {
        given()
                .when()
                .get(url)
                .then()
                .log().all()
                .body("$", hasKey(key));
    }

    public void validateScaleRegex(String responseBody) {
        Matcher matcher = Pattern.compile("\"scale\":\\s*(?:[1-9]|[1-9]\\d{1,3}|10000)").matcher(responseBody);
        assertTrue(matcher.find());
    }
}
