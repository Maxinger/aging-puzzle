package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.*;
import org.agingpuzzle.model.view.Membership;
import org.agingpuzzle.repo.*;
import org.agingpuzzle.web.controller.AbstractController;
import org.agingpuzzle.web.form.OrganizationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/{lang}/admin/organizations")
public class AdminOrganizationController extends AbstractController {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private BaseOrganizationRepository baseOrganizationRepository;

    @Autowired
    private BasePersonRepository basePersonRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public String listPage(@PathVariable String lang, Model model) {
        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang));

        addTranslations(lang, model, organizationRepository);
        return "admin/organizations";
    }

    @GetMapping("/new")
    public String newPage(@PathVariable String lang,
                          @RequestParam(required = false) Long baseId, Model model) {
        OrganizationForm orgForm = new OrganizationForm();
        orgForm.setBaseId(baseId);
        model.addAttribute("organization", new Organization());

        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang));
        return "admin/organization";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Organization organization = organizationRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());

        OrganizationForm orgForm = new OrganizationForm();
        orgForm.setId(organization.getId());
        orgForm.setBaseId(organization.getBaseId());
        Optional.ofNullable(organization.getBaseEntity().getParent()).map(BaseOrganization::getId).ifPresent(orgForm::setParentId);
        orgForm.setName(organization.getName());
        orgForm.setDescription(organization.getDescription());
        orgForm.setLocation(organization.getLocation());
        orgForm.setLinks(organization.getBaseEntity().getLinks());
        Optional.ofNullable(organization.getImage()).ifPresent(image -> {
            orgForm.setImagePath(image.getPath());
            orgForm.setImageSource(image.getSource());
        });
        model.addAttribute("organization", orgForm);

        var organizations = organizationRepository.findAllByLanguage(lang).stream()
                .filter(org -> !organization.getBaseId().equals(org.getBaseId()))
                .collect(Collectors.toList());
        model.addAttribute("organizations", organizations);

        var members = memberRepository.findPersonsByOrganization(organization.getBaseEntity().getId(), lang);
        var persons = members.stream()
                .map(Membership::getEntity)
                .collect(Collectors.toSet());
        var candidates = personRepository.findAllByLanguage(lang).stream()
                .filter(person -> !persons.contains(person))
                .collect(Collectors.toList());

        model.addAttribute("members", members);
        model.addAttribute("candidates", candidates);
        model.addAttribute("roles", Member.Role.getValues());
        return "admin/organization";
    }

    @PostMapping("/save")
    public String saveOrganization(@PathVariable String lang,
                                   @RequestParam(required = false) Long baseId,
                                   @Validated(ToValidate.class) Organization organization,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "admin/organization";
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

        return String.format("redirect:/%s/admin/organizations", lang);
    }

    @PostMapping("/delete")
    public String delete(@PathVariable String lang, @RequestParam Long id) {
        organizationRepository.deleteById(id);
        log.info("Deleted organization with id={}", id);

        return String.format("redirect:/%s/admin/organizations", lang);
    }

    @PostMapping("/{baseOrganizationId}/member")
    public String addMember(@PathVariable String lang,
                            @PathVariable Long baseOrganizationId,
                            @RequestParam(required = false) Long basePersonId,
                            @RequestParam(required = false) String role) {

        if (basePersonId != null && role != null) {
            Member member = new Member();
            member.setBaseOrganization(baseOrganizationRepository.getOne(baseOrganizationId));
            member.setBasePerson(basePersonRepository.getOne(basePersonId));
            member.setRole(Member.Role.valueOf(role));
            memberRepository.save(member);
            log.info("Added member with id={}", member.getId());
        }

        return String.format("redirect:/%s/admin/organizations/%d/edit", lang, baseOrganizationId);
    }

    @PostMapping("/{baseOrganizationId}/member/delete")
    public String deleteMember(@PathVariable String lang,
                               @PathVariable Long baseOrganizationId,
                               @RequestParam Long id) {
        memberRepository.deleteById(id);
        log.info("Deleted member with id={}", id);

        return String.format("redirect:/%s/admin/organizations/%d/edit", lang, baseOrganizationId);
    }


}
