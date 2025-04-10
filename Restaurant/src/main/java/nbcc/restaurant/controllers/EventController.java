package nbcc.restaurant.controllers;

import jakarta.validation.Valid;
import nbcc.restaurant.entities.Event;
import nbcc.restaurant.entities.Menu;
import nbcc.restaurant.entities.Layout;
import nbcc.restaurant.entities.Seating;
import nbcc.restaurant.repositories.*;
import nbcc.restaurant.services.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class EventController {

    private final EventRepository eventRepo;
    private final SeatingRepository seatingRepo;
    private final MenuService menuService;
    private final LayoutRepository layoutRepo;
    private final ReservationRequestRepository requestRepo;

    public EventController(EventRepository eventRepo, SeatingRepository seatingRepository, MenuService menuService, LayoutRepository layoutRepo, ReservationRequestRepository requestRepo) {
        this.eventRepo = eventRepo;
        this.seatingRepo = seatingRepository;
        this.menuService = menuService;
        this.layoutRepo = layoutRepo;
        this.requestRepo = requestRepo;
    }

    @ModelAttribute("menus")
    public List<Menu> getAllMenus() {
        return menuService.getAll();
    }
    @ModelAttribute("layouts") // adds this attribute to all get and post mappings
    public List<Layout> getAllLayouts() {
        return layoutRepo.findAll();
    }

    @GetMapping({"/", "events"})
    public String getAll(Model model){
        var values = eventRepo.findAll();

        model.addAttribute( "events", values);
        model.addAttribute("dateNow", LocalDate.now());

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
            return "/events/create";
        }

        if (event.getSeatings().get(0).getSeatingDateTime() == null ) {
            bindingResult.rejectValue("seatings[0].seatingDateTime", "error.seatings[0].seatingDateTime","At least one seating is required.");
            return "/events/create";
        }

        if (event.getSeatings().get(0).getSeatingDuration() <1 ) {
            bindingResult.rejectValue("seatings[0].seatingDuration", "error.seatings[0].seatingDuration","Duration cannot be less than 1");
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

    @GetMapping({ "/event/detail/{id}"})
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

    @GetMapping({ "/event/reserve/{id}"})
    public String reserve(Model model, @PathVariable long id){
        var entity= eventRepo.findById(id);

        if(entity.isPresent()){
            var seatings = seatingRepo.findByEventId(id);
            entity.get().setSeatings(seatings);
            model.addAttribute("event", entity.get());
            return "/events/reserve";
        }

        return "redirect:/events";
    }

    @GetMapping({ "/event/delete/{id}"})
    public String delete(Model model, @PathVariable long id){
        var entity= eventRepo.findById(id);
        var seatings = seatingRepo.findByEventId(id);

        boolean empty=true;

        if(entity.isPresent()){
            for(Seating seating: seatings){
                var request = requestRepo.findBySeatingId(seating.getId());
                if(request == null) {
                    empty = true;
                }
                else {
                    empty = false;
                    break;
                }
            }

            model.addAttribute("requestEmpty", empty);
            model.addAttribute("event", entity.get());
            model.addAttribute("dateNow", LocalDate.now());
            return "/events/delete";
        }


        return "redirect:/events";
    }

    @PostMapping({ "/event/delete/{id}"})
    public String delete( @PathVariable long id){
        var entity= eventRepo.findById(id);


        if(entity.isPresent()){
           var event= entity.get();

            if(event.getStartDate().isBefore(LocalDate.now()) && event.getEndDate().isBefore(LocalDate.now())){
                event.setArchived(true);
                eventRepo.save(event);
                return "redirect:/events";
            }

            if(!event.getSeatings().isEmpty()){
                var seatings = seatingRepo.findByEventId(id);
                seatingRepo.deleteAll(seatings);
            }
            eventRepo.deleteById(id);
        }

        return "redirect:/events";
    }

    @GetMapping({ "/event/edit/{id}"})
    public String edit(Model model, @PathVariable long id){
        var entity= eventRepo.findById(id);

        if(entity.isPresent()){
            model.addAttribute("event", entity.get());
            return "/events/edit";
        }
        return "redirect:/events";
    }

    @PostMapping({ "/event/edit"})
    public String edit(@Valid Event event, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "/events/edit";
        }

        if(event.getStartDate().isAfter(event.getEndDate())){
            bindingResult.rejectValue("startDate", "error.startDate", "Start date cannot be after end date");
            return "/events/edit";
        }

        eventRepo.save(event);
        return "redirect:/events";

    }

    @PostMapping("/event/filter")
    public String filter(String startDate, String endDate, Model model) {
        LocalDate s;
        LocalDate e;
        var events = eventRepo.findAll();

        if(startDate.isEmpty() && endDate.isEmpty() ) {
            return "redirect:/events";
        }
        else if(endDate.isEmpty()) {
            s = LocalDate.parse(startDate);
            events = eventRepo.findByStartDateGreaterThanEqual(s);
        }
        else if (startDate.isEmpty()){
            e = LocalDate.parse(endDate);
            events = eventRepo.findByEndDateLessThanEqual(e);

        }
        else {
            s = LocalDate.parse(startDate);
            e = LocalDate.parse(endDate);

            events = eventRepo.findByStartDateGreaterThanEqualAndEndDateLessThanEqual(s, e);
        }
        model.addAttribute("dateNow", LocalDate.now());
        model.addAttribute("events", events);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "/events/index";
    }
}


