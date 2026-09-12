package api.tests;

import api.client.PetstoreApi;
import data.models.request.petstore.OrderRequest;
import data.models.response.petstore.OrderResponse;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

@Feature("Petstore Store")
public class PetstoreOrderTest {

    PetstoreApi petstoreApi = new PetstoreApi();

    @Test
    @Story("Create order accepts a reordered request body and returns the matching order")
    public void createOrder() {
        OrderRequest orderRequest = OrderRequest.builder()
                .status("placed")
                .complete(false)
                .petId(198772L)
                .quantity(3)
                .shipDate(LocalDate.now().plusDays(5))
                .id(1001L)
                .build();

        OrderResponse createdOrder = petstoreApi.createOrder(orderRequest)
                .then()
                .statusCode(200)
                .extract()
                .as(OrderResponse.class);

        Assert.assertEquals(createdOrder.getPetId(), orderRequest.getPetId());
        Assert.assertEquals(createdOrder.getQuantity(), orderRequest.getQuantity());
        Assert.assertEquals(createdOrder.getStatus(), orderRequest.getStatus());
        Assert.assertEquals(createdOrder.getComplete(), orderRequest.getComplete());
        Assert.assertEquals(createdOrder.getShipDate(), orderRequest.getShipDate());
        Assert.assertNotNull(createdOrder.getId());
    }
}
