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

    private final SeatingRepository seatingRepo;
    private final EventRepository eventRepo;

    public SeatingController(SeatingRepository seatingRepo, EventRepository eventRepo) {
        this.seatingRepo = seatingRepo;
        this.eventRepo = eventRepo;
    }

    @GetMapping({ "/seating/create/{id}"})
    public String create(Model model, @PathVariable long id){
        var entity= eventRepo.findById(id);

        if(entity.isPresent()){
            model.addAttribute("event", entity.get());
            model.addAttribute("seating", new Seating());
            return "/seatings/create";
        }

        return "redirect:/events/"+id;
    }

    @PostMapping({ "/seating/create/{event_id}"})
    public String create(@Valid Seating seating, BindingResult bindingResult, @PathVariable long event_id, Model model){
        var eventDb= eventRepo.findById(event_id);
        if(eventDb.isPresent()) {
            var event = eventDb.get();
            if (bindingResult.hasErrors()) {
               return "/seatings/create";
           }


           if (seating.getSeatingDateTime().toLocalDate().isBefore(event.getStartDate())) {
               bindingResult.rejectValue("seatingDateTime", "error.seatingDateTime", "Seating date cannot be before event start date");
               return "/seatings/create";
           }
           if (seating.getSeatingDateTime().plusMinutes(seating.getSeatingDuration()).toLocalDate().isAfter(event.getEndDate())) {
               bindingResult.rejectValue("seatingDuration", "error.seatingDuration", "Seating duration cannot exceed event end date");
               return "/seatings/create";
           }

           seating.setEvent(event);
           try{seatingRepo.save(seating);} catch (Exception e) {
            System.out.println(e.getMessage());
            }

       }
        return "redirect:/event/" + event_id;

    }


}
