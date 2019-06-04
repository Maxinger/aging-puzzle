package org.agingpuzzle.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.Area;
import org.agingpuzzle.model.Organization;
import org.agingpuzzle.model.Project;
import org.agingpuzzle.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
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

    @GetMapping
    public String listPage(@PathVariable String lang,
                           @RequestParam(name="areaId", required = false) Long baseAreaId,
                           Model model) {

        if (baseAreaId != null) {
            model.addAttribute("projects", projectRepository.findAllByArea(baseAreaId, lang));

            Area area = areaRepository.findByBaseEntity_IdAndLanguage(baseAreaId, lang).orElseThrow(notFound());
            model.addAttribute("areas", Map.of(baseAreaId, area));

            model.addAttribute("baseAreaId", baseAreaId);
        } else {
            model.addAttribute("projects", projectRepository.findAllByLanguage(lang));

            model.addAttribute("areas", areaRepository.findAllByLanguage(lang)
                    .stream().collect(Collectors.toMap(Area::getBaseId, identity())));
        }

        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang)
                .stream().collect(Collectors.toMap(Organization::getBaseId, identity())));

        return "projects";
    }

    @GetMapping("/{id}")
    public String viewPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Project project = projectRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());
        model.addAttribute("project", project);
//        model.addAttribute("members", memberRepository.findPersonsByOrganization(organization.getBaseId(), lang));
        return "organization";
    }

}
