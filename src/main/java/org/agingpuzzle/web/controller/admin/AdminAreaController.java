package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.*;
import org.agingpuzzle.repo.AreaRepository;
import org.agingpuzzle.repo.BaseAreaRepository;
import org.agingpuzzle.service.ImageService;
import org.agingpuzzle.service.PuzzleService;
import org.agingpuzzle.web.controller.AbstractController;
import org.agingpuzzle.web.form.AreaForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/{lang}/admin/areas")
public class AdminAreaController extends AbstractController {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private BaseAreaRepository baseAreaRepository;

    @Autowired
    private PuzzleService puzzleService;

    @Autowired
    private ImageService imageServce;

    @GetMapping
    public String listPage(@PathVariable String lang, Model model) {
        model.addAttribute("areas", areaRepository.findAllByLanguage(lang));
        model.addAttribute("config", puzzleService.getConfig());

        addTranslations(lang, model, areaRepository);
        return "admin/areas";
    }

    @GetMapping("/new")
    public String newPage(@RequestParam(required = false) Long baseId, Model model) {
        AreaForm areaForm = new AreaForm();
        areaForm.setBaseId(baseId);

        model.addAttribute("area", areaForm);
        return "admin/area";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Area area = areaRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());

        AreaForm areaForm = new AreaForm();
        areaForm.setId(area.getId());
        areaForm.setBaseId(area.getBaseId());
        areaForm.setName(area.getName());
        areaForm.setDescription(area.getDescription());
        Optional.ofNullable(area.getImage()).ifPresent(image -> {
            areaForm.setImagePath(image.getPath());
            areaForm.setImageSource(image.getSource());
        });

        model.addAttribute("area", areaForm);
        return "admin/area";
    }

    @PostMapping("/save")
    public String saveArea(@PathVariable String lang,
                           @RequestParam MultipartFile file,
                           @Validated(ToValidate.class) @ModelAttribute("area") AreaForm areaForm,
                           BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "admin/area";
        }

        Area area;
        if (areaForm.isNew()) {
            BaseArea baseEntity = areaForm.getBaseId() == null
                    ? baseAreaRepository.save(new BaseArea())
                    : baseAreaRepository.findById(areaForm.getBaseId()).get();

            area = new Area();
            area.setBaseEntity(baseEntity);
            area.setLanguage(lang);

        } else {
            area = areaRepository.findById(areaForm.getId()).get();
        }

        area.setName(areaForm.getName());
        area.setDescription(areaForm.getDescription());

        Image image = Optional.ofNullable(area.getImage()).orElse(new Image());
        image.setSource(areaForm.getImageSource());

        if (!file.isEmpty()) {
            imageServce.saveImage(file, image);
        }
        if (image.getPath() != null) {
            area.setImage(image);
        }

        baseAreaRepository.save(area.getBaseEntity());
        areaRepository.save(area);
        log.info("Saved area {} with id={}", area.getName(), area.getId());

        puzzleService.refreshPuzzle();

        return String.format("redirect:/%s/admin/areas", lang);
    }


    @PostMapping("/delete")
    public String delete(@PathVariable String lang, @RequestParam Long id) {
        areaRepository.deleteById(id);
        log.info("Deleted area with id={}", id);

        puzzleService.refreshPuzzle();

        return String.format("redirect:/%s/admin/areas", lang);
    }

    @PostMapping("/config/new")
    public String newPuzzleConfig(@PathVariable String lang, @ModelAttribute PuzzleConfig config) {
        try {
            puzzleService.newPuzzleConfig(config);
            log.info("Saved puzzleConfig with id={}", config.getId());

            return String.format("redirect:/%s", lang);
        } catch (Exception e) {
            log.error("Failed to update puzzle config", e);
            return String.format("redirect:/%s/admin/areas", lang);
        }
    }
}
