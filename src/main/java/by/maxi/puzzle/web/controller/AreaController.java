package by.maxi.puzzle.web.controller;

import by.maxi.puzzle.model.Area;
import by.maxi.puzzle.model.PuzzleConfig;
import by.maxi.puzzle.repo.AreaRepository;
import by.maxi.puzzle.service.PuzzleService;
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

    @Autowired
    private PuzzleService puzzleService;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String listPage(Model model) {
        model.addAttribute("areas", areaRepository.findAll());
        model.addAttribute("config", puzzleService.getConfig());
        return "areas";
    }

    @GetMapping("/new")
    public String newPage(Model model) {
        model.addAttribute("area", new Area());
        return "area";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable Long id, Model model) {
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

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        log.info("Deleted area with id={}", id);
        areaRepository.deleteById(id);

        return "redirect:/areas";
    }

    @PostMapping("/config/new")
    public String newPuzzleConfig(@ModelAttribute PuzzleConfig config) {
        log.info("Saved puzzleConfig with id={}", 0L);
        try {
            puzzleService.newPuzzleConfig(config);
            return "redirect:/";
        } catch (Exception e) {
            log.error("Failed to update puzzle config", e);
            return "redirect:/areas";
        }
    }

    private Supplier<ResponseStatusException> notFound() {
        return () -> new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
