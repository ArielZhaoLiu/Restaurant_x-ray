package nbcc.restaurant.services;

import nbcc.restaurant.entities.Seating;
import nbcc.restaurant.repositories.SeatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatingService {
    private final SeatingRepository seatingRepository;

    public SeatingService(SeatingRepository seatingRepository) {
        this.seatingRepository = seatingRepository;
    }

    public List<Seating> findAll() {
        return seatingRepository.findAll();
    }

}
