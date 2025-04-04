package nbcc.restaurant.controllers.api;

import nbcc.restaurant.dtos.EventDTO;
import nbcc.restaurant.dtos.MenuWithItemDTO;
import nbcc.restaurant.entities.MenuItem;
import nbcc.restaurant.services.EventService;
import nbcc.restaurant.services.MenuItemService;
import nbcc.restaurant.services.MenuService;
import nbcc.restaurant.services.SeatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static nbcc.restaurant.dtos.DTOConverters.*;

@RestController
@RequestMapping("/api/menu")
public class MenuApiController {

    private final MenuService menuService;
    private final MenuItemService menuItemService;

    public MenuApiController(MenuService menuService, MenuItemService menuItemService) {
        this.menuService = menuService;
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public List<MenuWithItemDTO> getAll(){
        return toMenuWithItemDTOs(menuService.getAll(), menuItemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuWithItemDTO> get(@PathVariable long id) {
        var menu = menuService.getById(id);
        if (menu.isEmpty()) {
            return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        var menuItems = menuItemService.findByMenuId(menu.get().getId());


        return new  ResponseEntity<>(toMenuWithItemDTO(menu.get(),menuItems), HttpStatus.OK);

    }
}
