package tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.WeatherTestSteps;

@RunWith(SerenityRunner.class)
public class WeatherTest {

    @Steps(shared = true)
    WeatherTestSteps weatherTestSteps;


    @Before
    public void setup() {
        weatherTestSteps = new WeatherTestSteps();

        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        ;
        String weatherApiKey = environmentVariables.getProperty("weather.api.key");
        String weatherBaseUrl = environmentVariables.getProperty("weather.base.url");
        System.out.printf("Start with apiKey '{%s}', baseUrl '{%s}'", weatherApiKey, weatherBaseUrl);
        weatherTestSteps.set_apiKey(weatherApiKey);
        weatherTestSteps.set_baseUrl(weatherBaseUrl);
    }

    @After
    public void teardown() {
        weatherTestSteps = null;
        // Sleep due to limitations of free API key
        System.out.printf("Forced sleep for next test...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.err.println("Exception in sleep call");
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchByCityName() {
        weatherTestSteps.whenISearchACityByName("London");
        weatherTestSteps.CheckCode(200);
    }

    @Test
    public void testSearchByCityNameWithNoKey() {
        weatherTestSteps.set_apiKey(null);

        weatherTestSteps.whenISearchACityByName("London");
        weatherTestSteps.CheckCode(401);
    }

    @Test
    public void testSearchByCityNameBody() {
        weatherTestSteps.whenISearchACityByName("London");
        weatherTestSteps.CheckCode(200);
        weatherTestSteps.setResponsePath("name").CheckMatches("London");
    }

    @Test
    public void testSearchByCityIdBody() {
        weatherTestSteps.whenISearchACityByID("2643743");
        weatherTestSteps.CheckCode(200);
        weatherTestSteps.setResponsePath("id").CheckMatches(2643743);
    }

    @Test
    public void testSearchByCoordinatesBody() {
        weatherTestSteps.whenISearchByCoordinates(139, 35);
        weatherTestSteps.CheckCode(200);
        weatherTestSteps.setResponsePath("coord.lon").CheckMatches(139);
        weatherTestSteps.setResponsePath("coord.lat").CheckMatches(35);
    }

    @Test
    public void testSearchByZipCodeBody() {
        weatherTestSteps.whenISearchByZipCode(94040);
        weatherTestSteps.CheckCode(200);
        weatherTestSteps.setResponsePath("sys.country").CheckMatches("US");
    }

    @Test
    public void testSearchByZipCodeCountryBody() {
        weatherTestSteps.whenISearchByZipCode(50003, "es");
        weatherTestSteps.CheckCode(200);
        weatherTestSteps.setResponsePath("sys.country").CheckMatches("ES");
    }
}
