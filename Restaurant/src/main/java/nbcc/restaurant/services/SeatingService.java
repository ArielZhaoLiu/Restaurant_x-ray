package nbcc.restaurant.services;

import nbcc.restaurant.entities.Seating;
import nbcc.restaurant.repositories.SeatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatingService {
    private final SeatingRepository seatingRepository;

    public SeatingService(SeatingRepository seatingRepository) {
        this.seatingRepository = seatingRepository;
    }

    public List<Seating> findAll() {
        return seatingRepository.findAll();
    }

    public List<Seating> findByEventId(Long event_id) {
        return seatingRepository.findByEventId(event_id);
    }

    public Optional<Seating> findById(Long id) {
        return seatingRepository.findById(id);
    }

}
