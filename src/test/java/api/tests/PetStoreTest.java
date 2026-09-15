package api.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pet.store.v3.model.Order;
import steps.PetStoreSteps;
import util.Util;
import static org.apache.http.HttpStatus.SC_OK;
import static org.testng.AssertJUnit.assertEquals;
@Epic("Pet API")
@Feature("Pet Store")
public class PetStoreTest {
    private PetStoreSteps petStoreSteps;
    @BeforeClass
    public void setUp() {
        petStoreSteps = new PetStoreSteps();
    }

    @Test
    @Story("Place Order")
    public void placeOrder() {
        Order order = Util.generateOrder();
        Response res = petStoreSteps.placeOrder(order);
        res.then().statusCode(SC_OK);
        Order responseOrder = res.as(Order.class);
        assertEquals(order,responseOrder);
    }
}
