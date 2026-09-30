package steps;
import io.restassured.response.Response;
import static config.BaseConfiguration.authApi;
import static config.BaseConfiguration.authApiAuthorized;

import auth.api.model.RegisterRequest;
import auth.api.model.AuthenticationRequest;
import auth.api.model.RefreshTokenRequest;

public class AuthSteps {

    public Response register(RegisterRequest registerRequest){
        return authApi()
                .authentication()
                .register()
                .body(registerRequest)
                .execute(response -> response);
    }
    public Response authenticate(AuthenticationRequest authenticationRequest){
        return authApi()
                .authentication()
                .authenticate()
                .body(authenticationRequest)
                .execute(response -> response);
    }
    public Response refreshToken(String token , RefreshTokenRequest refreshTokenRequest){
        return authApiAuthorized(token)
                .authentication()
                .refreshToken()
                .body(refreshTokenRequest)
                .execute(response -> response);
    }
    public Response authorize(String token){
        return authApiAuthorized(token)
                .authorization()
                .sayHelloWithRoleAdminAndReadAuthority()
                .execute(response -> response);
    }


}
