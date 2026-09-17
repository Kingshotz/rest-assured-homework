package api.client;

import data.models.request.CreateUserRequest;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class EsculajsApi extends BaseApi{
    public Response createUser(CreateUserRequest createUserRequest){
        return given()
                .spec(REQ_SPEC)
                .body(createUserRequest)
                .when()
                .post("/users");
    }
    public Response authUser(String email, String password){
        return given()
                .spec(REQ_SPEC)
                .body(Map.of("email", email, "password", password))
                .when()
                .post("/auth/login");
    }
    public Response getAuthProfile(String JWT){
        return given()
                .spec(REQ_SPEC)
                .auth().oauth2(JWT)
                .when()
                .get("/auth/profile");
    }
}
