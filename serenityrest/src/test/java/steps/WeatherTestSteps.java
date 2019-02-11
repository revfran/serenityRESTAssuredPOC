package steps;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import static org.hamcrest.core.Is.is;

public class WeatherTestSteps {


    private String _apiKey;

    public void set_apiKey(String apiKey) {
        this._apiKey = apiKey;
    }

    private String _baseUrl;

    public void set_baseUrl(String _baseUrl) {
        this._baseUrl = _baseUrl;
    }

    private Response response;

    private String responsePath;

    /**
     * Returns the complete ending of URL string based on apiKey being null or not
     *
     * @return ending of URL or empty string
     */
    private String apiKeyUrlString() {
        if (_apiKey != null)
            return "&APPID=" + this._apiKey;
        else return "";
    }


    @Step("I try to search the city by '{0}' Name")
    public void whenISearchACityByName(String name) {
        response = SerenityRest.when().get(this._baseUrl + "?q=" + name + apiKeyUrlString());
    }

    @Step("I try to search the city by '{0}' id")
    public void whenISearchACityByID(String id) {
        response = SerenityRest.when().get(this._baseUrl + "?id=" + id + apiKeyUrlString());
    }

    @Step("I try to search by coordinates (lon '{0}', lat '{1})")
    public void whenISearchByCoordinates(int lon, int lat) {
        response = SerenityRest.when().get(this._baseUrl + "?lat=" + lat + "&lon=" + lon + apiKeyUrlString());
    }

    @Step("I try to search by zip code '{0}' id")
    public void whenISearchByZipCode(int code) {
        response = SerenityRest.when().get(this._baseUrl + "?zip=" + code + apiKeyUrlString());
    }

    @Step("I try to search by zip code '{0}' id")
    public void whenISearchByZipCode(int code, String countryCode) {
        response = SerenityRest.when().get(this._baseUrl + "?zip=" + code + "," + countryCode + apiKeyUrlString());
    }

    @Step("I check if it returns a {0} code")
    public void CheckCode(int code) {
        response.then().statusCode(code);
    }

    @Step("I set {0} path in response")
    public WeatherTestSteps setResponsePath(String responsePath) {
        this.responsePath = responsePath;
        return this;
    }

    @Step("I check if it contains a {0} code")
    public void CheckMatches(String content) {
        response.then().body(this.responsePath, is(content));
    }

    @Step("I check if it contains a {0} code")
    public void CheckMatches(int content) {
        response.then().body(this.responsePath, is(content));
    }

}
