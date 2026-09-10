package data.models.request;

import java.util.Objects;

public class BookingDates {
	private String checkin;
	private String checkout;

	public void setCheckin(String checkin){
		this.checkin = checkin;
	}

	public String getCheckin(){
		return checkin;
	}

	public void setCheckout(String checkout){
		this.checkout = checkout;
	}

	public String getCheckout(){
		return checkout;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BookingDates)) return false;
		BookingDates that = (BookingDates) o;
		return Objects.equals(checkin, that.checkin) && Objects.equals(checkout, that.checkout);
	}

	@Override
	public int hashCode() {
		return Objects.hash(checkin, checkout);
	}

	@Override
	public String toString() {
		return "BookingDates{checkin='" + checkin + "', checkout='" + checkout + "'}";
	}
}
