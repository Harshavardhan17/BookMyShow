package org.harsha.bookmyshow.services;

import org.harsha.bookmyshow.models.Booking;
import org.harsha.bookmyshow.models.BookingStatus;
import org.harsha.bookmyshow.models.Show;
import org.harsha.bookmyshow.models.ShowSeat;
import org.harsha.bookmyshow.models.ShowSeatStatus;
import org.harsha.bookmyshow.models.User;
import org.harsha.bookmyshow.repositories.BookingRepository;
import org.harsha.bookmyshow.repositories.ShowRepository;
import org.harsha.bookmyshow.repositories.ShowSeatRepository;
import org.harsha.bookmyshow.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Transactional(isolation = Isolation.SERIALIZABLE)
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final BookingRepository bookingRepository;
    private final PricingCalculatorImpl pricingCalculatorImpl;


    public BookingServiceImpl(UserRepository userRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository, BookingRepository bookingRepository, PricingCalculatorImpl pricingCalculatorImpl) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
        this.pricingCalculatorImpl = pricingCalculatorImpl;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(List<Long> showSeatIds, Long userId, Long showId) {

//      * 1. Get show details from the DB
        Optional<Show> showOptional = showRepository.findById(showId);
        if (showOptional.isEmpty()) {
            throw new IllegalArgumentException("Show not found");
        }
        Show bookedShow = showOptional.get();

//     * 2. Get user details from the DB
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        User bookedBy = userOptional.get();

//         * ------ Acquire the lock ------
//         * 3. Get all the ShowSeats corresponding to showSeatIds
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
//         * 4. Check if seats are available
        for (ShowSeat showSeat : showSeats) {
            if (showSeat.getStatus() != ShowSeatStatus.AVAILABLE) {
//         * 5. If not, we throw an exception
                if (showSeat.getStatus() == ShowSeatStatus.BOOKED &&
                        Duration.between(showSeat.getCreatedAt().toInstant(), new Date().toInstant()).toMinutes() > 15) {
                    throw new RuntimeException("Blocked seats must be released");
                }

                throw new RuntimeException("Show seat not available");
            }
        }

//         * 6. Change the status to BLOCKED
        for (ShowSeat showSeat : showSeats) {
            showSeat.setStatus(ShowSeatStatus.BLOCKED);
            showSeat.setBookedAt(new Date());
//         * 7. Save details to DB
            showSeatRepository.save(showSeat);
        }
//         * ------ Release the lock ------
//         * 8. Create the booking object
        Booking booking = new Booking();
        booking.setBookedBy(bookedBy);
        booking.setBookedAt(new Date());
        booking.setPayments(new ArrayList<>());
        booking.setStatus(BookingStatus.PENDING);
        booking.setShowSeats(showSeats);
        booking.setTotalAmount(pricingCalculatorImpl.calculatePrice(showSeats, bookedShow));
//         * 9. Save to DB and return the Booking object created
        return bookingRepository.save(booking);
    }
}
