package api.client;

import data.models.request.booker.AuthRequest;
import data.models.request.booker.BookingRequest;
import data.models.response.booker.AuthResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static data.Constants.BOOKER_AUTH_PATH;
import static data.Constants.BOOKER_BOOKING_PATH;
import static data.Constants.BOOKER_URI;
import static io.restassured.RestAssured.given;

public class BookerApi extends BaseApi {

    private final RequestSpecification reqSpec = specFor(BOOKER_URI);

    public Response authenticate(AuthRequest request) {
        return given()
                .spec(reqSpec)
                .body(request)
                .when()
                .post(BOOKER_AUTH_PATH);
    }

    public String createToken(AuthRequest request) {
        return authenticate(request)
                .then()
                .statusCode(200)
                .extract()
                .as(AuthResponse.class)
                .getToken();
    }

    public Response createBooking(BookingRequest request) {
        return given()
                .spec(reqSpec)
                .body(request)
                .when()
                .post(BOOKER_BOOKING_PATH);
    }

    public Response getBooking(int id) {
        return given()
                .spec(reqSpec)
                .when()
                .get(BOOKER_BOOKING_PATH + "/{id}", id);
    }

    public Response updateBooking(int id, BookingRequest request, String token) {
        return given()
                .spec(reqSpec)
                .cookie("token", token)
                .body(request)
                .when()
                .put(BOOKER_BOOKING_PATH + "/{id}", id);
    }
}
