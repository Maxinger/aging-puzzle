package by.maxi.puzzle.web.controller;

import by.maxi.puzzle.model.Area;
import by.maxi.puzzle.model.BaseArea;
import by.maxi.puzzle.model.PuzzleConfig;
import by.maxi.puzzle.model.ToValidate;
import by.maxi.puzzle.repo.AreaRepository;
import by.maxi.puzzle.repo.BaseAreaRepository;
import by.maxi.puzzle.service.PuzzleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/{lang}/areas")
public class AreaController extends AbstractController {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private BaseAreaRepository baseAreaRepository;

    @Autowired
    private PuzzleService puzzleService;

    @GetMapping
    public String listPage(@PathVariable String lang, Model model) {
        model.addAttribute("areas", areaRepository.findAllByLanguage(lang));
        model.addAttribute("config", puzzleService.getConfig());

        addTranslations(lang, model, areaRepository);
        return "areas";
    }

    @GetMapping("/new")
    public String newPage(@RequestParam(required = false) Long baseId, Model model) {
        model.addAttribute("baseId", baseId);
        model.addAttribute("area", new Area());
        return "area";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Area area = areaRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());
        model.addAttribute("area", area);
        return "area";
    }

    @PostMapping("/save")
    public String saveOrganization(@PathVariable String lang,
                                   @RequestParam(required = false) Long baseId,
                                   @Validated(ToValidate.class) Area area,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "area";
        }


        if (area.isNew()) {
            BaseArea baseEntity = baseId == null
                    ? baseAreaRepository.save(new BaseArea())
                    : baseAreaRepository.findById(baseId).get();

            area.setBaseEntity(baseEntity);
            area.setLanguage(lang);

        } else {
            Area updated = area;
            area = areaRepository.findById(area.getId()).get();
            area.setName(updated.getName());
            area.setDescription(updated.getDescription());
        }

        areaRepository.save(area);
        log.info("Saved area {} with id={}", area.getName(), area.getId());

        return String.format("redirect:/%s/areas", lang);
    }


    @PostMapping("/delete")
    public String delete(@PathVariable String lang, @RequestParam Long id) {
        log.info("Deleted area with id={}", id);
        areaRepository.deleteById(id);

        return String.format("redirect:/%s/areas", lang);
    }

    @PostMapping("/config/new")
    public String newPuzzleConfig(@PathVariable String lang, @ModelAttribute PuzzleConfig config) {
        try {
            puzzleService.newPuzzleConfig(config);
            log.info("Saved puzzleConfig with id={}", config.getId());

            return String.format("redirect:/%s", lang);
        } catch (Exception e) {
            log.error("Failed to update puzzle config", e);
            return String.format("redirect:/%s/areas", lang);
        }
    }
}
