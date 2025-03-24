package nbcc.restaurant.controllers;

import jakarta.validation.Valid;
import nbcc.restaurant.entities.Menu;
import nbcc.restaurant.repositories.EventRepository;
import nbcc.restaurant.repositories.MenuItemRepository;
import nbcc.restaurant.services.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/menu"})
public class MenuController {

    private final MenuService menuService;
    private final MenuItemRepository menuItemRepo;
    private final EventRepository eventRepo;
    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    public MenuController(MenuService menuService, MenuItemRepository menuItemRepo, EventRepository eventRepo) {
        this.menuService = menuService;
        this.menuItemRepo = menuItemRepo;
        this.eventRepo = eventRepo;
    }

    @GetMapping
    public String getAll(Model model){
        var values = menuService.getAll();

        model.addAttribute( "menus", values);
        return "/menus/index";
    }

    @GetMapping({ "/create"})
    public String create(Model model){
        model.addAttribute("menu", new Menu());
        return "/menus/create";
    }

    @PostMapping({ "/create"})
    public String create(@Valid Menu menu, BindingResult result){
        if(result.hasErrors()){
            return "/menus/create";
        }

        try{
            menuService.create(menu);
        }catch (Exception e){
            log.error("Failed to create menu", e);
        }

        return "redirect:/menu";
    }

    @GetMapping({ "/edit/{id}"})
    public String edit(Model model, @PathVariable long id){
        var entity= menuService.getById(id);

        if(entity.isPresent()){
            model.addAttribute("menu", entity.get());
            return "/menus/edit";
        }
        return "redirect:/menu";
    }

    @PostMapping({ "/edit/{id}"})
    public String edit(@PathVariable long id, @Valid Menu menu, BindingResult result ){
        if(result.hasErrors()){
            return "/menus/edit";
        }

        menuService.update(id, menu);
        return "redirect:/menu";
    }

    @GetMapping({ "/{id}"})
    public String detail(Model model, @PathVariable long id){
        var entity= menuService.getById(id);

        if(entity.isPresent()){
            var menuItems = menuItemRepo.findByMenuId(id);
            entity.get().setMenuItems(menuItems);
            model.addAttribute("menu", entity.get());
            return "/menus/detail";
        }
        return "redirect:/menu";


    }

    @GetMapping({ "/delete/{id}"})
    public String delete(Model model, @PathVariable long id){
        var entity= menuService.getById(id);
        var event = eventRepo.findByMenuId(id);
        boolean empty;

            if (entity.isPresent()) {
                model.addAttribute("menu", entity.get());
                if(event.isEmpty()) {
                    empty = true;
                    model.addAttribute("eventEmpty", empty);
                }
                else{
                    empty = false;
                    model.addAttribute("eventEmpty", empty);
                }
                return "/menus/delete";
            }

        return "redirect:/menu";
    }

    @PostMapping({ "/delete/{id}"})
    public String delete( @PathVariable long id) {
        var entity = menuService.getById(id);

            if (entity.isPresent()) {
                var menu = entity.get();
                if (!menu.getMenuItems().isEmpty()) {
                    var menuItems = menuItemRepo.findByMenuId(id);
                    menuItemRepo.deleteAll(menuItems);
                }
                menuService.delete(id);
            }



        return "redirect:/menu";
    }
}
