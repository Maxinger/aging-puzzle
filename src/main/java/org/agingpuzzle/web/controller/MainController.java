package org.agingpuzzle.web.controller;

import org.agingpuzzle.service.PuzzleService;
import org.agingpuzzle.web.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    @Autowired
    private PuzzleService puzzleService;

    @GetMapping("/")
    public String index() {
        return String.format("redirect:/%s", WebUtils.SUPPORTED_LANGUAGES[0]);
    }

    @GetMapping("/{lang:[a-z]{2}}")
    public String index(@PathVariable String lang, Model model) {
        model.addAttribute("tiles", puzzleService.getPuzzleView(lang));
        return "index";
    }

    @GetMapping("/{lang}/login")
    public String loginPage() {
        return "login";
    }
}
