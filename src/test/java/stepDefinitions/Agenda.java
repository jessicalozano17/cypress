package stepDefinitions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helper.jsonObjects.Client;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Agenda {

    private RequestSpecification requestSpecification = RestAssured.given().relaxedHTTPSValidation();
    private Response response;
    private String commonUrl = "http://127.0.0.1:8000/";
    private JsonPath jsonPathEvaluator;
    private JSONObject requestBody = new JSONObject();
    private String specificUrl;
    private Client userData = Login.userData;


    @When("^I store the firstName (.*), the lastName (.*), the email (.*), the phone (.*) and the mobile (.*) as a new contact$")
    public void iStoreTheDataAsNewContact(String firstname, String lastname, String email, String phone, String mobile) {
        Random rand = new Random();
        specificUrl = commonUrl + "api/v1/contacts/";
        userData.setFirstName(firstname + rand.nextInt());
        userData.setLastName(lastname + rand.nextInt());
        userData.setPhone(phone);
        userData.setEmail(email);
        userData.setMobile(mobile);
        requestBody.put("firstName", userData.getFirstName());
        requestBody.put("lastName", userData.getLastName());
        requestBody.put("email", email);
        requestBody.put("phone", phone);
        requestBody.put("mobile", mobile);
        response = requestSpecification
                .contentType("application/json")
                .auth()
                .oauth2(userData.getSessionToken())
                .body(requestBody)
                .post(specificUrl);
        jsonPathEvaluator = response.jsonPath();
        userData.setId(jsonPathEvaluator.get("id"));
        userData.setFullName(jsonPathEvaluator.get("fullName").toString());
    }

    @When("^I try to store a new contact with the same data$")
    public void iTryToStoreANewContactWithTheSameData () {
        specificUrl = commonUrl + "api/v1/contacts/";
        response = requestSpecification
                .contentType("application/json")
                .auth()
                .oauth2(userData.getSessionToken())
                .body(requestBody)
                .post(specificUrl);
        jsonPathEvaluator = response.jsonPath();
    }

    @When("^I retrieve all the previously created contacts$")
    public void iRetrieveAllThePreviouslyCreatedContacts () {
        specificUrl = commonUrl + "api/v1/contacts/";
        response = requestSpecification
                .contentType("application/json")
                .auth()
                .oauth2(userData.getSessionToken())
                .get(specificUrl);
        jsonPathEvaluator = response.jsonPath();
    }

    @When("^I retrieve the id of the previously created contact$")
    public void iRetrieveTheIdOfThePreviouslyCreatedContact () {
        specificUrl = commonUrl + "api/v1/contacts/" + userData.getId();
        response = requestSpecification
                .contentType("application/json")
                .auth()
                .oauth2(userData.getSessionToken())
                .get(specificUrl);
        jsonPathEvaluator = response.jsonPath();
    }

    @When("^I modify the field (.*) with the data (.*)$")
    public void iModifyTheFieldWithTheData (String field, String data) {
        specificUrl = commonUrl + "api/v1/contacts/" + userData.getId();
        userData.setAttribute(field, data);
        requestBody.put(field, data);
        response = requestSpecification
                .contentType("application/json")
                .auth()
                .oauth2(userData.getSessionToken())
                .body(requestBody)
                .patch(specificUrl);
        jsonPathEvaluator = response.jsonPath();
    }

    @Then("^The new contact is stored with an unique id$")
    public void theNewContactIsStoredWithAnUniqueId() {
        assertEquals(201, response.statusCode());
        assertEquals(userData.getFirstName(), jsonPathEvaluator.get("firstName").toString());
        assertEquals(userData.getLastName(), jsonPathEvaluator.get("lastName").toString());
        assertEquals(userData.getEmail(), jsonPathEvaluator.get("email").toString());
        assertEquals(userData.getPhone(), jsonPathEvaluator.get("phone").toString());
        assertEquals(userData.getMobile(), jsonPathEvaluator.get("mobile").toString());
        assertNotNull(jsonPathEvaluator.get("id").toString());
        assertEquals(userData.getFirstName() + " " + userData.getLastName(), jsonPathEvaluator.get("fullName").toString());
    }

    @Then("^The new contact is not stored$")
    public void theNewContactIsNotStored() {
        assertEquals(400, response.statusCode());
        assertEquals("Conflict, contact for " + userData.getFirstName() + " " + userData.getLastName() + " already exists", jsonPathEvaluator.get("detail").toString());
    }

    @Then("^I can verify that the contacts information is correct$")
    public void iCanVerifyThatTheContactsInformationIsCorrect() {
        assertEquals(200, response.statusCode());
        for (int i = 0 ; i < jsonPathEvaluator.getList("").size() ; i++) {
            assertNotNull(jsonPathEvaluator.get("[" + i + "].firstName"));
            assertNotNull(jsonPathEvaluator.get("[" + i + "].lastName"));
            assertNotNull(jsonPathEvaluator.get("[" + i + "].email"));
            assertNotNull(jsonPathEvaluator.get("[" + i + "].phone"));
            assertNotNull(jsonPathEvaluator.get("[" + i + "].mobile"));
            assertNotNull(jsonPathEvaluator.get("[" + i + "].id"));
            assertNotNull(jsonPathEvaluator.get("[" + i + "].fullName"));
            assertEquals(i + 1, Integer.parseInt(jsonPathEvaluator.get("[" + i + "].id").toString()));
        }
    }

    @Then("^I can verify that the contact information is correct for the specific id$")
    public void iCanVerifyThatTheContactInformationIsCorrectForTheSpecificId() {
        assertEquals(200, response.statusCode());
        assertEquals(userData.getFirstName(), jsonPathEvaluator.get("firstName").toString());
        assertEquals(userData.getLastName(), jsonPathEvaluator.get("lastName").toString());
        assertEquals(userData.getEmail(), jsonPathEvaluator.get("email").toString());
        assertEquals(userData.getPhone(), jsonPathEvaluator.get("phone").toString());
        assertEquals(userData.getMobile(), jsonPathEvaluator.get("mobile").toString());
        assertEquals(userData.getId(), Integer.parseInt(jsonPathEvaluator.get("id").toString()));
        assertEquals(userData.getFullName(), jsonPathEvaluator.get("fullName").toString());
    }

    @Then("^the agenda is up to date with the changes$")
    public void theAgendaIsUpToDateWithTheChanges() {
        assertEquals(200, response.statusCode());
        assertEquals(userData.getFirstName(), jsonPathEvaluator.get("firstName").toString());
        assertEquals(userData.getLastName(), jsonPathEvaluator.get("lastName").toString());
        assertEquals(userData.getEmail(), jsonPathEvaluator.get("email").toString());
        assertEquals(userData.getPhone(), jsonPathEvaluator.get("phone").toString());
        assertEquals(userData.getMobile(), jsonPathEvaluator.get("mobile").toString());
        assertEquals(userData.getId(), Integer.parseInt(jsonPathEvaluator.get("id").toString()));
    }

}
