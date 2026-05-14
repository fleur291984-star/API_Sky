import enums.TitleNaming;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static enums.TitleNaming.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.StringContains.containsString;

public class CurrencyTest {
    @Test(dataProvider = "moneyName")
    public void checkCurrency(TitleNaming currency, Integer grow, Integer scale) {
        given()
                .log().all()
        .when()
                .get("https://kurs.onliner.by/sdapi/kurs/api/bestrate?currency="+currency.name()+"&type=nbrb")
        .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/rate_schema.json"))
                .header("Content-type", containsString("application/json"))
                .body("grow", equalTo(grow))
                .body("scale", equalTo(scale));
    }

    @DataProvider(name = "moneyName")
    public Object[][] TitleNaming() {
        return new Object[][]{
                {RUB, 1, 100},
                {EUR, -1, 1},
                {USD, -1, 1}
        };
    }
}
