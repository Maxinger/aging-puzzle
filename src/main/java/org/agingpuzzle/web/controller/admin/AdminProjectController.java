package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.*;
import org.agingpuzzle.repo.*;
import org.agingpuzzle.service.DictionaryService;
import org.agingpuzzle.service.ImageService;
import org.agingpuzzle.web.controller.AbstractController;
import org.agingpuzzle.web.controller.Pagination;
import org.agingpuzzle.web.form.MemberForm;
import org.agingpuzzle.web.form.ProjectForm;
import org.agingpuzzle.web.mapper.MemberMapper;
import org.agingpuzzle.web.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Slf4j
@Controller
@RequestMapping("/{lang}/admin/projects")
public class AdminProjectController extends AbstractController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BaseProjectRepository baseProjectRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ImageService imageServce;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping
    public String listPage(@PathVariable String lang,
                           HttpServletRequest request, Model model) {

        int count = projectRepository.countByLanguage(lang);
        Pagination pagination = new Pagination(request, count);
        model.addAttribute("pagination", pagination);

        model.addAttribute("projects",
                projectRepository.findAllByLanguage(lang,  pagination.toPageable()));

        model.addAttribute("areas", areaRepository.findAllByLanguage(lang)
                .stream().collect(Collectors.toMap(Area::getBaseId, identity())));
        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang)
                .stream().collect(Collectors.toMap(Organization::getBaseId, identity())));

        addTranslations(lang, model, projectRepository);
        return "admin/projects";
    }

    private void initEditPage(Model model, String lang, Long baseId) {
        model.addAttribute("areas", areaRepository.findAllByLanguage(lang));
        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang));
        model.addAttribute("statuses", dictionaryService.getDictionaryForType(DictionaryService.STATUS_TYPE, lang));

        if (baseId != null) {
            model.addAttribute("members", memberRepository.findPersonsByProject(baseId, lang));
        }
    }

    @GetMapping("/new")
    public String newPage(@PathVariable String lang,
                          @RequestParam(required = false) Long baseId, Model model) {

        BaseProject baseProject = baseProjectRepository.safeFindById(baseId);
        model.addAttribute("project", projectMapper.baseProjectToForm(baseProject));

        initEditPage(model, lang, baseId);
        return "admin/project";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Project project = projectRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());

        model.addAttribute("project", projectMapper.projectToForm(project));

        initEditPage(model, lang, project.getBaseId());
        return "admin/project";
    }

    @PostMapping("/save")
    public String saveProject(@PathVariable String lang,
                              @RequestParam MultipartFile file,
                              @Validated @ModelAttribute("project") ProjectForm projectForm,
                              BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            initEditPage(model, lang, projectForm.getBaseId());
            return "admin/project";
        }

        Project project;
        if (projectForm.isNew()) {
            BaseProject baseEntity = projectForm.getBaseId() == null
                    ? baseProjectRepository.save(new BaseProject())
                    : baseProjectRepository.findById(projectForm.getBaseId()).get();

            project = new Project();
            project.setBaseEntity(baseEntity);
            project.setLanguage(lang);
        } else {
            project = projectRepository.findById(projectForm.getId()).get();
        }

        projectMapper.formToProject(projectForm, project);

        imageServce.saveImage(project, file, projectForm.getImageSource());

        baseProjectRepository.save(project.getBaseEntity());
        projectRepository.save(project);
        log.info("Saved project {} with id={}", project.getName(), project.getId());

        return String.format("redirect:/%s/admin/projects", lang);
    }

    @PostMapping("/delete")
    public String delete(@PathVariable String lang, @RequestParam Long id) {
        projectRepository.deleteById(id);
        log.info("Deleted project with id={}", id);

        return String.format("redirect:/%s/admin/projects", lang);
    }

    private void initEditMemberPage(Model model, String lang, Long baseProjectId, Long basePersonId) {
        model.addAttribute("roles", dictionaryService.getDictionaryForType(DictionaryService.ROLE_TYPE, lang));
        model.addAttribute("candidates", personRepository.findCandidatesForProject(baseProjectId, lang));

        model.addAttribute("entityPath", "projects");
        model.addAttribute("entityName", projectRepository.getName(baseProjectId, lang).orElseThrow(notFound()));

        if (basePersonId != null) {
            model.addAttribute("memberName", personRepository.getName(basePersonId, lang).orElseThrow(notFound()));
        }
    }

    @GetMapping("/{baseProjectId}/members/new")
    public String newMemberPage(@PathVariable String lang,
                                @PathVariable Long baseProjectId, Model model) {

        MemberForm form = new MemberForm();
        form.setBaseEntityId(baseProjectId);
        model.addAttribute("member", form);

        initEditMemberPage(model, lang, baseProjectId, null);
        return "admin/member";
    }

    @GetMapping("/{baseProjectId}/members/{memberId}/edit")
    public String editMemberPage(@PathVariable String lang,
                                 @PathVariable Long baseProjectId, @PathVariable Long memberId, Model model) {

        Member member = memberRepository.findById(memberId).get();
        model.addAttribute("member", memberMapper.projectMemberToForm(member));

        initEditMemberPage(model, lang, baseProjectId, member.getBasePerson().getId());
        return "admin/member";
    }


    @PostMapping("/{baseProjectId}/members/save")
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

        memberMapper.formToProjectMember(memberForm, member);

        memberRepository.save(member);
        log.info("Saved member with id={}", member.getId());

        return String.format("redirect:/%s/admin/projects/%d/edit", lang, memberForm.getBaseEntityId());
    }

    @PostMapping("/{baseProjectId}/member/delete")
    public String deleteMember(@PathVariable String lang,
                               @PathVariable Long baseProjectId,
                               @RequestParam Long id) {
        memberRepository.deleteById(id);
        log.info("Deleted member with id={}", id);

        return String.format("redirect:/%s/admin/projects/%d/edit", lang, baseProjectId);
    }
}
