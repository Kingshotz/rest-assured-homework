package ge.tbc.testautomation;

import com.github.javafaker.Faker;
import ge.tbc.testautomation.data.Constants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ge.tbc.testautomation.data.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PetsApiTests {
    private RequestSpecification spec;
    Random random;
    JSONObject pet;
    JSONObject updatedPet;
    @BeforeClass
    public void beforeClass() {
        spec = new RequestSpecBuilder()
                .setBaseUri(Constants.PET_STORE_URI)
                .build();
        random = new Random();
        pet = generatePetJsonObject();
        updatedPet = new JSONObject(pet.toString());
    }

    @Test
    public void StoreOrderPostTest() {
        Map<String, Object> orderRequest = new HashMap<>();
        orderRequest.put(ID_KEY, random.nextInt(10));
        orderRequest.put(PET_ID_KEY, random.nextInt(10) );
        orderRequest.put(QUANTITY_KEY, random.nextInt(10) );
        orderRequest.put(SHIP_DATE_KEY, Instant.now().toString());
        orderRequest.put(STATUS_KEY, ORDER_STATUS_PLACED);
        orderRequest.put(COMPLETE_KEY, random.nextBoolean());

        given(spec)
                .basePath(Constants.PET_STORE_PATH)
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .contentType(ContentType.JSON)
                .body(orderRequest)
                .when()
                .post(ORDER_ENDPOINT)
                .then()
                .statusCode(200)
                .body(ID_KEY, anyOf(
                        equalTo(orderRequest.get(ID_KEY)),
                        equalTo(9223372036854775807L)
                ),
                PET_ID_KEY, equalTo(orderRequest.get(PET_ID_KEY)),
                QUANTITY_KEY, equalTo(orderRequest.get(QUANTITY_KEY)),
                SHIP_DATE_KEY, startsWith(orderRequest.get(SHIP_DATE_KEY).toString().substring(0, 19)),
                STATUS_KEY, equalTo(orderRequest.get(STATUS_KEY)),
                COMPLETE_KEY, equalTo(orderRequest.get(COMPLETE_KEY)));
    }

    @Test
    public void PetPostTest() {
        int petId = 2 + random.nextInt(10);

        given(spec)
                .basePath(Constants.PET_PATH)
                .accept(ContentType.JSON)
                .contentType(ContentType.URLENC)
                .formParam(NAME_KEY, PET_NAME_DOGGIE)
                .formParam(STATUS_KEY, PET_STATUS_AVAILABLE)
                .when()
                .post(PET_BY_ID_ENDPOINT, petId)
                .then()
                .log().all()
                .statusCode(200)
                .body(CODE_KEY, equalTo(200))
                .body(TYPE_KEY, notNullValue())
                .body(MESSAGE_KEY, notNullValue());
    }

    @Test
    public void NotFoundPetPostTest() {
        given(spec)
                .basePath(Constants.PET_PATH)
                .accept(ContentType.JSON)
                .contentType(ContentType.URLENC)
                .formParam(NAME_KEY, PET_NAME_DOGGIE)
                .formParam(STATUS_KEY, PET_STATUS_AVAILABLE)
                .when()
                .post(PET_BY_ID_ENDPOINT, 2000)
                .then()
                .statusCode(404)
                .body(CODE_KEY, equalTo(404))
                .body(TYPE_KEY, notNullValue())
                .body(MESSAGE_KEY, notNullValue());

    }

    @Test
    public void UserLoginTest() {
        Response response = given(spec)
                .basePath(Constants.PET_USER_PATH)
                .accept(ContentType.JSON)
                .queryParam(USERNAME_PARAM, LOGIN_USERNAME)
                .queryParam(PASSWORD_PARAM, LOGIN_PASSWORD)
                .when()
                .get(LOGIN_ENDPOINT)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        String message = response.jsonPath().getString(MESSAGE_KEY);
        Matcher matcher = Pattern.compile(THIRTEEN_DIGIT_NUMBER_REGEX).matcher(message);

        assertThat(SESSION_NUMBER_NOT_FOUND_MESSAGE + message,
                matcher.find(), is(true));

        String sessionNumber = matcher.group();
        System.out.println(EXTRACTED_SESSION_NUMBER_MESSAGE + sessionNumber);
    }

    @Test
    public void petPost() {
        given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(pet.toString())
                .when()
                .post(Constants.PET_PATH)
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test(dependsOnMethods = "petPost")
    public void petGet() {
        JsonPath jsonPath = given()
                .spec(spec)
                .basePath(Constants.PET_PATH)
                .contentType(ContentType.JSON)
                .queryParam(STATUS_KEY, pet.get(STATUS_KEY))
                .when()
                .get(FIND_BY_STATUS_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().jsonPath();

        Map<String, Object> petFound = jsonPath
                .param(ID_KEY, pet.get(ID_KEY))
                .getMap(FIND_BY_ID_QUERY);

        assertThat(PET_NOT_FOUND_IN_STATUS_MESSAGE, petFound, is(notNullValue()));
        assertThat(petFound.get(ID_KEY), equalTo(pet.get(ID_KEY)));
        assertThat(petFound.get(NAME_KEY), equalTo(pet.get(NAME_KEY)));
        assertThat(petFound.get(STATUS_KEY), equalTo(pet.get(STATUS_KEY)));
    }

    @Test(dependsOnMethods = "petGet")
    public void updatePetTest() {

        updatedPet.put(NAME_KEY, new Faker().animal().name());
        updatedPet.put(STATUS_KEY, PET_STATUS_SOLD);

        given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(updatedPet.toString())
                .when()
                .put(Constants.PET_PATH)
                .then()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "updatePetTest")
    public void checkUpdatedPetTest() {
        JsonPath jsonPath = given()
                .spec(spec)
                .basePath(Constants.PET_PATH)
                .contentType(ContentType.JSON)
                .when()
                .get(ID_ENDPOINT, pet.get(ID_KEY))
                .then()
                .statusCode(200)
                .extract().jsonPath();

        assertThat(jsonPath.get(NAME_KEY), equalTo(updatedPet.get(NAME_KEY)));
        assertThat(jsonPath.get(STATUS_KEY), equalTo(updatedPet.get(STATUS_KEY)));

    }

    private JSONObject generatePetJsonObject(){
        Faker faker = new Faker();

        JSONObject category = new JSONObject();
        category.put(ID_KEY, faker.number().numberBetween(1,100));
        category.put(NAME_KEY, faker.animal().name());

        JSONArray photoUrls = new JSONArray();
        for(int i=0;i<faker.number().numberBetween(1,10);i++){
            photoUrls.put(faker.internet().url());
        }

        JSONArray tags = new JSONArray();
        for(int i=0;i<faker.number().numberBetween(1, 10);i++){
            JSONObject tag = new JSONObject();
            tag.put(ID_KEY, faker.number().numberBetween(1,100));
            tag.put(NAME_KEY, faker.animal().name());

            tags.put(tag);
        }

        JSONObject pet = new JSONObject();
        pet.put(ID_KEY, faker.number().numberBetween(1,100));
        pet.put(CATEGORY_KEY, category);
        pet.put(NAME_KEY, faker.animal().name());
        pet.put(PHOTO_URLS_KEY, photoUrls);
        pet.put(TAGS_KEY, tags);
        pet.put(STATUS_KEY, faker.options().option(PET_STATUS_AVAILABLE, PET_STATUS_PENDING, PET_STATUS_SOLD));
        return pet;
    }
}
