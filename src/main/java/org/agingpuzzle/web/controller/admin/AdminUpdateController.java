package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.*;
import org.agingpuzzle.repo.*;
import org.agingpuzzle.web.controller.AbstractController;
import org.agingpuzzle.web.controller.Pagination;
import org.agingpuzzle.web.form.FormMapper;
import org.agingpuzzle.web.form.UpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Optional;

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

    @Autowired
    private FormMapper formMapper;

    @GetMapping
    public String listPage(@PathVariable String lang,
                           @RequestParam(defaultValue = "1") int page,
                           HttpServletRequest request, Model model) {
        int count = updateRepository.countByLanguage(lang);
        Pagination pagination = new Pagination(request, page, count);

        model.addAttribute("updates",
                updateRepository.viewAllByLanguage(lang,
                        updateRepository.page(page - 1, pagination.getItemsPerPage())));

        model.addAttribute("pagination", pagination);

        addTranslations(lang, model, updateRepository);
        return "admin/updates";
    }

    @GetMapping("/new")
    public String newPage(@PathVariable String lang,
                          @RequestParam(required = false) Long baseId, Model model) {
        UpdateForm updateForm = new UpdateForm();
        updateForm.setDate(LocalDate.now());
        updateForm.setBaseId(baseId);
        model.addAttribute("update", updateForm);

        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang));
        model.addAttribute("projects", projectRepository.findAllByLanguage(lang));
        return "admin/update";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Update update = updateRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());
        model.addAttribute("update", formMapper.updateToForm(update));

        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang));
        model.addAttribute("projects", projectRepository.findAllByLanguage(lang));
        return "admin/update";
    }

    @PostMapping("/save")
    public String saveUpdate(@PathVariable String lang,
                             @Validated @ModelAttribute("update") UpdateForm updateForm,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "admin/update";
        }

        Update update;
        if (updateForm.isNew()) {
            BaseUpdate baseEntity = updateForm.getBaseId() == null
                    ? baseUpdateRepository.save(new BaseUpdate())
                    : baseUpdateRepository.findById(updateForm.getBaseId()).get();

            update = new Update();
            update.setBaseEntity(baseEntity);
            update.setLanguage(lang);
        } else {
            update = updateRepository.findById(updateForm.getId()).get();
        }

        formMapper.formToUpdate(updateForm, update);

        baseUpdateRepository.save(update.getBaseEntity());
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
