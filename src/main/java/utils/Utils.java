package utils;

import com.github.javafaker.Faker;
import data.Constants;
import data.models.request.AuthenticateRequest;
import data.models.request.BookingDates;
import data.models.request.CreateBookingRequest;
import data.models.request.CreateUserRequest;
import data.models.request.PartialUpdateRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utils {
    private static final Faker faker = new Faker();
    public static CreateUserRequest generateUser(){
        CreateUserRequest user = new CreateUserRequest();
        user.setName(faker.name().fullName());
        user.setEmail(String.format(Constants.UNIQUE_EMAIL_PATTERN, System.currentTimeMillis()));
        user.setAvatar(faker.avatar().image());
        user.setRole(faker.options().option("admin", "customer"));
        user.setPassword("password");
        return user;
    }
    public static AuthenticateRequest generateAuthReq(){
        AuthenticateRequest auth = new AuthenticateRequest();
        auth.setUsername(Constants.USERNAME);
        auth.setPassword(Constants.PASSWORD);
        return auth;
    }
    public static CreateBookingRequest generateBookingReq(){
        CreateBookingRequest req = new CreateBookingRequest();
        req.setFirstname(faker.name().firstName());
        req.setLastname(faker.name().lastName());
        req.setTotalprice(faker.number().numberBetween(100, 300));
        req.setDepositpaid(faker.bool().bool());
        req.setBookingdates(generateBookingDates());
        req.setAdditionalneeds(faker.options().option("Breakfast", "Lunch", "Dinner"));
        return req;
    }
    public static BookingDates generateBookingDates(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date checkinDate = faker.date().future(60, TimeUnit.DAYS);
        Date checkoutDate = faker.date().future(7, TimeUnit.DAYS, checkinDate);

        BookingDates dates = new BookingDates();
        dates.setCheckin(sdf.format(checkinDate));
        dates.setCheckout(sdf.format(checkoutDate));
        return dates;
    }
    public static PartialUpdateRequest generateUpdate() {
        PartialUpdateRequest request = new PartialUpdateRequest();
        boolean fieldSet = false;
        if (faker.bool().bool()) {
            request.setTotalprice(faker.number().numberBetween(50, 1000));
            fieldSet = true;
        }
        if (faker.bool().bool()) {
            request.setDepositpaid(faker.bool().bool());
            fieldSet = true;
        }
        if (faker.bool().bool()) {
            request.setBookingdates(generateBookingDates());
            fieldSet = true;
        }
        if (faker.bool().bool()) {
            request.setAdditionalneeds(faker.food().dish());
            fieldSet = true;
        }

        if (!fieldSet) {
            request.setBookingdates(generateBookingDates());
        }
        return request;
    }
}
