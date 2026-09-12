package utils;

import com.github.javafaker.Faker;
import data.models.request.booker.BookingDates;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utils {

    private static final Faker faker = new Faker();

    public static BookingDates generateBookingDates() {
        Date checkinDate = faker.date().future(30, TimeUnit.DAYS);
        Date checkoutDate = faker.date().future(7, TimeUnit.DAYS, checkinDate);

        return BookingDates.builder()
                .checkin(toLocalDate(checkinDate))
                .checkout(toLocalDate(checkoutDate))
                .build();
    }

    private static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
