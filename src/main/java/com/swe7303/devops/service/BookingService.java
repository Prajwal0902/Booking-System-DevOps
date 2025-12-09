package com.swe7303.devops.service;

import com.swe7303.devops.model.Booking;
import java.util.List;

public interface BookingService {
    List<Booking> getAllBookings();

    Booking saveBooking(Booking booking);

    Booking getBookingById(Integer bookingId);

    void deleteBooking(Integer bookingId);
}
