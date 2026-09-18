package api.client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static data.Constants.SWAPI_PLANETS_PATH;
import static data.Constants.SWAPI_URI;
import static io.restassured.RestAssured.given;

public class SwapiApi extends BaseApi {

    private final RequestSpecification reqSpec = specFor(SWAPI_URI);

    public Response getPlanets() {
        return given()
                .spec(reqSpec)
                .queryParam("format", "json")
                .when()
                .get(SWAPI_PLANETS_PATH);
    }

    public Response getPlanetByUrl(String url) {
        return given()
                .accept("application/json")
                .when()
                .get(url);
    }
}
