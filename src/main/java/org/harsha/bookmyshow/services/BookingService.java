package org.harsha.bookmyshow.services;

import org.harsha.bookmyshow.models.Booking;

import java.util.List;

public interface BookingService {
   Booking bookMovie(List<Long> showSeatIds, Long userId, Long showId);
}
