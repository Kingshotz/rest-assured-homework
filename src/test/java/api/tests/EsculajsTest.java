package api.tests;

import api.client.EsculajsApi;
import data.models.request.CreateUserRequest;
import data.models.response.AuthResponse;
import data.models.response.ProfileResponse;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Utils;

import static io.restassured.internal.common.assertion.AssertParameter.notNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class EsculajsTest {
    EsculajsApi esculajsApi;
    CreateUserRequest user;
    AuthResponse authResponse;
    @BeforeClass
    public void beforeClass() {
        esculajsApi = new EsculajsApi();
        user = Utils.generateUser();
    }

    @Test
    public void createUser() {
        esculajsApi
                .createUser(user)
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test(dependsOnMethods = "createUser")
    public void authUser() {
        Response response = esculajsApi
                .authUser(user.getEmail(), user.getPassword())
                .then()
                .statusCode(201)
                .extract()
                .response();
        authResponse = response.as(AuthResponse.class);
    }

    @Test(dependsOnMethods = "authUser")
    public void getAuthProfile() {
        Response res = esculajsApi
                .getAuthProfile(authResponse.getAccessToken())
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
        ProfileResponse profileResponse = res.as(ProfileResponse.class);

        assertThat(profileResponse.getName(), equalTo(user.getName()));
        assertThat(profileResponse.getEmail(), equalTo(user.getEmail()));
        assertThat(profileResponse.getAvatar(), equalTo(user.getAvatar()));
        assertThat(profileResponse.getRole(), equalTo(user.getRole()));

    }
}
