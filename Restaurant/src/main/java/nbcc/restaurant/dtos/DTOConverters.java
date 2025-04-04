package nbcc.restaurant.dtos;

import nbcc.restaurant.entities.Event;
import nbcc.restaurant.entities.Menu;
import nbcc.restaurant.entities.Seating;
import nbcc.restaurant.services.EventService;
import nbcc.restaurant.services.MenuService;
import nbcc.restaurant.services.SeatingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DTOConverters {

    private final SeatingService seatingService;
    private final MenuService menuService;

    public DTOConverters(SeatingService seatingService, MenuService menuService) {
        this.seatingService = seatingService;
        this.menuService = menuService;
    }



    public static EventDTO toEventDTO(Event event, List<SeatingDTO> seatings, MenuDTO menu) {

            return new EventDTO(
                    event.getId(),
                    event.getName(),
                    event.getDescription(),
                    event.getStartDate(),
                    event.getEndDate(),
                    event.getPrice(),
                    seatings,
                    menu
            );
    }

    public static SeatingDTO toSeatingDTO(Seating seating) {
        return new SeatingDTO(
                seating.getId(),
                seating.getSeatingDateTime(),
                seating.getSeatingDuration()
        );
    }

    public static MenuDTO toMenuDTO(Menu menu) {
        return new MenuDTO(
                menu.getId(),
                menu.getName(),
                menu.getDescription()
        );
    }

    public static List<EventDTO> toEventDTOs(Iterable<Event> events, Iterable<Seating> seatings, Iterable<Menu> menus) {
        var eventDTOS = new ArrayList<EventDTO>();


        for(var event: events){
            var seatingDTOS = new ArrayList<SeatingDTO>();
            MenuDTO menu = new MenuDTO();

            for (Seating seating : seatings) {

                if(seating.getEvent().getId() == event.getId()){
                    seatingDTOS.add(toSeatingDTO(seating));
                }
            }

            for (Menu m : menus) {
                if(m.getId() == event.getMenu().getId()){
                    menu = toMenuDTO(m);
                    break;
                }
            }


            eventDTOS.add(toEventDTO(event, seatingDTOS, menu));
        }

        return eventDTOS;
    }
}
