package by.maxi.puzzle.web.controller;

import by.maxi.puzzle.model.Area;
import by.maxi.puzzle.repo.AreaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.function.Supplier;

@Slf4j
@Controller
@RequestMapping("/areas")
public class AreaController {

    @Autowired
    private AreaRepository areaRepository;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String listPage(Model model) {
        model.addAttribute("areas", areaRepository.findAll());
        return "areas";
    }

    @GetMapping("/new")
    public String newAreaPage(Model model) {
        model.addAttribute("area", new Area());
        return "area";
    }

    @GetMapping("/{id}/edit")
    public String editAreaPage(@PathVariable Long id, Model model) {
        Area area = areaRepository.findById(id).orElseThrow(notFound());

        model.addAttribute("area", area);
        return "area";
    }

    @PostMapping("/save")
    public String saveArea(@Valid Area area, BindingResult result) {
        if (result.hasErrors()) {
            return "area";
        }

        areaRepository.save(area);
        log.info("Saved area {} with id={}", area.getName(), area.getId());

        return "redirect:/areas";
    }

    private Supplier<ResponseStatusException> notFound() {
        return () -> new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
