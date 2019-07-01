package org.agingpuzzle.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.Update;
import org.agingpuzzle.model.view.UpdateView;
import org.agingpuzzle.repo.MemberRepository;
import org.agingpuzzle.repo.OrganizationRepository;
import org.agingpuzzle.repo.ProjectRepository;
import org.agingpuzzle.repo.UpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/{lang}/updates")
public class UpdateController extends AbstractController {

    @Autowired
    private UpdateRepository updateRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @GetMapping
    public String listPage(@PathVariable String lang,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(name="organization", required = false) Long baseOrganizationId,
                           @RequestParam(name="project", required = false) Long baseProjectId,
                           HttpServletRequest request, Model model) {

        int count;
        List<UpdateView> updates;

        int itemsPerPage = Pagination.DEFAULT_ITEMS_PER_PAGE;

        if (baseProjectId != null) {
            count = updateRepository.countAllByProject(baseProjectId, lang);
            updates = updateRepository.viewAllByProject(baseProjectId, lang, updateRepository.page(page - 1, itemsPerPage));
            model.addAttribute("project", projectRepository.findByBaseEntity_IdAndLanguage(baseProjectId, lang).orElseThrow(notFound()));
        } else if (baseOrganizationId != null) {
            count = updateRepository.countAllByOrganization(baseOrganizationId, lang);
            updates = updateRepository.viewAllByOrganization(baseOrganizationId, lang, updateRepository.page(page - 1, itemsPerPage));
            model.addAttribute("organization", organizationRepository.findByBaseEntity_IdAndLanguage(baseOrganizationId, lang).orElseThrow(notFound()));
        } else {
            count = updateRepository.countByLanguage(lang);
            updates = updateRepository.viewAllByLanguage(lang, updateRepository.page(page - 1, itemsPerPage));
        }

        model.addAttribute("updates", updates);
        model.addAttribute("pagination", new Pagination(request, page, count, itemsPerPage));

        addTranslations(lang, model, updateRepository);
        return "updates";
    }

    @GetMapping("/{id}")
    public String viewPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Update update = updateRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());
        model.addAttribute("update", update);
        return "update";
    }
}
