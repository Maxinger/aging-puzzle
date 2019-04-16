package by.maxi.puzzle.web.controller;

import by.maxi.puzzle.service.PuzzleService;
import by.maxi.puzzle.service.UpdatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    @Autowired
    private UpdatesService updatesService;

    @Autowired
    private PuzzleService puzzleService;

    @GetMapping("/")
    public String index(Model model) {
        return index("en", model);
    }

    @GetMapping("/{lang}")
    public String index(@PathVariable String lang, Model model) {
        model.addAttribute("updates", updatesService.getLastUpdates(3));
        model.addAttribute("tiles", puzzleService.getPuzzleView());
        model.addAttribute("lang", lang);
        return "index";
    }
}
