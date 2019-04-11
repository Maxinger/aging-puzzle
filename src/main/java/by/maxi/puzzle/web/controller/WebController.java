package by.maxi.puzzle.web.controller;

import by.maxi.puzzle.service.PuzzleService;
import by.maxi.puzzle.service.UpdatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    private UpdatesService updatesService;

    @Autowired
    private PuzzleService puzzleService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("updates", updatesService.getLastUpdates(3));
        model.addAttribute("areas", puzzleService.getAreas());
        return "index";
    }

    @GetMapping("/organizations")
    public String organizations(Model model) {
        model.addAttribute("updates", updatesService.getLastUpdates(3));
        return "organizations";
    }
}
