package nbcc.restaurant.services;

import nbcc.restaurant.entities.MenuItem;
import nbcc.restaurant.entities.Seating;
import nbcc.restaurant.repositories.MenuItemRepository;
import nbcc.restaurant.repositories.SeatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> findAll(){return menuItemRepository.findAll();}

    public List<MenuItem> findByMenuId(Long menu_id){return menuItemRepository.findByMenuId(menu_id);}

}



