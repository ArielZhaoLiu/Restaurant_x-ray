package nbcc.restaurant.controllers;

import jakarta.validation.Valid;
import nbcc.restaurant.entities.ReservationRequest;
import nbcc.restaurant.entities.ReservationStatus;
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

import java.util.List;

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

    @GetMapping({ "/seating/reserve/{id}"})
    public String reserve(@PathVariable long id, Model model){

        var seatingDb= seatingRepo.findById(id);
        var eventDb= eventRepo.findById(seatingDb.get().getEvent().getId());

        var reservation = new ReservationRequest();
        reservation.setSeating(seatingDb.get());
        model.addAttribute("reservation", reservation);
        model.addAttribute("event", eventDb.get());
        model.addAttribute("seating", seatingDb.get());

        return "/reservationRequests/create";
    }

    @PostMapping({ "/seating/reserve/{seating_id}"})
    public String reserve(@ModelAttribute("reservation") @Valid ReservationRequest reservationRequest,
                                     BindingResult bindingResult,
                                     @PathVariable long seating_id, Model model){

        var seating = seatingRepo.findById(seating_id).orElse(null);
        var eventDb= seating.getEvent();

        reservationRequest.setSeating(seating);
        reservationRequest.setStatus(ReservationStatus.PENDING); // by default, is pending

        if (bindingResult.hasErrors()) {

            model.addAttribute("event", eventDb);
            model.addAttribute("seating", seating);
            return "reservationRequests/create";
        }

        reservationRequestRepo.save(reservationRequest);

        return "reservationRequests/reservationConfirmationPage";
       // return "redirect:/event/" + eventDb.getId();
    }

    @GetMapping("/reservations")
    public String getAll(Long eventId, ReservationStatus status, Model model) {

        List<ReservationRequest> reservations;

        if (eventId != null && status != null) {
            reservations = reservationRequestRepo.findBySeating_Event_IdAndStatusOrderBySeating_SeatingDateTimeAsc(eventId, status);
        } else if (eventId != null) {
            reservations = reservationRequestRepo.findBySeating_Event_IdOrderBySeating_SeatingDateTimeAsc(eventId);
        } else if (status != null) {
            reservations = reservationRequestRepo.findByStatusOrderBySeating_SeatingDateTimeAsc(status);
        } else {
            reservations = reservationRequestRepo.findAllByOrderBySeating_SeatingDateTimeAsc();
        }

        model.addAttribute("reservations", reservations);
        model.addAttribute("events", eventRepo.findAll());
        model.addAttribute("selectedEvent", eventId);
        model.addAttribute("status", ReservationStatus.values());
        model.addAttribute("selectedStatus", status);

        return "/reservationRequests/index";
    }

    @GetMapping("/reservation/{id}")
    public String detail(Model model, @PathVariable long id){

        var reservation = reservationRequestRepo.findById(id);

        if(reservation.isPresent()) { // this means the entity was found in the database

            var seating = reservation.get().getSeating();
            var event = seating.getEvent();

            model.addAttribute("seating", seating);
            model.addAttribute("event", event);
            model.addAttribute("reservation", reservation.get());

            return "/reservationRequests/detail";
        }
        return "redirect:/reservations";
    }
}
