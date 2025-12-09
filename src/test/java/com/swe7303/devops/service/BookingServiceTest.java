package com.swe7303.devops.service;

import com.swe7303.devops.model.Booking;
import com.swe7303.devops.repository.BookingRepository;
import com.swe7303.devops.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void testGetAllBookings() {
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(new Booking(), new Booking()));

        List<Booking> bookings = bookingService.getAllBookings();

        assertEquals(2, bookings.size());
    }

    @Test
    void testSaveBooking() {
        Booking booking = new Booking();
        booking.setCustomerName("John Doe");

        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking result = bookingService.saveBooking(booking);

        assertNotNull(result);
        assertEquals("John Doe", result.getCustomerName());
    }

    @Test
    void testDeleteBooking() {
        bookingService.deleteBooking(1);

        verify(bookingRepository, times(1)).deleteById(1);
    }
}
