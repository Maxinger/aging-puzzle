package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.*;
import org.agingpuzzle.repo.AreaRepository;
import org.agingpuzzle.repo.BaseProjectRepository;
import org.agingpuzzle.repo.OrganizationRepository;
import org.agingpuzzle.repo.ProjectRepository;
import org.agingpuzzle.service.ImageService;
import org.agingpuzzle.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

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
    private ImageService imageServce;

    @GetMapping
    public String listPage(@PathVariable String lang, Model model) {
        model.addAttribute("projects", projectRepository.findAllByLanguage(lang));

        addTranslations(lang, model, projectRepository);
        return "admin/projects";
    }

    @GetMapping("/new")
    public String newPage(@PathVariable String lang,
                          @RequestParam(required = false) Long baseId, Model model) {
        model.addAttribute("baseId", baseId);
        model.addAttribute("project", new Project());

        Optional<BaseProject> baseProject = Optional.ofNullable(baseId)
                .flatMap(baseProjectRepository::findById);

        model.addAttribute("image", baseProject.map(BaseProject::getImage).orElse(new Image()));
        model.addAttribute("links", baseProject.map(BaseProject::getLinks).orElse(null));

        model.addAttribute("areas", areaRepository.findAllByLanguage(lang));
        model.addAttribute("organizations", organizationRepository.findAllByLanguage(lang));

        return "admin/project";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Project project = projectRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());
        model.addAttribute("project", project);
        model.addAttribute("image", Optional.ofNullable(project.getImage()).orElse(new Image()));
        model.addAttribute("links", project.getLinks());
        return "admin/project";
    }

    @PostMapping("/save")
    public String saveProject(@PathVariable String lang,
                              @RequestParam(required = false) Long baseId,
                              @RequestParam MultipartFile file,
                              @RequestParam(required = false) String projectLinks,
                              @Validated(ToValidate.class) Project project,
                              BindingResult projectResult,
                              @Validated(ToValidate.class) Image image,
                              BindingResult imageResult) throws IOException {
        if (projectResult.hasErrors() || imageResult.hasErrors()) {
            return "admin/project";
        }

        if (project.isNew()) {
            BaseProject baseEntity = baseId == null
                    ? baseProjectRepository.save(new BaseProject())
                    : baseProjectRepository.findById(baseId).get();

            project.setBaseEntity(baseEntity);
            project.setLanguage(lang);
        } else {
            Project updated = project;
            project = projectRepository.findById(project.getId()).get();
            project.setName(updated.getName());
            project.setDescription(updated.getDescription());
        }

        Image updated = image;
        image = Optional.ofNullable(project.getImage()).orElse(new Image());
        image.setSource(updated.getSource());

        if (!file.isEmpty()) {
            imageServce.saveImage(file, image);
        }
        if (image.getPath() != null) {
            project.setImage(image);
        }

        project.setLinks(projectLinks);

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
}
