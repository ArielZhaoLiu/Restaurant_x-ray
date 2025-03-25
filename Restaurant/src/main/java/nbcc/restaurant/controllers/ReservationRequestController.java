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
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @PostMapping({ "/seating/reserve/{seating_id}"})
    public String requestReservation(@ModelAttribute("reservation") @Valid ReservationRequest reservationRequest,
                                     BindingResult bindingResult,
                                     @PathVariable long seating_id, Model model){

        var seating = seatingRepo.findById(seating_id).orElse(null);
        var eventDb= eventRepo.findById(seating.getEvent().getId()).orElse(null);

        reservationRequest.setSeating(seating);

        model.addAttribute("event", eventDb);
        model.addAttribute("seating", seating);

        if (bindingResult.hasErrors()) {

            return "reservationRequests/create";
        }

        reservationRequestRepo.save(reservationRequest);

        return "redirect:/event/" + eventDb.getId();
    }

}
