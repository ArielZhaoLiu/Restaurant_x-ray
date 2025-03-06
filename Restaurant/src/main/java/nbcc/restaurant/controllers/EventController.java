package nbcc.restaurant.controllers;

import jakarta.validation.Valid;
import nbcc.restaurant.entities.Event;
import nbcc.restaurant.repositories.EventRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EventController {

    private final EventRepository eventRepo;

    public EventController(EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    @GetMapping({ "/event/create"})
    public String create(Model model){

        model.addAttribute("event", new Event());
        return "/events/create";
    }


}
