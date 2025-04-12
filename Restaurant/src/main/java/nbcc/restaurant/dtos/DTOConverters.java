package nbcc.restaurant.dtos;

import nbcc.restaurant.entities.*;
import nbcc.restaurant.repositories.ReservationRequestRepository;
import nbcc.restaurant.services.EventService;
import nbcc.restaurant.services.MenuItemService;
import nbcc.restaurant.services.MenuService;
import nbcc.restaurant.services.SeatingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DTOConverters {

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

    public static MenuItemDTO toMenuItemDTO(MenuItem menuItem) {
        return new MenuItemDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getMenu().getId()
        );
    }

    public static MenuWithItemDTO toMenuWithItemDTO(Menu menu, Iterable<MenuItem> menuItems) {
        var menuItemDTOS = new ArrayList<MenuItemDTO>();

        for(var menuItem: menuItems){

            if(menuItem.getMenu().getId() == menu.getId()){
                menuItemDTOS.add(toMenuItemDTO(menuItem));
            }
        }

        return new MenuWithItemDTO(menu.getId(), menu.getName(), menu.getDescription(), menuItemDTOS);
    }

    public static List<MenuWithItemDTO> toMenuWithItemDTOs(Iterable<Menu> menus, Iterable<MenuItem> menuItems) {
        var MenuWithItemDTOs = new ArrayList<MenuWithItemDTO>();


        for(var menu: menus){
            var menuItemsList = new ArrayList<MenuItem>();

            for (MenuItem menuItem : menuItems) {

                if(menuItem.getMenu().getId() == menu.getId()){
                    menuItemsList.add(menuItem);
                }
            }

            MenuWithItemDTOs.add(toMenuWithItemDTO(menu, menuItemsList));
        }

        return MenuWithItemDTOs;
    }


    public static ReservationRequest toReservationRequest(Seating seating, String firstName, String lastName, String email, int groupSize) {
        return new ReservationRequest(seating, firstName, lastName, email, groupSize);
    }

    public static RequestDTO toRequestDTO(ReservationRequest reservationRequest, long event_id) {

        return new RequestDTO(
                reservationRequest.getId(),
                reservationRequest.getSeating().getId(),
                event_id,
                reservationRequest.getFirstName(),
                reservationRequest.getLastName(),
                reservationRequest.getEmail(),
                reservationRequest.getGroupSize()
        );
    }
}
