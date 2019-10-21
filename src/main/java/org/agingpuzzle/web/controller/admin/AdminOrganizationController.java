package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.*;
import org.agingpuzzle.model.view.Membership;
import org.agingpuzzle.repo.*;
import org.agingpuzzle.service.DictionaryService;
import org.agingpuzzle.service.ImageService;
import org.agingpuzzle.web.controller.AbstractController;
import org.agingpuzzle.web.form.MemberForm;
import org.agingpuzzle.web.form.OrganizationForm;
import org.agingpuzzle.web.mapper.MemberMapper;
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

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping
    public String listPage(@PathVariable String lang, Model model) {
        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang));

        addTranslations(lang, model, organizationRepository);
        return "admin/organizations";
    }

    @GetMapping("/new")
    public String newPage(@PathVariable String lang,
                          @RequestParam(required = false) Long baseId, Model model) {

        BaseOrganization baseOrganization = baseOrganizationRepository.safeFindById(baseId);
        model.addAttribute("organization", organizationMapper.baseOrganizationToForm(baseOrganization));

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
        model.addAttribute("members", members);

        return "admin/organization";
    }

    @PostMapping("/save")
    public String saveOrganization(@PathVariable String lang,
                                   @RequestParam MultipartFile file,
                                   @Validated @ModelAttribute("organization") OrganizationForm orgForm,
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
//            member.setRole(Member.Role.valueOf(role));
            memberRepository.save(member);
            log.info("Added member with id={}", member.getId());
        }

        return String.format("redirect:/%s/admin/organizations/%d/edit", lang, baseOrganizationId);
    }

    private void initEditMemberPage(Model model, String lang, Long baseOrganizationId, Long basePersonId) {
        model.addAttribute("roles", dictionaryService.getDictionaryForType(DictionaryService.ROLE_TYPE, lang));
        model.addAttribute("candidates", personRepository.findCandidatesForOrganization(baseOrganizationId, lang));

        model.addAttribute("entityPath", "organizations");
        model.addAttribute("entityName", organizationRepository.findByBaseEntity_IdAndLanguage(baseOrganizationId, lang).get().getName());

        if (basePersonId != null) {
            Person person = personRepository.findByBaseEntity_IdAndLanguage(basePersonId, lang).get();
            model.addAttribute("memberName", person.getName());
        }
    }

    @GetMapping("/{baseOrganizationId}/members/new")
    public String newMemberPage(@PathVariable String lang,
                                @PathVariable Long baseOrganizationId, Model model) {

        MemberForm form = new MemberForm();
        form.setBaseEntityId(baseOrganizationId);
        model.addAttribute("member", form);

        initEditMemberPage(model, lang, baseOrganizationId, null);

        return "admin/member";
    }

    @GetMapping("/{baseOrganizationId}/members/{memberId}/edit")
    public String editMemberPage(@PathVariable String lang,
                                 @PathVariable Long baseOrganizationId, @PathVariable Long memberId, Model model) {

        Member member = memberRepository.findById(memberId).get();
        model.addAttribute("member", memberMapper.organizationMemberToForm(member));

        initEditMemberPage(model, lang, baseOrganizationId, member.getBasePerson().getId());

        return "admin/member";
    }


    @PostMapping("/{baseOrganizationId}/members/save")
    public String saveMember(@PathVariable String lang,
                             @Validated @ModelAttribute("member") MemberForm memberForm,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            initEditMemberPage(model, lang, memberForm.getBaseEntityId(), memberForm.getBasePersonId());
            return "admin/member";
        }

        Member member;
        if (memberForm.isNew()) {
            member = new Member();
        } else {
            member = memberRepository.findById(memberForm.getId()).get();
        }

        memberMapper.formToOrganizationMember(memberForm, member);

        memberRepository.save(member);
        log.info("Saved member with id={}", member.getId());

        return String.format("redirect:/%s/admin/organizations/%d/edit", lang, memberForm.getBaseEntityId());
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
