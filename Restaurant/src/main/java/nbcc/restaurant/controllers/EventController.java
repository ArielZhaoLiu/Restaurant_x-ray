package nbcc.restaurant.controllers;

import jakarta.validation.Valid;
import nbcc.restaurant.entities.Event;
import nbcc.restaurant.entities.Seating;
import nbcc.restaurant.repositories.EventRepository;
import nbcc.restaurant.repositories.SeatingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class EventController {

    private final EventRepository eventRepo;
    private final SeatingRepository seatingRepo;

    public EventController(EventRepository eventRepo, SeatingRepository seatingRepository) {
        this.eventRepo = eventRepo;
        this.seatingRepo = seatingRepository;
    }

    @GetMapping({"/", "events"})
    public String getAll(Model model){
        var values = eventRepo.findAll();

        model.addAttribute( "events", values);
        return "/events/index";
    }

    @GetMapping({ "/event/create"})
    public String create(Model model){

        model.addAttribute("event", new Event());
        return "/events/create";
    }

    @PostMapping({ "/event/create"})
    public String create(@Valid Event event, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "/events/create";
        }

        if(event.getStartDate().isAfter(event.getEndDate())){
            bindingResult.rejectValue("startDate", "error.startDate", "Start date cannot be after end date");
        }

        if(bindingResult.hasErrors()){
            return "/events/create";
        }

        if (event.getSeatings() == null || event.getSeatings().isEmpty()) {
            bindingResult.rejectValue("seatings", "error.seatings", "At least one seating is required.");
            return "/events/create";
        }

        if (event.getSeatings().get(0).getSeatingDateTime().toLocalDate().isBefore(event.getStartDate())){
            bindingResult.rejectValue("seatings[0].seatingDateTime", "error.seatings[0].seatingDateTime", "Seating date cannot be before event start date");
            return "/events/create";
        }
        if(event.getSeatings().get(0).getSeatingDateTime().plusMinutes(event.getSeatings().get(0).getSeatingDuration()).toLocalDate().isAfter(event.getEndDate())){
            bindingResult.rejectValue("seatings[0].seatingDuration", "error.seatings[0].seatingDuration", "Seating duration cannot exceed event end date");
            return "/events/create";
        }

        var seating = new Seating();
        seating.setSeatingDateTime(event.getSeatings().get(0).getSeatingDateTime());
        seating.setSeatingDuration(event.getSeatings().get(0).getSeatingDuration());
        seating.setEvent(event);

        eventRepo.save(event);
        seatingRepo.save(seating);
        return "redirect:/events";
    }

    @GetMapping({ "/event/{id}"})
    public String detail(Model model, @PathVariable long id){
        var entity= eventRepo.findById(id);

        if(entity.isPresent()){
            var seatings = seatingRepo.findByEventId(id);
            entity.get().setSeatings(seatings);
            model.addAttribute("event", entity.get());
            return "/events/detail";
        }

        return "redirect:/events";
    }

}
