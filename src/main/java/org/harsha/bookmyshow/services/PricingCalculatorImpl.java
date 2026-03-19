package org.harsha.bookmyshow.services;

import org.harsha.bookmyshow.models.Show;
import org.harsha.bookmyshow.models.ShowSeat;
import org.harsha.bookmyshow.models.ShowSeatType;
import org.harsha.bookmyshow.repositories.ShowSeatTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingCalculatorImpl implements PricingCalculator {

    private final ShowSeatTypeRepository showSeatTypeRepository;

    public PricingCalculatorImpl(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    @Override
    public double calculatePrice(List<ShowSeat> showSeats, Show show) {
        double price = 0;
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findByShow(show);
        for (ShowSeat showSeat : showSeats) {
            for (ShowSeatType showSeatType : showSeatTypes) {
                if (showSeat.getSeat().getSeatType() == showSeatType.getSeatType()) {
                    price += showSeatType.getPrice();
                    break;
                }
            }
        }
        return price;
    }
}
