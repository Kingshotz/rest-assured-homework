package steps;

import io.restassured.response.Response;

import static config.BaseConfiguration.petStoreApi;
import pet.store.v3.model.Order;

public class PetStoreSteps {
    public Response placeOrder(Order order) {
        return petStoreApi()
                .store()
                .placeOrder()
                .body(order)
                .execute(response -> response);
    }
}
