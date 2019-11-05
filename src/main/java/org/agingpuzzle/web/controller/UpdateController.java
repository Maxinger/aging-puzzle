package org.agingpuzzle.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.Update;
import org.agingpuzzle.model.view.UpdateView;
import org.agingpuzzle.repo.OrganizationRepository;
import org.agingpuzzle.repo.ProjectRepository;
import org.agingpuzzle.repo.UpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.IntFunction;

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
                           @RequestParam(name="organization", required = false) Long baseOrganizationId,
                           @RequestParam(name="project", required = false) Long baseProjectId,
                           HttpServletRequest request, Model model) {

        List<UpdateView> updates;

        IntFunction<Pageable> pageable = count -> {
            var pagination = new Pagination(request, count);
            model.addAttribute("pagination", pagination);
            return pagination.toPageable(updateRepository.getDefaultSort());
        };

        if (baseProjectId != null) {
            int count = updateRepository.countAllByProject(baseProjectId, lang);
            updates = updateRepository.viewAllByProject(baseProjectId, lang, pageable.apply(count));
            model.addAttribute("project", projectRepository.findByBaseEntity_IdAndLanguage(baseProjectId, lang).orElseThrow(notFound()));
        } else if (baseOrganizationId != null) {
            int count = updateRepository.countAllByOrganization(baseOrganizationId, lang);
            updates = updateRepository.viewAllByOrganization(baseOrganizationId, lang, pageable.apply(count));
            model.addAttribute("organization", organizationRepository.findByBaseEntity_IdAndLanguage(baseOrganizationId, lang).orElseThrow(notFound()));
        } else {
            int count = updateRepository.countByLanguage(lang);
            updates = updateRepository.viewAllByLanguage(lang, pageable.apply(count));
        }

        model.addAttribute("updates", updates);
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
