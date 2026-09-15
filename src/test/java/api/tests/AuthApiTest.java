package api.tests;

import data.Constants;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.AuthSteps;
import auth.api.model.RegisterRequest;
import auth.api.model.AuthenticationResponse;
import util.Util;
import auth.api.model.AuthenticationRequest;
import auth.api.model.RefreshTokenRequest;


import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
@Epic("Authentication API")
@Feature("Authentication")
public class AuthApiTest {
    AuthSteps authSteps;
    AuthenticationResponse authResponse;
    RegisterRequest  req;

    @BeforeClass
    public void beforeClass() {
        authSteps = new AuthSteps();
    }

    @Test(priority = 1)
    @Story("Register Admin")
    public void registerAdmin() {
        req = Util.generateAdmin();
        Response res = authSteps.register(req);
        res.then().statusCode(SC_OK);
    }

    @Test(priority = 2)
    @Story("Authenticate")
    public void authenticate(){
        AuthenticationRequest request = new AuthenticationRequest()
                .email(req.getEmail())
                .password(req.getPassword());
        Response res = authSteps.authenticate(request);

        res.then().statusCode(SC_OK);
        authResponse = res.as(AuthenticationResponse.class);

        assertThat(authResponse.getRoles(),hasItems(Constants.ROLES));
    }
    @Story("Refresh Token")
    @Test(priority = 3)
    public void refreshToken(){
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest()
                .refreshToken(authResponse.getRefreshToken());
        Response res = authSteps.refreshToken(authResponse.getAccessToken(),
                refreshTokenRequest);
        res.then().statusCode(SC_OK);
        authResponse = res.as(AuthenticationResponse.class);
    }

}
