package org.agingpuzzle.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.Area;
import org.agingpuzzle.model.Organization;
import org.agingpuzzle.repo.MemberRepository;
import org.agingpuzzle.repo.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/{lang}/organizations")
public class OrganizationController extends AbstractController {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MemberRepository memberRepository;

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
        model.addAttribute("members", memberRepository.findPersonsByOrganization(organization.getBaseId(), lang));
        return "organization";
    }

}
