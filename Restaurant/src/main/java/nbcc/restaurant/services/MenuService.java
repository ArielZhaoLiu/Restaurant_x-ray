package nbcc.restaurant.services;

import jakarta.persistence.EntityNotFoundException;
import nbcc.restaurant.entities.Menu;
import nbcc.restaurant.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getAll(){
        return menuRepository.findAll();
    }

    public Optional<Menu> getById(Long id){
        return menuRepository.findById(id);
    }

    public Menu create(Menu menu){
        return menuRepository.save(menu);
    }

    public Menu update(Long id, Menu menu){
        var dbOptMenu = menuRepository.findById(id);

        if (dbOptMenu.isEmpty()) {
            throw new EntityNotFoundException("Menu not found with id " + id);
        }

        var dbMenu = dbOptMenu.get();

        dbMenu.setName(menu.getName());
        dbMenu.setDescription(menu.getDescription());

        menu.setId(id);
        return menuRepository.save(dbMenu);
    }

    public void delete(Long id){
        menuRepository.deleteById(id);
    }
}


