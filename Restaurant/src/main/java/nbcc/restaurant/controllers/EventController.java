package nbcc.restaurant.controllers;

import jakarta.validation.Valid;
import nbcc.restaurant.entities.Event;
import nbcc.restaurant.repositories.EventRepository;
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

    public EventController(EventRepository eventRepo) {
        this.eventRepo = eventRepo;
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

        eventRepo.save(event);
        return "redirect:/events";
    }

    @GetMapping({ "/event/{id}"})
    public String detail(Model model, @PathVariable long id){
        var entity= eventRepo.findById(id);

        if(entity.isPresent()){
            model.addAttribute("event", entity.get());
            return "/events/detail";
        }
        return "redirect:/events";
    }

}
