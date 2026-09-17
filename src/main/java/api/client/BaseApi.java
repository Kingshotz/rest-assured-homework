package api.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static data.Constants.*;

public class BaseApi {
    public static final RequestSpecification REQ_SPEC = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setBasePath(BASE_PATH)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build();
}
