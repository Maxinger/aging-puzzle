package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.BasePerson;
import org.agingpuzzle.model.Person;
import org.agingpuzzle.repo.BasePersonRepository;
import org.agingpuzzle.repo.PersonRepository;
import org.agingpuzzle.service.ImageService;
import org.agingpuzzle.web.controller.AbstractController;
import org.agingpuzzle.web.controller.Pagination;
import org.agingpuzzle.web.form.PersonForm;
import org.agingpuzzle.web.mapper.PersonMapper;
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
@RequestMapping("/{lang}/admin/persons")
public class AdminPersonController extends AbstractController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BasePersonRepository basePersonRepository;

    @Autowired
    private ImageService imageServce;

    @Autowired
    private PersonMapper personMapper;

    @GetMapping
    public String listPage(@PathVariable String lang,
                           HttpServletRequest request, Model model) {
        int count = personRepository.countByLanguage(lang);
        Pagination pagination = new Pagination(request, count);
        model.addAttribute("pagination", pagination);

        model.addAttribute("persons", personRepository.findAllByLanguage(lang, pagination.toPageable()));

        addTranslations(lang, model, personRepository);
        return "admin/persons";
    }

    @GetMapping("/new")
    public String newPage(@RequestParam(required = false) Long baseId,
                          @PathVariable String lang, Model model) {

        BasePerson basePerson = basePersonRepository.safeFindById(baseId);
        model.addAttribute("person", personMapper.basePersonToForm(basePerson));
        model.addAttribute("title", getMessage(lang, "admin.person.add"));

        return "admin/person";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Person person = personRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());
        model.addAttribute("person", personMapper.personToForm(person));
        model.addAttribute("title", person.getName());

        return "admin/person";
    }

    @PostMapping("/save")
    public String savePerson(@PathVariable String lang,
                             @RequestParam MultipartFile file,
                             @Validated @ModelAttribute("person") PersonForm personForm,
                             BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "admin/person";
        }

        Person person;
        if (personForm.isNew()) {
            BasePerson baseEntity = personForm.getBaseId() == null
                    ? basePersonRepository.save(new BasePerson())
                    : basePersonRepository.findById(personForm.getBaseId()).get();

            person = new Person();
            person.setBaseEntity(baseEntity);
            person.setLanguage(lang);
        } else {
            person = personRepository.findById(personForm.getId()).get();
        }

        personMapper.formToPerson(personForm, person);

        imageServce.saveImage(person, file, personForm.getImageSource());

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
