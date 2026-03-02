package org.harsha.bookmyshow.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequestDto {

    private List<Long> showSeatIds;

    private Long userId;

    private Long showId;
}
