package api.tests;

import api.client.BookerApi;
import data.models.request.AuthenticateRequest;
import data.models.request.CreateBookingRequest;
import data.models.request.PartialUpdateRequest;
import data.models.response.Booking;
import data.models.response.CreateBookingResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Utils;

import static org.hamcrest.Matchers.notNullValue;

public class BookerTest {
    BookerApi bookersApi;
    int bookingId;
    CreateBookingRequest createdBooking;

    @BeforeClass
    public void beforeClass() {
        bookersApi = new BookerApi();
    }

    @Test(priority = 1)
    public void Authorization() {
        AuthenticateRequest auth = Utils.generateAuthReq();

        bookersApi.authenticate(auth)
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test(priority = 2)
    public void createBooking() {
        createdBooking = Utils.generateBookingReq();

        CreateBookingResponse created = bookersApi.createBooking(createdBooking)
                .then()
                .statusCode(200)
                .body("bookingid", notNullValue())
                .extract()
                .as(CreateBookingResponse.class);

        bookingId = created.getBookingid();
        Assert.assertEquals(created.getBooking(), toBooking(createdBooking),
                "POST response body does not echo the created booking");

        Booking fetched = bookersApi.getBooking(bookingId)
                .then()
                .statusCode(200)
                .extract()
                .as(Booking.class);

        Assert.assertEquals(fetched, toBooking(createdBooking),
                "GET booking does not match what was created");
    }

    @Test(priority = 3, dependsOnMethods = "createBooking")
    public void updateBooking() {
        PartialUpdateRequest update = Utils.generateUpdate();

        bookersApi.partialUpdateBooking(update, bookingId)
                .then()
                .statusCode(200);

        Booking fetched = bookersApi.getBooking(bookingId)
                .then()
                .statusCode(200)
                .extract()
                .as(Booking.class);

        Assert.assertEquals(fetched, applyUpdate(createdBooking, update),
                "GET booking does not reflect the partial update");
    }

    @Test(priority = 4, dependsOnMethods = "createBooking")
    public void deleteBooking() {
        bookersApi.deleteBooking(bookingId)
                .then()
                .statusCode(201);

        bookersApi.getBooking(bookingId)
                .then()
                .statusCode(404);
    }

    private static Booking toBooking(CreateBookingRequest r) {
        Booking b = new Booking();
        b.setFirstname(r.getFirstname());
        b.setLastname(r.getLastname());
        b.setTotalprice(r.getTotalprice());
        b.setDepositpaid(r.isDepositpaid());
        b.setBookingdates(r.getBookingdates());
        b.setAdditionalneeds(r.getAdditionalneeds());
        return b;
    }

    private static Booking applyUpdate(CreateBookingRequest base, PartialUpdateRequest u) {
        Booking b = toBooking(base);
        if (u.getFirstname() != null) b.setFirstname(u.getFirstname());
        if (u.getLastname() != null) b.setLastname(u.getLastname());
        if (u.getTotalprice() != null) b.setTotalprice(u.getTotalprice());
        if (u.getDepositpaid() != null) b.setDepositpaid(u.getDepositpaid());
        if (u.getBookingdates() != null) b.setBookingdates(u.getBookingdates());
        if (u.getAdditionalneeds() != null) b.setAdditionalneeds(u.getAdditionalneeds());
        return b;
    }
}
