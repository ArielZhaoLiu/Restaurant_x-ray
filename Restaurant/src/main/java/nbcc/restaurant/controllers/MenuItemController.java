package nbcc.restaurant.controllers;

import jakarta.validation.Valid;
import nbcc.restaurant.entities.MenuItem;
import nbcc.restaurant.repositories.MenuItemRepository;
import nbcc.restaurant.repositories.MenuRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MenuItemController {
    private final MenuItemRepository menuItemRepo;
    private final MenuRepository menuRepo;

    public MenuItemController(MenuItemRepository menuItemRepo, MenuRepository menuRepo) {
        this.menuItemRepo = menuItemRepo;
        this.menuRepo = menuRepo;
    }

    @GetMapping({ "/menuItem/create/{id}"})
    public String create(Model model, @PathVariable long id){
        var entity= menuRepo.findById(id);

        if(entity.isPresent()){
            model.addAttribute("menu", entity.get());
            model.addAttribute("menuItem", new MenuItem());
            return "/menuItems/create";
        }

        return "redirect:/menus/"+id;
    }

    @PostMapping({ "/menuItem/create/{menu_id}"})
    public String create(@Valid MenuItem menuItem, BindingResult bindingResult, @PathVariable long menu_id, Model model){
        var menuDb= menuRepo.findById(menu_id);

        if(menuDb.isPresent()) {
            model.addAttribute("menu", menuDb.get());
            var menu = menuDb.get();
            if (bindingResult.hasErrors()) {
                return "menuItems/create";
            }

            menuItem.setMenu(menu);
            try{menuItemRepo.save(menuItem);} catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        return "redirect:/menu/" + menu_id;
    }

    @GetMapping({ "/menuItem/delete/{id}"})
    public String delete(Model model, @PathVariable long id){
        var menuItem= menuItemRepo.findById(id);

        if(menuItem.isPresent()){
            var menuDb= menuRepo.findById(menuItem.get().getMenu().getId());
            if(menuDb.isPresent()) {
                model.addAttribute("menu", menuDb.get());

                model.addAttribute("menuItem", menuItem.get());
            }
            return "/menuItems/delete";

        }
        return "redirect:/menu/" + menuItem.get().getMenu().getId();
    }

    @PostMapping({ "/menuItem/delete/{id}"})
    public String delete( @PathVariable long id){
        var menuItem= menuItemRepo.findById(id);
        menuItemRepo.deleteById(id);

        return "redirect:/menu/" + menuItem.get().getMenu().getId();
    }

    @GetMapping({ "/menuItem/edit/{id}"})
    public String edit(Model model, @PathVariable long id){
        var menuItem= menuItemRepo.findById(id);
        var menuDb= menuRepo.findById(menuItem.get().getMenu().getId());

        if(menuItem.isPresent()){
            model.addAttribute("menu", menuDb.get());
            model.addAttribute("menuItem", menuItem.get());
            return "/menuItems/edit";
        }
        return "redirect:/menu/" + menuItem.get().getMenu().getId();
    }

    @PostMapping({ "/menuItem/edit/{id}"})
    public String edit(@PathVariable long id, @Valid MenuItem menuItem, BindingResult bindingResult, Model model){
        var menuItemDb= menuItemRepo.findById(menuItem.getId());

        if(menuItemDb.isPresent()){
            var menuDb= menuRepo.findById(menuItemDb.get().getMenu().getId());


            if(bindingResult.hasErrors()){
                return "/menuItems/edit";
            }

            try{menuItemRepo.save(menuItem);} catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return "redirect:/menu/" + menuDb.get().getId();
        }


        return "redirect:/menu/" + menuRepo.findById(menuItemDb.get().getMenu().getId());

    }

}

