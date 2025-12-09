package com.swe7303.devops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.swe7303.devops.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
