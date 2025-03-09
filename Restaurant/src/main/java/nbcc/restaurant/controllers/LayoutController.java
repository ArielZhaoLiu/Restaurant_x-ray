package nbcc.restaurant.controllers;

import jakarta.validation.Valid;
import nbcc.restaurant.entities.Layout;
import nbcc.restaurant.repositories.LayoutRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LayoutController {

    private final LayoutRepository layoutRepo;

    public LayoutController(LayoutRepository layoutRepo) {
        this.layoutRepo = layoutRepo;
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

        if(entity.isPresent()) { // this means the entity was found in the database
            model.addAttribute("layout", entity.get());
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
}
