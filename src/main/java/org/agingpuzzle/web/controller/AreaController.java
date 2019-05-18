package org.agingpuzzle.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.Area;
import org.agingpuzzle.repo.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/{lang}/areas")
public class AreaController extends AbstractController {

    @Autowired
    private AreaRepository areaRepository;

    @GetMapping
    public String listPage(@PathVariable String lang, Model model) {
        model.addAttribute("areas", areaRepository.findAllByLanguage(lang));

        return "areas";
    }

    @GetMapping("/{id}")
    public String viewPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Area area = areaRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());
        model.addAttribute("area", area);
        return "area";
    }
}
