package nbcc.restaurant.controllers.api;

import jakarta.validation.Valid;
import nbcc.restaurant.dtos.RequestDTO;
import nbcc.restaurant.repositories.ReservationRequestRepository;
import nbcc.restaurant.services.EventService;
import nbcc.restaurant.services.SeatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import static nbcc.restaurant.dtos.DTOConverters.*;

@RestController
@RequestMapping("/api/request")
public class ReservationRequestApiController {


    private final ReservationRequestRepository requestRepo;
    private final EventService eventService;
    private final SeatingService seatingService;


    public ReservationRequestApiController(ReservationRequestRepository requestRepo, EventService eventService, SeatingService seatingService) {
        this.requestRepo = requestRepo;
        this.eventService = eventService;
        this.seatingService = seatingService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid RequestDTO requestDTO) throws Exception {
        var event = eventService.getById(requestDTO.getEvent_id());
        if(event.isPresent()){
            var seating = seatingService.findById(requestDTO.getSeating_id());
            if(seating.isPresent()){
                if(seating.get().getEvent().getId() == event.get().getId()){
                    var reservationRequest = requestRepo.findBySeatingId(seating.get().getId());
                    if(reservationRequest !=null){
                        return new ResponseEntity<>("There is existing request", HttpStatus.BAD_REQUEST);
                    }
                    if(event.get().getEndDate().isAfter(LocalDate.now())){
                        var request = toReservationRequest(seating.get(), requestDTO.getFirstName(), requestDTO.getLastName(), requestDTO.getEmail(), requestDTO.getGroupSize());
                        request = requestRepo.save(request);
                        return new ResponseEntity<>(toRequestDTO(request, event.get().getId()), HttpStatus.CREATED);
                    }
                    else{
                        return new ResponseEntity<>("Cannot request for past event", HttpStatus.BAD_REQUEST);
                    }

                }
            }
        }
        return new ResponseEntity<>("Event or seating does not match or invalid", HttpStatus.BAD_REQUEST);
    }

}
