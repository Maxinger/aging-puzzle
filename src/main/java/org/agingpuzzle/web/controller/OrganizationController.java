package org.agingpuzzle.web.controller;

import org.agingpuzzle.model.BaseOrganization;
import org.agingpuzzle.model.Organization;
import org.agingpuzzle.model.ToValidate;
import org.agingpuzzle.repo.BaseOrganizationRepository;
import org.agingpuzzle.repo.OrganizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/{lang}/organizations")
public class OrganizationController extends AbstractController {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private BaseOrganizationRepository baseOrganizationRepository;

    @GetMapping
    public String listPage(@PathVariable String lang, Model model) {
        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang));

        addTranslations(lang, model, organizationRepository);
        return "organizations";
    }

    @GetMapping("/new")
    public String newPage(@RequestParam(required = false) Long baseId, Model model) {
        model.addAttribute("baseId", baseId);
        model.addAttribute("organization", new Organization());
        return "organization";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Organization organization = organizationRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());
        model.addAttribute("organization", organization);
        return "organization";
    }

    @PostMapping("/save")
    public String saveOrganization(@PathVariable String lang,
                                   @RequestParam(required = false) Long baseId,
                                   @Validated(ToValidate.class) Organization organization,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "organization";
        }

        if (organization.isNew()) {
            BaseOrganization baseEntity = baseId == null
                    ? baseOrganizationRepository.save(new BaseOrganization())
                    : baseOrganizationRepository.findById(baseId).get();

            organization.setBaseEntity(baseEntity);
            organization.setLanguage(lang);
        } else {
            Organization updated = organization;
            organization = organizationRepository.findById(organization.getId()).get();
            organization.setName(updated.getName());
            organization.setDescription(updated.getDescription());
            organization.setLocation(updated.getLocation());
        }

        organizationRepository.save(organization);
        log.info("Saved organization {} with id={}", organization.getName(), organization.getId());

        return String.format("redirect:/%s/organizations", lang);
    }

    @PostMapping("/delete")
    public String delete(@PathVariable String lang, @RequestParam Long id) {
        organizationRepository.deleteById(id);
        log.info("Deleted organization with id={}", id);

        return String.format("redirect:/%s/organizations", lang);
    }
}
