package data.models.response;

public class CreateBookingResponse {
    private int bookingid;
    private Booking booking;

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public int getBookingid() {
        return bookingid;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }
}
