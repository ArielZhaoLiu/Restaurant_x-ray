package nbcc.restaurant.controllers.api;

import nbcc.restaurant.dtos.EventDTO;
import nbcc.restaurant.dtos.MenuDTO;
import nbcc.restaurant.dtos.SeatingDTO;
import nbcc.restaurant.entities.Seating;
import nbcc.restaurant.services.EventService;
import nbcc.restaurant.services.MenuService;
import nbcc.restaurant.services.SeatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static nbcc.restaurant.dtos.DTOConverters.toEventDTO;
import static nbcc.restaurant.dtos.DTOConverters.toEventDTOs;


@RestController
@RequestMapping("/api/event")
public class EventApiController {
    private final EventService eventService;
    private final SeatingService seatingService;
    private final MenuService menuService;


    public EventApiController(EventService eventService, SeatingService seatingService, MenuService menuService) {
        this.eventService = eventService;
        this.seatingService = seatingService;
        this.menuService = menuService;
    }

    @GetMapping
    public List<EventDTO> getAll(){
        return toEventDTOs(eventService.getAll(), seatingService.findAll(), menuService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> get(@PathVariable long id) {
        var event = eventService.getById(id);

        if (event.isEmpty()) {
            return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        var menuDB = menuService.getById(event.get().getMenu().getId());
        var menuDTO = new MenuDTO(menuDB.get().getId(), menuDB.get().getName(), menuDB.get().getDescription());

        var seatings = seatingService.findByEventId(id);
        var seatingList = seatings.stream().toList();
        List<SeatingDTO> seatingDTOList = new ArrayList<>();

        for (Seating seating : seatingList) {
            SeatingDTO seatingDTO = new SeatingDTO(seating.getId(), seating.getSeatingDateTime(), seating.getSeatingDuration());
            seatingDTOList.add(seatingDTO);
        }

        return new  ResponseEntity<>(toEventDTO(event.get(),seatingDTOList, menuDTO), HttpStatus.OK);
    }


}
