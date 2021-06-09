package stepDefinitions;

import helper.JsonUtils;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helper.jsonObjects.Client;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Login {

    private RequestSpecification requestSpecification = RestAssured.given();
    private Response response;
    private String commonUrl = "http://127.0.0.1:8000/";
    private JsonPath jsonPathEvaluator;
    private String specificUrl;
    public static Client userData = new Client();


    @Given("^I am an user in the auth/login endpoint$")
    public void iAmAnUserInTheAuthLoginEndpoint() {
        requestSpecification = requestSpecification
                .contentType("application/x-www-form-urlencoded");
    }

    @When("^I login with the username (.*) and the password (.*)$")
    public void iLoginWithTheUsernameAndThePassword(String username, String password) {
        specificUrl = commonUrl + "auth/login";
        response = requestSpecification
                .formParam("username", "test")
                .formParam("password", "1234")
                .post(specificUrl);
        jsonPathEvaluator = response.jsonPath();
        userData.setSessionToken(jsonPathEvaluator.get("access_token").toString());
        userData.setUsername(username);
        userData.setPassword(password);
    }

    @When("^I check my authentication credentials$")
    public void iCheckMyAuthenticationCredentials() {
        specificUrl = commonUrl + "auth/me";
        response = requestSpecification
                .auth()
                .oauth2(userData.getSessionToken())
                .get(specificUrl);
        jsonPathEvaluator = response.jsonPath();
    }

    @When("^I logout$")
    public void iLogout() {
        specificUrl = commonUrl + "auth/logout";
        response = requestSpecification
                .auth()
                .oauth2(userData.getSessionToken())
                .get(specificUrl);
        jsonPathEvaluator = response.jsonPath();
    }

    @Then("^I am able to login$")
    public void iAmAbleToLogin() {
        assertEquals(200, response.statusCode());
        assertEquals("bearer", jsonPathEvaluator.get("token_type").toString());
    }

    @Then("^I should be properly logged in$")
    public void iShouldBeProperlyLoggedIn() {
        assertEquals(200, response.statusCode());
        assertEquals(userData.getUsername(), jsonPathEvaluator.get("username").toString());
        assertNotNull(jsonPathEvaluator.get("id"));
        assertNotNull(jsonPathEvaluator.get("full_name"));
        assertNotNull(jsonPathEvaluator.get("email"));
    }

    @Then("^I should be properly logged out$")
    public void iShouldBeProperlyLoggedOut() {
        assertEquals(200, response.statusCode());
        specificUrl = commonUrl + "auth/me";
        response = requestSpecification
                .auth()
                .oauth2(userData.getSessionToken())
                .get(specificUrl);
        assertEquals(401, response.statusCode());
    }

}
