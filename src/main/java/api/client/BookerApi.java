package api.client;

import data.Constants;
import data.models.request.AuthenticateRequest;
import data.models.request.CreateBookingRequest;
import data.models.request.PartialUpdateRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BookerApi {
    private String token;

    private final RequestSpecification REQ_SPEC = new RequestSpecBuilder()
            .setBaseUri(Constants.BOOKER_URI)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build();

    public Response authenticate(AuthenticateRequest auth) {
        return given()
                .spec(REQ_SPEC)
                .body(auth)
                .when()
                .post(Constants.BOOKER_AUTH_PATH);
    }

    public String getToken() {
        if (token == null) {
            AuthenticateRequest auth = new AuthenticateRequest();
            auth.setUsername(Constants.USERNAME);
            auth.setPassword(Constants.PASSWORD);

            token = authenticate(auth)
                    .then()
                    .statusCode(200)
                    .extract()
                    .path("token");
        }
        return token;
    }

    public Response createBooking(CreateBookingRequest createBookingRequest) {
        return given()
                .spec(REQ_SPEC)
                .body(createBookingRequest)
                .when()
                .post(Constants.BOOKER_CREATE_PATH);
    }

    public Response getBooking(int id) {
        return given()
                .spec(REQ_SPEC)
                .when()
                .get(Constants.BOOKER_CREATE_PATH + "/{id}", id);
    }

    public Response partialUpdateBooking(PartialUpdateRequest partialUpdateRequest, int id) {
        return given()
                .spec(REQ_SPEC)
                .cookie("token", getToken())
                .body(partialUpdateRequest)
                .when()
                .patch(Constants.BOOKER_CREATE_PATH + "/{id}", id);
    }

    public Response deleteBooking(int id) {
        return given()
                .spec(REQ_SPEC)
                .cookie("token", getToken())
                .when()
                .delete(Constants.BOOKER_CREATE_PATH + "/{id}", id);
    }
}
