package api.client;

import data.models.request.petstore.OrderRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static data.Constants.PETSTORE_ORDER_PATH;
import static data.Constants.PETSTORE_URI;
import static io.restassured.RestAssured.given;

public class PetstoreApi extends BaseApi {

    private final RequestSpecification reqSpec = specFor(PETSTORE_URI);

    public Response createOrder(OrderRequest request) {
        return given()
                .spec(reqSpec)
                .body(request)
                .when()
                .post(PETSTORE_ORDER_PATH);
    }
}
