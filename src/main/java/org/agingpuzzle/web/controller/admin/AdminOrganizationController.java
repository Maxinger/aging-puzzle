package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.BaseOrganization;
import org.agingpuzzle.model.Member;
import org.agingpuzzle.model.Organization;
import org.agingpuzzle.model.ToValidate;
import org.agingpuzzle.model.view.Membership;
import org.agingpuzzle.repo.*;
import org.agingpuzzle.service.ImageService;
import org.agingpuzzle.web.controller.AbstractController;
import org.agingpuzzle.web.form.OrganizationForm;
import org.agingpuzzle.web.mapper.OrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private ImageService imageServce;

    @Autowired
    private OrganizationMapper organizationMapper;

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
        model.addAttribute("organization", orgForm);

        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang));
        return "admin/organization";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Organization organization = organizationRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());

        model.addAttribute("organization", organizationMapper.organizationToForm(organization));

        model.addAttribute("organizations",
                organizationRepository.findAllByLanguageAndIdNot(lang, organization.getId()));

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
                                   @RequestParam MultipartFile file,
                                   @Validated(ToValidate.class) @ModelAttribute("organization") OrganizationForm orgForm,
                                   BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "admin/organization";
        }

        Organization organization;
        if (orgForm.isNew()) {
            BaseOrganization baseEntity = orgForm.getBaseId() == null
                    ? baseOrganizationRepository.save(new BaseOrganization())
                    : baseOrganizationRepository.findById(orgForm.getBaseId()).get();

            organization = new Organization();
            organization.setBaseEntity(baseEntity);
            organization.setLanguage(lang);
        } else {
            organization = organizationRepository.findById(orgForm.getId()).get();
        }

        organizationMapper.formToOrganization(orgForm, organization);

        imageServce.saveImage(organization, file, orgForm.getImageSource());

        baseOrganizationRepository.save(organization.getBaseEntity());
        organizationRepository.save(organization);
        log.info("Saved organization {} with id={}", orgForm.getName(), orgForm.getId());

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
