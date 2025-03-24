package nbcc.restaurant.controllers;

import jakarta.validation.Valid;
import nbcc.restaurant.entities.ReservationRequest;
import nbcc.restaurant.entities.Seating;
import nbcc.restaurant.repositories.EventRepository;
import nbcc.restaurant.repositories.ReservationRequestRepository;
import nbcc.restaurant.repositories.SeatingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReservationRequestController {

    private final ReservationRequestRepository reservationRequestRepo;
    private final SeatingRepository seatingRepo;
    private final EventRepository eventRepo;

    public ReservationRequestController(ReservationRequestRepository reservationRequestRepository, SeatingRepository seatingRepository, EventRepository eventRepository) {
        this.reservationRequestRepo = reservationRequestRepository;
        this.seatingRepo = seatingRepository;
        this.eventRepo = eventRepository;
    }

    @PostMapping({ "/seating/reserve/{id}"})
    public String requestReservation(@PathVariable long id, @Valid ReservationRequest reservationRequest, BindingResult bindingResult, Model model){

        var seating = seatingRepo.findById(id);
        var eventDb= eventRepo.findById(seating.get().getEvent().getId());

        if (bindingResult.hasErrors()) {
            return "/reservationRequests/create";
        }

        model.addAttribute("event", eventDb.get());

        reservationRequest.setSeating(seating.get());
        reservationRequestRepo.save(reservationRequest);


        return "redirect:/event/" + eventRepo.findById(seating.get().getEvent().getId());
    }

}
