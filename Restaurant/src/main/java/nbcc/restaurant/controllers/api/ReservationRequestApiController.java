package nbcc.restaurant.controllers.api;

import jakarta.validation.Valid;
import nbcc.restaurant.dtos.RequestDTO;
import nbcc.restaurant.entities.ReservationRequest;
import nbcc.restaurant.entities.ReservationStatus;
import nbcc.restaurant.repositories.ReservationRequestRepository;
import nbcc.restaurant.services.EventService;
import nbcc.restaurant.services.SeatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> create(@RequestBody @Valid RequestDTO requestDTO) throws Exception {
        var event = eventService.getById(requestDTO.getEvent_id());
        if(event.isPresent()){
            var seating = seatingService.findById(requestDTO.getSeating_id());
            if(seating.isPresent()){
                if(seating.get().getEvent().getId() == event.get().getId()){
                    var reservationRequest = requestRepo.findBySeatingId(seating.get().getId());
                    if(reservationRequest !=null){
                        return new ResponseEntity<>("There is existing request", HttpStatus.BAD_REQUEST);
                    }
                    var request = toReservationRequest(seating.get(), requestDTO.getFirstName(), requestDTO.getLastName(), requestDTO.getEmail(), requestDTO.getGroupSize());
                    request = requestRepo.save(request);
                    var str = toRequestDTO(request, event.get().getId()).toString();
                    return new ResponseEntity<>(str, HttpStatus.CREATED);
                }
            }
        }
        return new ResponseEntity<>("Event and seating does not match", HttpStatus.BAD_REQUEST);
    }

}
