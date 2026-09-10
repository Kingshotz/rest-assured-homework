package api.client;

import data.Constants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BookStoreApi {
    RequestSpecification REQ_SPEC = new RequestSpecBuilder()
            .setBaseUri(Constants.BOOK_STORE_URI)
            .setBasePath(Constants.BOOK_STORE_PATH)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build();
    public Response getAllBooks(){
        return given()
                .spec(REQ_SPEC)
                .when()
                .get();
    }
}
