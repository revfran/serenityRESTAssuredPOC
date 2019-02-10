package steps;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import static org.hamcrest.Matchers.is;

public class CountriesSearchSteps {
    private String ISO_CODE_SEARCH = "http://services.groupkt.com/country/get/iso2code/";
    private Response response;

    @Step("I try to search the country by '{0}' code")
    public void searchCountryByCode(String code){
        response = SerenityRest.when().get(ISO_CODE_SEARCH + code);
    }

    @Step("I check if search returns a 200 code")
    public void searchIsExecutedSuccesfully(){
        response.then().statusCode(200);
    }

    @Step("I check that response contains '{0}' country")
    public void iShouldFindCountry(String country){
        response.then().body("RestResponse.result.name", is(country));
    }
}
