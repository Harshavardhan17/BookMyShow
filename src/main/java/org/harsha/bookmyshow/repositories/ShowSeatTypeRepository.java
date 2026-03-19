package org.harsha.bookmyshow.repositories;

import org.harsha.bookmyshow.models.Show;
import org.harsha.bookmyshow.models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Long> {
    List<ShowSeatType> findByShow(Show show);
}
