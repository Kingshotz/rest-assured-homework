package data.models.response.booker;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBookingResponse {
    private int bookingid;
    private BookingResponse booking;
}
