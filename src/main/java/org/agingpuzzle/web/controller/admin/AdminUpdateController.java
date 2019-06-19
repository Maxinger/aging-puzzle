package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.BaseUpdate;
import org.agingpuzzle.model.ToValidate;
import org.agingpuzzle.model.Update;
import org.agingpuzzle.repo.BaseUpdateRepository;
import org.agingpuzzle.repo.OrganizationRepository;
import org.agingpuzzle.repo.ProjectRepository;
import org.agingpuzzle.repo.UpdateRepository;
import org.agingpuzzle.web.controller.AbstractController;
import org.agingpuzzle.web.form.UpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/{lang}/admin/updates")
public class AdminUpdateController extends AbstractController {

    @Autowired
    private UpdateRepository updateRepository;

    @Autowired
    private BaseUpdateRepository baseUpdateRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public String listPage(@PathVariable String lang, Model model) {
        model.addAttribute("updates", updateRepository.findAllByLanguage(lang));

        addTranslations(lang, model, updateRepository);
        return "admin/updates";
    }

    @GetMapping("/new")
    public String newPage(@PathVariable String lang,
                          @RequestParam(required = false) Long baseId, Model model) {
        model.addAttribute("baseId", baseId);
        model.addAttribute("update", new UpdateForm());

        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang));
        model.addAttribute("projects", projectRepository.findAllByLanguage(lang));
        return "admin/update";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Update update = updateRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());
        model.addAttribute("update", update);

        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang));
        model.addAttribute("projects", projectRepository.findAllByLanguage(lang));
        return "admin/update";
    }

    @PostMapping("/save")
    public String saveUpdate(@PathVariable String lang,
                             @RequestParam(required = false) Long baseId,
                             @Validated(ToValidate.class) Update update,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "admin/update";
        }

        if (update.isNew()) {
            BaseUpdate baseEntity = baseId == null
                    ? baseUpdateRepository.save(new BaseUpdate())
                    : baseUpdateRepository.findById(baseId).get();

            update.setBaseEntity(baseEntity);
            update.setLanguage(lang);

        } else {
            Update updated = update;
            update = updateRepository.findById(update.getId()).get();
            update.setTitle(updated.getTitle());
            update.setPreview(updated.getPreview());
            update.setFullText(updated.getFullText());
        }

        updateRepository.save(update);
        log.info("Saved update {} with id={}", update.getTitle(), update.getId());

        return String.format("redirect:/%s/admin/updates", lang);
    }


    @PostMapping("/delete")
    public String delete(@PathVariable String lang, @RequestParam Long id) {
        updateRepository.deleteById(id);
        log.info("Deleted update with id={}", id);

        return String.format("redirect:/%s/admin/updates", lang);
    }
}
