package org.harsha.bookmyshow.services;

import org.harsha.bookmyshow.models.Show;
import org.harsha.bookmyshow.models.ShowSeat;

import java.util.List;

public interface PricingCalculator {
    double calculatePrice(List<ShowSeat> showSeats, Show show);
}
