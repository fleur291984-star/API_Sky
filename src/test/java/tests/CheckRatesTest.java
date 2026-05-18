package tests;

import enums.TitleNaming;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.RateStep;
import validators.RateValidator;

import static enums.TitleNaming.*;

public class CheckRatesTest {
    private final RateStep steps = new RateStep();
    private final RateValidator validator = new RateValidator();

    String response = steps.getRatesResponse();

    @Test(dataProvider = "moneyName")
    public void checkRates(TitleNaming currency) {
        validator.validateSchema(200);
        validator.validateHeaders();
        validator.validateKeys("delta");
        validator.validateScaleRegex(response);
    }

    @DataProvider(name = "moneyName")
    public Object[][] TitleNaming() {
        return new Object[][]{
                {RUB},
                {EUR},
                {USD}
        };
    }
}
