package org.agingpuzzle.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.Area;
import org.agingpuzzle.model.BaseOrganization;
import org.agingpuzzle.model.Organization;
import org.agingpuzzle.repo.AreaRepository;
import org.agingpuzzle.repo.MemberRepository;
import org.agingpuzzle.repo.OrganizationRepository;
import org.agingpuzzle.repo.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Slf4j
@Controller
@RequestMapping("/{lang}/organizations")
public class OrganizationController extends AbstractController {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AreaRepository areaRepository;

    @GetMapping
    public String listPage(@PathVariable String lang, Model model) {
        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang));
        return "organizations";
    }

    @GetMapping("/{id}")
    public String viewPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Organization organization = organizationRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());
        model.addAttribute("organization", organization);
        Optional.ofNullable(organization.getBaseEntity().getParent())
                .map(BaseOrganization::getId)
                .flatMap(parentId -> organizationRepository.findByBaseEntity_IdAndLanguage(parentId, lang))
                .ifPresent(parent -> model.addAttribute("parent", parent));
        model.addAttribute("members", memberRepository.findPersonsByOrganization(organization.getBaseId(), lang));
        model.addAttribute("organizations", organizationRepository.findAllByLanguageAndBaseEntityParent(lang, organization.getBaseEntity()));
        model.addAttribute("projects", projectRepository.findAllByOrganization(organization.getBaseEntity().getId(), lang));
        model.addAttribute("areas", areaRepository.findAllByLanguage(lang)
                .stream().collect(Collectors.toMap(Area::getBaseId, identity())));

        return "organization";
    }

}
