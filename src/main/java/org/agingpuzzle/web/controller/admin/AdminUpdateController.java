package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.BaseUpdate;
import org.agingpuzzle.model.Update;
import org.agingpuzzle.repo.BaseUpdateRepository;
import org.agingpuzzle.repo.OrganizationRepository;
import org.agingpuzzle.repo.ProjectRepository;
import org.agingpuzzle.repo.UpdateRepository;
import org.agingpuzzle.web.controller.AbstractController;
import org.agingpuzzle.web.controller.Pagination;
import org.agingpuzzle.web.form.UpdateForm;
import org.agingpuzzle.web.mapper.UpdateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    private UpdateMapper updateMapper;

    @GetMapping
    public String listPage(@PathVariable String lang,
                           HttpServletRequest request, Model model) {
        int count = updateRepository.countByLanguage(lang);
        Pagination pagination = new Pagination(request, count);
        model.addAttribute("pagination", pagination);

        model.addAttribute("updates",
                updateRepository.viewAllByLanguage(lang, pagination.toPageable(updateRepository.getDefaultSort())));

        addTranslations(lang, model, updateRepository);
        return "admin/updates";
    }

    private void initEditPage(Model model, String lang) {
        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang));
        model.addAttribute("projects", projectRepository.findAllByLanguage(lang));
    }

    @GetMapping("/new")
    public String newPage(@PathVariable String lang,
                          @RequestParam(required = false) Long baseId, Model model) {

        BaseUpdate baseUpdate = baseUpdateRepository.safeFindById(baseId);
        model.addAttribute("update", updateMapper.baseUpdateToForm(baseUpdate));

        initEditPage(model, lang);
        return "admin/update";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Update update = updateRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());
        model.addAttribute("update", updateMapper.updateToForm(update));

        initEditPage(model, lang);
        return "admin/update";
    }

    @PostMapping("/save")
    public String saveUpdate(@PathVariable String lang,
                             @Validated @ModelAttribute("update") UpdateForm updateForm,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            initEditPage(model, lang);
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

        updateMapper.formToUpdate(updateForm, update);

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
