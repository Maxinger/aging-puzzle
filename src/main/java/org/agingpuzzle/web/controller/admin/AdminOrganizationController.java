package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.*;
import org.agingpuzzle.model.view.Membership;
import org.agingpuzzle.repo.*;
import org.agingpuzzle.service.ImageService;
import org.agingpuzzle.web.controller.AbstractController;
import org.agingpuzzle.web.form.OrganizationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private ImageService imageServce;

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

        organization.setName(orgForm.getName());
        organization.setDescription(orgForm.getDescription());;
        organization.setLocation(orgForm.getLocation());

        var parent = Optional.ofNullable(orgForm.getParentId())
                .flatMap(baseOrganizationRepository::findById)
                .orElse(null);
        organization.getBaseEntity().setParent(parent);

        Image image = Optional.ofNullable(organization.getImage()).orElse(new Image());
        image.setSource(orgForm.getImageSource());

        if (!file.isEmpty()) {
            imageServce.saveImage(file, image);
        }
        if (image.getPath() != null) {
            organization.setImage(image);
        }

        organization.setLinks(orgForm.getLinks());

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
