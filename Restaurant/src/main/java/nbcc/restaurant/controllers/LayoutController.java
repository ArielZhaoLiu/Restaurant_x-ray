package nbcc.restaurant.controllers;

import jakarta.validation.Valid;
import nbcc.restaurant.entities.DiningTable;
import nbcc.restaurant.entities.Layout;
import nbcc.restaurant.repositories.DiningTableRepository;
import nbcc.restaurant.repositories.LayoutRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LayoutController {

    private final LayoutRepository layoutRepo;
    private final DiningTableRepository diningTableRepo;

    public LayoutController(LayoutRepository layoutRepo, DiningTableRepository diningTableRepo) {
        this.layoutRepo = layoutRepo;
        this.diningTableRepo = diningTableRepo;
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

    @PostMapping("/layout/edit")
    public String edit(@Valid Layout layout, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/layouts/edit";
        }
        layoutRepo.save(layout);
        return "redirect:/layouts";
    }

    @PostMapping("/diningTable/create")
    public String tableCreate(@RequestParam long layoutId, @Valid DiningTable diningTable, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/layouts/edit";
        }

        Layout layout = layoutRepo.findById(layoutId).orElse(null);
        diningTable.setLayout(layout);     // if no this, layout will be null
        diningTableRepo.save(diningTable);

        return "redirect:/layout/edit/" + layoutId;
    }


    @PostMapping("/diningTable/delete/{id}")
    public String tableDelete(@PathVariable long id, @RequestParam long layoutId) {

        diningTableRepo.deleteById(id);
        return "redirect:/layout/edit/" + layoutId;
    }

    @GetMapping("/layout/{id}")
    public String detail(Model model, @PathVariable long id){

        var entity = layoutRepo.findById(id);

        if(entity.isPresent()) { // this means the entity was found in the database
            model.addAttribute("layout", entity.get());
            return "/layouts/detail";
        }
        return "redirect:/layouts";
    }

    @GetMapping("/layout/delete/{id}")
    public String delete (Model model, @PathVariable long id){

        var entity = layoutRepo.findById(id);

        if(entity.isPresent()) { // this means the entity was found in the database
            model.addAttribute("layout", entity.get());
            return "/layouts/delete";
        }

        return "redirect:/layouts";
    }

    @PostMapping("/layout/delete/{id}")
    public String delete(@PathVariable long id) {

        layoutRepo.deleteById(id);
        return "redirect:/layouts";
    }



}
