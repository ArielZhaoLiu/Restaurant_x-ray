package nbcc.restaurant.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import nbcc.restaurant.entities.DiningTable;
import nbcc.restaurant.entities.Layout;
import nbcc.restaurant.repositories.DiningTableRepository;
import nbcc.restaurant.repositories.EventRepository;
import nbcc.restaurant.repositories.LayoutRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class LayoutController {

    private final LayoutRepository layoutRepo;
    private final DiningTableRepository diningTableRepo;
    private final EventRepository eventRepository;

    public LayoutController(LayoutRepository layoutRepo, DiningTableRepository diningTableRepo, EventRepository eventRepository) {
        this.layoutRepo = layoutRepo;
        this.diningTableRepo = diningTableRepo;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/layouts")
    public String getAll(Model model) {
        var values = layoutRepo.findAll();
        model.addAttribute("layouts", values);
        return "/layouts/index";
    }

    @GetMapping("/layout/create")
    public String create(Model model) {

        model.addAttribute("layout", new Layout());
        return "/layouts/create";
    }

    @PostMapping("/layout/create")
    public String create(@Valid Layout layout, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/layouts/create";
        }
        layoutRepo.save(layout);
        return "redirect:/layouts";
    }

    @GetMapping("/layout/edit/{id}")
    public String edit(Model model, @PathVariable long id) {
        var entity = layoutRepo.findById(id);
        var tables = diningTableRepo.findByLayoutId(id);

        if(entity.isPresent()) { // this means the entity was found in the database
            model.addAttribute("layout", entity.get());
            model.addAttribute("diningTables", tables); // can't just diningTableRepo.getAll(), this gonna all tables,no just associated with current layout id
            model.addAttribute("newDiningTable", new DiningTable()); //add create new table

            return "/layouts/edit";
        }
        return "redirect:/layouts";
    }

    @PostMapping("/layout/edit/{id}")
    public String edit(@Valid Layout layout, BindingResult bindingResult, Model model, @PathVariable long id) {

        if (bindingResult.hasErrors()) {
            return "/layouts/edit";
        }

        var dbOptionalLayout = layoutRepo.findById(id);
        if (dbOptionalLayout.isEmpty()){
            throw new EntityNotFoundException("Layout with id " + id + " not found");
        }
        var dbLayout = dbOptionalLayout.get();
        dbLayout.setName(layout.getName());
        dbLayout.setDescription(layout.getDescription());

        layoutRepo.save(dbLayout);
        return "redirect:/layout/edit/" + id;
    }

    @PostMapping("/diningTable/create/{layoutId}")
    public String tableCreate(@PathVariable long layoutId, @Valid DiningTable diningTable, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/layouts/edit";
        }

        Layout layout = layoutRepo.findById(layoutId).orElse(null);
        if (layout == null) {
            return "/layouts/edit";
        }

        layout.setLastUpdatedDate();
        diningTable.setLayout(layout);     // if no this, layout will be null
        layoutRepo.save(layout);

        // want to: if table number already existed in database, let user choose another one, or alert user will cover the previous one.
//        var tableId = diningTable.getId();
//        var entity = diningTableRepo.findById(tableId);
//        if(entity.isPresent()) {
//            return "/layouts/edit";
//        }


        diningTableRepo.save(diningTable);

        return "redirect:/layout/edit/" + layoutId;
    }


    @PostMapping("/diningTable/delete/{id}")
    public String tableDelete(@PathVariable long id) {

        var table = diningTableRepo.findById(id).orElse(null);

        var layoutId = table.getLayout().getId();

        Layout layout = layoutRepo.findById(layoutId).orElse(null);
        if (layout == null) {
            return "/layouts/edit";
        }

        layout.setLastUpdatedDate();

        diningTableRepo.deleteById(id);
        return "redirect:/layout/edit/" + layoutId;
    }

    @GetMapping("/layout/{id}")
    public String detail(Model model, @PathVariable long id){

        var entity = layoutRepo.findById(id);
        var tables = diningTableRepo.findByLayoutId(id);

        if(entity.isPresent()) { // this means the entity was found in the database
            model.addAttribute("layout", entity.get());
            model.addAttribute("diningTables", tables);
            return "/layouts/detail";
        }
        return "redirect:/layouts";
    }

    @GetMapping("/layout/delete/{id}")
    public String delete (Model model, @PathVariable long id){

        var entity = layoutRepo.findById(id);
        var tables = diningTableRepo.findByLayoutId(id);
        var layout = layoutRepo.findById(id).orElse(null);

        if(entity.isPresent()) { // this means the entity was found in the database
            if (layout != null) {
                if(layout.getEvents() != null && !layout.getEvents().isEmpty()){
                    layout.setArchived(true);
                }

                model.addAttribute("layout", entity.get());
                model.addAttribute("diningTables", tables);
            }

            return "/layouts/delete";
        }

        return "redirect:/layouts";
    }

    @PostMapping("/layout/delete/{id}")
    public String delete(@PathVariable long id) {

        var tables = diningTableRepo.findByLayoutId(id);
        var layout = layoutRepo.findById(id).orElse(null);

        if (layout != null) {
            if(layout.getEvents() != null && !layout.getEvents().isEmpty()){
                layout.setArchived(true);
                layoutRepo.save(layout);
            } else {
                diningTableRepo.deleteAll(tables);
                layoutRepo.deleteById(id);
            }
        }

        return "redirect:/layouts";
    }

}
