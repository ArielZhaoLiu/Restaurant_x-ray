package nbcc.restaurant.controllers;

import nbcc.restaurant.entities.Layout;
import nbcc.restaurant.repositories.LayoutRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

        model.addAttribute("game", new Layout());
        return "/layouts/create";
    }
}
