package config;
import data.Constants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import static io.restassured.RestAssured.config;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;

public class BaseConfiguration {
    public static pet.store.v3.invoker.ApiClient petStoreApi() {
        return pet.store.v3.invoker.ApiClient.api(
                pet.store.v3.invoker.ApiClient.Config.apiConfig()
                .reqSpecSupplier(() -> new RequestSpecBuilder()
                        .log(LogDetail.ALL)
                        .setConfig(config()
                                .objectMapperConfig(objectMapperConfig()
                                        .defaultObjectMapper(pet.store.v3.invoker.JacksonObjectMapper.jackson())))
                        .addFilter(new ErrorLoggingFilter()) // log only in case of error
                        .addFilter(new RequestLoggingFilter()) // log all requests
                        .addFilter(new ResponseLoggingFilter()) // log all responses
                        .setBaseUri(Constants.PET_STORE_BASE_URL)));
    }
    public static auth.api.invoker.ApiClient authApi(){
        return auth.api.invoker.ApiClient.api(
                auth.api.invoker.ApiClient.Config.apiConfig()
                        .reqSpecSupplier(() -> new RequestSpecBuilder()
                                .log(LogDetail.ALL)
                                .setConfig(config()
                                        .objectMapperConfig(objectMapperConfig()
                                                .defaultObjectMapper(auth.api.invoker.JacksonObjectMapper.jackson())))
                                .addFilter(new ErrorLoggingFilter()) // log only in case of error
                                .addFilter(new RequestLoggingFilter()) // log all requests
                                .addFilter(new ResponseLoggingFilter()) // log all responses
                                .setBaseUri(Constants.AUTH_BASE_URL))
        );
    }
    public static auth.api.invoker.ApiClient authApiAuthorized(String accessToken){
        return auth.api.invoker.ApiClient.api(
                auth.api.invoker.ApiClient.Config.apiConfig()
                        .reqSpecSupplier(() -> new RequestSpecBuilder()
                                .log(LogDetail.ALL)
                                .setConfig(config()
                                        .objectMapperConfig(objectMapperConfig()
                                                .defaultObjectMapper(auth.api.invoker.JacksonObjectMapper.jackson())))
                                .addHeader("Authorization", "Bearer " + accessToken)
                                .addFilter(new ErrorLoggingFilter()) // log only in case of error
                                .addFilter(new RequestLoggingFilter()) // log all requests
                                .addFilter(new ResponseLoggingFilter()) // log all responses
                                .setBaseUri(Constants.AUTH_BASE_URL))
        );
    }

}
