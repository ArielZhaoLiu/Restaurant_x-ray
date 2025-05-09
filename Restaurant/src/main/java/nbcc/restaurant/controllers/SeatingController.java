package nbcc.restaurant.controllers;

import jakarta.validation.Valid;
import nbcc.restaurant.entities.Event;
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
public class SeatingController {

    private final SeatingRepository seatingRepo;
    private final EventRepository eventRepo;
    private final ReservationRequestRepository requestRepo;

    public SeatingController(SeatingRepository seatingRepo, EventRepository eventRepo, ReservationRequestRepository requestRepo) {
        this.seatingRepo = seatingRepo;
        this.eventRepo = eventRepo;
        this.requestRepo = requestRepo;
    }

    @GetMapping({ "/seating/create/{id}"})
    public String create(Model model, @PathVariable long id){
        var entity= eventRepo.findById(id);

        if(entity.isPresent()){
            model.addAttribute("event", entity.get());
            model.addAttribute("seating", new Seating());
            return "/seatings/create";
        }

        return "redirect:/event/detail/"+id;
    }

    @PostMapping({ "/seating/create/{event_id}"})
    public String create(@Valid Seating seating, BindingResult bindingResult, @PathVariable long event_id, Model model){
        var eventDb= eventRepo.findById(event_id);

        if(eventDb.isPresent()) {
            model.addAttribute("event", eventDb.get());
            var event = eventDb.get();
            if (bindingResult.hasErrors()) {

                return "seatings/create";
           }


           if (seating.getSeatingDateTime().toLocalDate().isBefore(event.getStartDate())) {
               bindingResult.rejectValue("seatingDateTime", "error.seatingDateTime", "Seating date cannot be before event start date");

               return "seatings/create";
           }
           if (seating.getSeatingDateTime().plusMinutes(seating.getSeatingDuration()).toLocalDate().isAfter(event.getEndDate())) {
               bindingResult.rejectValue("seatingDuration", "error.seatingDuration", "Seating duration cannot exceed event end date");
               return "seatings/create";
           }
            if (seating.getSeatingDuration()<1) {
                bindingResult.rejectValue("seatingDuration", "error.seatingDuration", "Duration cannot be less than 1");
                return "seatings/create";
            }

           seating.setEvent(event);
           try{seatingRepo.save(seating);} catch (Exception e) {
            System.out.println(e.getMessage());
            }

       }
        return "redirect:/event/detail/" + event_id;

    }

    @GetMapping({ "/seating/delete/{id}"})
    public String delete(Model model, @PathVariable long id){
        var seating= seatingRepo.findById(id);
        var request= requestRepo.findBySeatingId(id);
        boolean empty;

        if(seating != null){
            var eventDb= eventRepo.findById(seating.getEvent().getId());

            if(eventDb.isPresent()) {
            model.addAttribute("event", eventDb.get());
            model.addAttribute("seating", seating);
            }

            if (request.isEmpty())
                empty = true;
            else
                empty = false;

            model.addAttribute("requestEmpty", empty);
            return "/seatings/delete";
        }
        return "redirect:/event/detail/" + seating.getEvent().getId();
    }

    @PostMapping({ "/seating/delete/{id}"})
    public String delete( @PathVariable long id){
        var seating= seatingRepo.findById(id);

        seatingRepo.deleteById(id);

        return "redirect:/event/detail/" + seating.getEvent().getId();
    }


    @GetMapping({ "/seating/edit/{id}"})
    public String edit(Model model, @PathVariable long id){
        var seating= seatingRepo.findById(id);
        var eventDb= eventRepo.findById(seating.getEvent().getId());

        if(seating != null && eventDb.isPresent()) {
            model.addAttribute("event", eventDb.get());
            model.addAttribute("seating", seating);
            return "/seatings/edit";
        }
        return "redirect:/event/detail/" + seating.getEvent().getId();
    }

    @PostMapping({ "/seating/edit/{id}"})
    public String edit(@PathVariable long id, @Valid Seating seating, BindingResult bindingResult, Model model){
        var seatingDb= seatingRepo.findById(seating.getId());

        if(seatingDb != null){
            var eventDb= eventRepo.findById(seatingDb.getEvent().getId());
            model.addAttribute("event", eventDb.get());
            if(bindingResult.hasErrors()){
                return "/seatings/edit";
            }

            if (seating.getSeatingDateTime().toLocalDate().isBefore(eventDb.get().getStartDate())) {
                bindingResult.rejectValue("seatingDateTime", "error.seatingDateTime", "Seating date cannot be before event start date");
                return "seatings/create";
            }
            if (seating.getSeatingDateTime().plusMinutes(seating.getSeatingDuration()).toLocalDate().isAfter(eventDb.get().getEndDate())) {
                bindingResult.rejectValue("seatingDateTime", "error.seatingDateTime", "Seating duration cannot exceed event end date");
                return "seatings/create";
            }
            if (seating.getSeatingDuration()<1) {
                bindingResult.rejectValue("seatingDuration", "error.seatingDuration", "Duration cannot be less than 1");
                return "seatings/create";
            }

            seatingDb.setSeatingDateTime(seating.getSeatingDateTime());
            seatingDb.setSeatingDuration(seating.getSeatingDuration());
            try{seatingRepo.save(seatingDb);} catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return "redirect:/event/detail/" + eventDb.get().getId();
        }

        return "redirect:/event/detail/" + eventRepo.findById(seatingDb.getEvent().getId());
    }

}
