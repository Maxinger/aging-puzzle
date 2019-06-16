package org.agingpuzzle.web.controller;

import org.agingpuzzle.model.view.Statistics;
import org.agingpuzzle.repo.ProjectRepository;
import org.agingpuzzle.service.PuzzleService;
import org.agingpuzzle.web.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private PuzzleService puzzleService;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/")
    public String index(@SessionAttribute(value = "lang", required = false) String lang) {
        return String.format("redirect:/%s", lang != null ? lang : WebUtils.SUPPORTED_LANGUAGES[0]);
    }

    @GetMapping("/{lang:[a-z]{2}}")
    public String index(@PathVariable String lang, Model model) {
        model.addAttribute("tiles", puzzleService.getPuzzleView(lang));
        var counts = projectRepository.getCountsByArea(lang).stream()
                .collect(Collectors.toMap(Statistics::getId, Statistics::getCount));

        model.addAttribute("projectCounts", counts);
        return "index";
    }

    @GetMapping("/{lang}/login")
    public String loginPage() {
        return "login";
    }
}
