package ge.tbc.testautomation;

import ge.tbc.testautomation.data.Constants;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PetsApiTests {
    private RequestSpecification spec;
    Random random;

    @BeforeClass
    public void beforeClass() {
        spec = new RequestSpecBuilder()
                .setBaseUri(Constants.PET_STORE_URI)
                .build();
        random = new Random();
    }

    @Test
    public void StoreOrderPostTest() {
        Map<String, Object> orderRequest = new HashMap<>();
        orderRequest.put("id", random.nextInt(10));
        orderRequest.put("petId", random.nextInt(10) );
        orderRequest.put("quantity", random.nextInt(10) );
        orderRequest.put("shipDate", Instant.now().toString());
        orderRequest.put("status", "placed");
        orderRequest.put("complete", random.nextBoolean());

        given(spec)
                .basePath(Constants.PET_STORE_PATH)
                .header("accept","application/json")
                .contentType(ContentType.JSON)
                .body(orderRequest)
                .when()
                .post("/order")
                .then()
                .statusCode(200)
                .body("id", anyOf(
                        equalTo(orderRequest.get("id")),
                        equalTo(9223372036854775807L)
                ),
                "petId",equalTo(orderRequest.get("petId")),
                "quantity", equalTo(orderRequest.get("quantity")),
                "shipDate", startsWith(orderRequest.get("shipDate").toString().substring(0, 19)),
                "status", equalTo(orderRequest.get("status")),
                "complete", equalTo(orderRequest.get("complete")));
    }

    @Test
    public void PetPostTest() {
        int petId = 2 + random.nextInt(10);

        given(spec)
                .basePath(Constants.PET_PATH)
                .accept(ContentType.JSON)
                .contentType(ContentType.URLENC)
                .formParam("name", "doggie")
                .formParam("status", "available")
                .when()
                .post("/{petId}", petId)
                .then()
                .log().all()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("type", notNullValue())
                .body("message", notNullValue());
    }

    @Test
    public void NotFoundPetPostTest() {
        given(spec)
                .basePath(Constants.PET_PATH)
                .accept(ContentType.JSON)
                .contentType(ContentType.URLENC)
                .formParam("name","doggie")
                .formParam("status", "available")
                .when()
                .post("/{petId}", 2000)
                .then()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("type", notNullValue())
                .body("message", notNullValue());

    }

    @Test
    public void UserLoginTest() {
        Response response = given(spec)
                .basePath(Constants.PET_USER_PATH)
                .accept(ContentType.JSON)
                .queryParam("username", "username")
                .queryParam("password", "password")
                .when()
                .get("/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        String message = response.jsonPath().getString("message");
        Matcher matcher = Pattern.compile("\\d{13}").matcher(message);

        assertThat("Message did not contain a 10-digit number: " + message,
                matcher.find(), is(true));

        String sessionNumber = matcher.group();
        System.out.println("Extracted session number: " + sessionNumber);
    }
}
