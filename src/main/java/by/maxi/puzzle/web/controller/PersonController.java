package by.maxi.puzzle.web.controller;

import by.maxi.puzzle.model.BasePerson;
import by.maxi.puzzle.model.Person;
import by.maxi.puzzle.model.ToValidate;
import by.maxi.puzzle.repo.BasePersonRepository;
import by.maxi.puzzle.repo.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/{lang}/persons")
public class PersonController extends AbstractController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BasePersonRepository basePersonRepository;

    @GetMapping
    public String listPage(@PathVariable String lang, Model model) {
        model.addAttribute("persons", personRepository.findAllByLanguage(lang));

        addTranslations(lang, model, personRepository);
        return "persons";
    }

    @GetMapping("/new")
    public String newPage(@RequestParam(required = false) Long baseId, Model model) {
        model.addAttribute("baseId", baseId);
        model.addAttribute("person", new Person());
        return "person";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Person person = personRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());
        model.addAttribute("person", person);
        return "person";
    }

    @PostMapping("/save")
    public String savePerson(@PathVariable String lang,
                             @RequestParam(required = false) Long baseId,
                             @Validated(ToValidate.class) Person person,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "person";
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

        personRepository.save(person);
        log.info("Saved person {} with id={}", person.getName(), person.getId());

        return String.format("redirect:/%s/persons", lang);
    }

    @PostMapping("/delete")
    public String delete(@PathVariable String lang, @RequestParam Long id) {
        personRepository.deleteById(id);
        log.info("Deleted person with id={}", id);

        return String.format("redirect:/%s/persons", lang);
    }
}
