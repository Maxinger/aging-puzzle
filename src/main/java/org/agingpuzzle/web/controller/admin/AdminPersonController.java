package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.BasePerson;
import org.agingpuzzle.model.Image;
import org.agingpuzzle.model.Person;
import org.agingpuzzle.model.ToValidate;
import org.agingpuzzle.repo.BasePersonRepository;
import org.agingpuzzle.repo.PersonRepository;
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
@RequestMapping("/{lang}/admin/persons")
public class AdminPersonController extends AbstractController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BasePersonRepository basePersonRepository;

    @Autowired
    private ImageService imageServce;

    @GetMapping
    public String listPage(@PathVariable String lang, Model model) {
        model.addAttribute("persons", personRepository.findAllByLanguage(lang));

        addTranslations(lang, model, personRepository);
        return "admin/persons";
    }

    @GetMapping("/new")
    public String newPage(@RequestParam(required = false) Long baseId, Model model) {
        model.addAttribute("baseId", baseId);
        model.addAttribute("person", new Person());

        Optional<BasePerson> basePerson = Optional.ofNullable(baseId)
                .flatMap(basePersonRepository::findById);

        model.addAttribute("image", basePerson.map(BasePerson::getImage).orElse(new Image()));
        model.addAttribute("links", basePerson.map(BasePerson::getLinks).orElse(null));

        return "admin/person";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Person person = personRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());
        model.addAttribute("person", person);
        model.addAttribute("image", Optional.ofNullable(person.getImage()).orElse(new Image()));
        model.addAttribute("links", person.getLinks());
        return "admin/person";
    }

    @PostMapping("/save")
    public String savePerson(@PathVariable String lang,
                             @RequestParam(required = false) Long baseId,
                             @RequestParam MultipartFile file,
                             @RequestParam(required = false) String personLinks,
                             @Validated(ToValidate.class) Person person,
                             BindingResult personResult,
                             @Validated(ToValidate.class) Image image,
                             BindingResult imageResult) throws IOException {
        if (personResult.hasErrors() || imageResult.hasErrors()) {
            return "admin/person";
        }

        if (person.isNew()) {
            BasePerson baseEntity = baseId == null
                    ? basePersonRepository.save(new BasePerson())
                    : basePersonRepository.findById(baseId).get();

            person.setBaseEntity(baseEntity);
            person.setLanguage(lang);
        } else {
            Person updated = person;
            person = personRepository.findById(person.getId()).get();
            person.setName(updated.getName());
            person.setDescription(updated.getDescription());
        }

        Image updated = image;
        image = Optional.ofNullable(person.getImage()).orElse(new Image());
        image.setSource(updated.getSource());

        if (!file.isEmpty()) {
            imageServce.saveImage(file, image);
        }
        if (image.getPath() != null) {
            person.setImage(image);
        }

        person.setLinks(personLinks);

        basePersonRepository.save(person.getBaseEntity());
        personRepository.save(person);
        log.info("Saved person {} with id={}", person.getName(), person.getId());

        return String.format("redirect:/%s/admin/persons", lang);
    }

    @PostMapping("/delete")
    public String delete(@PathVariable String lang, @RequestParam Long id) {
        personRepository.deleteById(id);
        log.info("Deleted person with id={}", id);

        return String.format("redirect:/%s/admin/persons", lang);
    }
}
