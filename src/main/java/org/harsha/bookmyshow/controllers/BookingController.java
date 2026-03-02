package org.harsha.bookmyshow.controllers;

import org.harsha.bookmyshow.dto.BookingRequestDto;
import org.harsha.bookmyshow.dto.BookingResponseDto;
import org.harsha.bookmyshow.dto.ResponseStatus;
import org.harsha.bookmyshow.models.Booking;
import org.harsha.bookmyshow.services.BookingService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookingResponseDto bookMovie(BookingRequestDto bookingRequestdto) {
        BookingResponseDto bookingResponseDto = new BookingResponseDto();
        try {
            Booking booking = bookingService.bookMovie(
                    bookingRequestdto.getShowSeatIds(),
                    bookingRequestdto.getUserId(),
                    bookingRequestdto.getShowId()
            );
            bookingResponseDto.setStatus(ResponseStatus.SUCCESS);
            bookingResponseDto.setBookingId(booking.getId());
        } catch (Exception e) {
            bookingResponseDto.setStatus(ResponseStatus.FAILURE);
            bookingResponseDto.setFailureMessage(e.getMessage());
        }

        return bookingResponseDto;
    }
}
