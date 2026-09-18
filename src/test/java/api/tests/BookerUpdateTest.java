package api.tests;

import api.client.BookerApi;
import com.github.javafaker.Faker;
import data.models.request.booker.AuthRequest;
import data.models.request.booker.BookingRequest;
import data.models.response.booker.BookingResponse;
import data.models.response.booker.CreateBookingResponse;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Utils;

import static data.Constants.BOOKER_PASSWORD;
import static data.Constants.BOOKER_USERNAME;

@Feature("Restful-Booker Booking")
public class BookerUpdateTest {

    private static final Faker faker = new Faker();

    BookerApi bookerApi;
    String token;
    int bookingId;

    @BeforeClass
    public void beforeClass() {
        bookerApi = new BookerApi();
        token = bookerApi.createToken(new AuthRequest(BOOKER_USERNAME, BOOKER_PASSWORD));

        BookingRequest initialBooking = BookingRequest.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .totalprice(faker.number().numberBetween(100, 500))
                .depositpaid(faker.bool().bool())
                .bookingdates(Utils.generateBookingDates())
                .additionalneeds(faker.lorem().sentence())
                .build();

        bookingId = bookerApi.createBooking(initialBooking)
                .then()
                .statusCode(200)
                .extract()
                .as(CreateBookingResponse.class)
                .getBookingid();
    }

    @Test
    @Story("Update booking returns the updated fields and hides ignored ones")
    public void updateBookingReturnsUpdatedFields() {
        BookingRequest updateRequest = BookingRequest.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .totalprice(faker.number().numberBetween(500, 1000))
                .depositpaid(faker.bool().bool())
                .bookingdates(Utils.generateBookingDates())
                .additionalneeds(faker.lorem().sentence())
                .saleprice(faker.number().randomDouble(2, 100, 500))
                .passportNo(String.valueOf(faker.number().numberBetween(1000000, 9999999)))
                .build();

        BookingResponse updated = bookerApi.updateBooking(bookingId, updateRequest, token)
                .then()
                .statusCode(200)
                .extract()
                .as(BookingResponse.class);

        Assert.assertEquals(updated.getFirstname(), updateRequest.getFirstname());
        Assert.assertEquals(updated.getLastname(), updateRequest.getLastname());
        Assert.assertEquals(updated.getTotalprice(), updateRequest.getTotalprice());
        Assert.assertEquals(updated.getDepositpaid(), updateRequest.getDepositpaid());
        Assert.assertEquals(updated.getBookingdates(), updateRequest.getBookingdates());
        Assert.assertEquals(updated.getAdditionalneeds(), updateRequest.getAdditionalneeds());
    }
}
