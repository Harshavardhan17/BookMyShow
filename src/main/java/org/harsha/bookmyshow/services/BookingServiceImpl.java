package org.harsha.bookmyshow.services;

import org.harsha.bookmyshow.models.Booking;
import org.harsha.bookmyshow.repositories.BookingRepository;
import org.harsha.bookmyshow.repositories.ShowRepository;
import org.harsha.bookmyshow.repositories.ShowSeatRepository;
import org.harsha.bookmyshow.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(isolation = Isolation.SERIALIZABLE)
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final BookingRepository bookingRepository;

    public BookingServiceImpl(UserRepository userRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking bookMovie(List<Long> showSeatIds, Long userId, Long showId) {

        /*
         * 1. Get show details from the DB
         * 2. Get user details from the DB
         * ------ Acquire the lock ------
         * 3. Get all the ShowSeats corresponding to showSeatIds
         * 4. Check if seats are available
         * 5. If not, we throw an exception
         * 6. Change the status to BLOCKED
         * 7. Save details to DB
         * ------ Release the lock ------
         * 8. Create the booking object
         * 9. Save to DB and return the Booking object created
         * */
        return null;
    }
}
