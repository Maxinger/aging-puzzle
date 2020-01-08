package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.*;
import org.agingpuzzle.repo.*;
import org.agingpuzzle.service.DictionaryService;
import org.agingpuzzle.service.ImageService;
import org.agingpuzzle.web.controller.AbstractController;
import org.agingpuzzle.web.controller.Pagination;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
    public String listPage(@PathVariable String lang,
                           HttpServletRequest request, Model model) {

        int count = organizationRepository.countByLanguage(lang);
        Pagination pagination = new Pagination(request, count);
        model.addAttribute("pagination", pagination);

        model.addAttribute("organizations",
                organizationRepository.findAllByLanguage(lang, pagination.toPageable()));

        addTranslations(lang, model, organizationRepository);
        return "admin/organizations";
    }

    private void initEditPage(Model model, String lang, Long baseId) {
        if (baseId == null) {
            model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang));
        } else {
            model.addAttribute("organizations", organizationRepository.findAllByLanguageAndBaseEntity_IdNot(lang, baseId));
            model.addAttribute("members", memberRepository.findPersonsByOrganization(baseId, lang));
        }
    }

    @GetMapping("/new")
    public String newPage(@PathVariable String lang,
                          @RequestParam(required = false) Long baseId, Model model) {

        BaseOrganization baseOrganization = baseOrganizationRepository.safeFindById(baseId);
        model.addAttribute("organization", organizationMapper.baseOrganizationToForm(baseOrganization));
        model.addAttribute("title", getMessage(lang, "admin.organization.add"));

        initEditPage(model, lang, baseId);
        return "admin/organization";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Organization organization = organizationRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());

        model.addAttribute("organization", organizationMapper.organizationToForm(organization));
        model.addAttribute("title", organization.getName());

        initEditPage(model, lang, organization.getBaseId());
        return "admin/organization";
    }

    @PostMapping("/save")
    public String saveOrganization(@PathVariable String lang,
                                   @RequestParam MultipartFile file,
                                   @Validated @ModelAttribute("organization") OrganizationForm orgForm,
                                   BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            initEditPage(model, lang, orgForm.getBaseId());
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

    private void initEditMemberPage(Model model, String lang, Long baseOrganizationId, Long basePersonId) {
        model.addAttribute("roles", dictionaryService.getDictionaryForType(DictionaryService.ROLE_TYPE, lang));
        model.addAttribute("candidates", personRepository.findCandidatesForOrganization(baseOrganizationId, lang));

        model.addAttribute("entityPath", "organizations");
        model.addAttribute("entityName", organizationRepository.getName(baseOrganizationId, lang).orElseThrow(notFound()));

        if (basePersonId != null) {
            model.addAttribute("memberName", personRepository.getName(basePersonId, lang).orElseThrow(notFound()));
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
