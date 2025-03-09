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

@Controller
public class SeatingController {

    private final SeatingRepository seatingRepository;
    private final EventRepository eventRepository;

    public SeatingController(SeatingRepository seatingRepository, EventRepository eventRepository) {
        this.seatingRepository = seatingRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping({ "/seating/create/{id}"})
    public String create(Model model, @PathVariable long id){
        var event= eventRepository.findById(id);

        if(event.isPresent()){
            model.addAttribute("event", event.get());
        }

        model.addAttribute("seating", new Event());
        return "/seatings/create";
    }

    @PostMapping({ "/seating/create"})
    public String create(@Valid Seating seating, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "/seatings/create";
        }

        seatingRepository.save(seating);
        return "redirect:/seatings";
    }

}
