package by.maxi.puzzle.web.controller;

import by.maxi.puzzle.model.BaseOrganization;
import by.maxi.puzzle.model.Organization;
import by.maxi.puzzle.repo.BaseOrganizationRepository;
import by.maxi.puzzle.repo.OrganizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

@Slf4j
@Controller
@RequestMapping("/{lang}/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private BaseOrganizationRepository baseOrganizationRepository;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String listPage(@PathVariable String lang, Model model) {
        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang));
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
                                   @Validated(Organization.ToValidate.class) Organization organization,
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
        log.info("Deleted organization with id={}", id);
        organizationRepository.deleteById(id);

        return String.format("redirect:/%s/organizations", lang);
    }

    private Supplier<ResponseStatusException> notFound() {
        return () -> new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
