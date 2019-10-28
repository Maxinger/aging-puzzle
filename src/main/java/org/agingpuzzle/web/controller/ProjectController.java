package org.agingpuzzle.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.Area;
import org.agingpuzzle.model.Organization;
import org.agingpuzzle.model.Project;
import org.agingpuzzle.repo.AreaRepository;
import org.agingpuzzle.repo.MemberRepository;
import org.agingpuzzle.repo.OrganizationRepository;
import org.agingpuzzle.repo.ProjectRepository;
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
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Slf4j
@Controller
@RequestMapping("/{lang}/projects")
public class ProjectController extends AbstractController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping
    public String listPage(@PathVariable String lang,
                           @RequestParam(name="area", required = false) Long baseAreaId,
                           HttpServletRequest request, Model model) {

        List<Project> projects;
        IntFunction<Pageable> pageable = count -> {
            var pagination = new Pagination(request, count);
            model.addAttribute("pagination", pagination);
            return pagination.toPageable();
        };

        if (baseAreaId != null) {
            int count = projectRepository.countByArea(baseAreaId, lang);
            projects = projectRepository.findAllByArea(baseAreaId, lang, pageable.apply(count));

            Area area = areaRepository.findByBaseEntity_IdAndLanguage(baseAreaId, lang).orElseThrow(notFound());
            model.addAttribute("areas", Map.of(baseAreaId, area));

            model.addAttribute("baseAreaId", baseAreaId);
        } else {
            int count = projectRepository.countByLanguage(lang);
            projects = projectRepository.findAllByLanguage(lang, pageable.apply(count));

            model.addAttribute("areas", areaRepository.findAllByLanguage(lang)
                    .stream().collect(Collectors.toMap(Area::getBaseId, identity())));
        }

        model.addAttribute("projects", projects);

        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang)
                .stream().collect(Collectors.toMap(Organization::getBaseId, identity())));

        return "projects";
    }

    @GetMapping("/{id}")
    public String viewPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Project project = projectRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());
        model.addAttribute("project", project);

        Optional.ofNullable(project.getBaseEntity().getBaseArea())
                .flatMap(baseArea -> areaRepository.findByBaseEntity_IdAndLanguage(baseArea.getId(), lang))
                .ifPresent(area -> model.addAttribute("area", area));

        Optional.ofNullable(project.getBaseEntity().getBaseOrganization())
                .flatMap(baseOrg -> organizationRepository.findByBaseEntity_IdAndLanguage(baseOrg.getId(), lang))
                .ifPresent(area -> model.addAttribute("organization", area));

        model.addAttribute("members", memberRepository.findPersonsByProject(project.getBaseId(), lang));
        return "project";
    }

}
