package org.agingpuzzle.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.Person;
import org.agingpuzzle.repo.MemberRepository;
import org.agingpuzzle.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/{lang}/persons")
public class PersonController extends AbstractController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping
    public String listPage(@PathVariable String lang, Model model) {
        model.addAttribute("persons", personRepository.findAllByLanguage(lang));
        return "persons";
    }

    @GetMapping("/{id}")
    public String viewPage(@PathVariable String lang,
                           @PathVariable Long id, Model model) {

        Person person = personRepository.findByBaseEntity_IdAndLanguage(id, lang).orElseThrow(notFound());
        model.addAttribute("person", person);
        model.addAttribute("organizations", memberRepository.findOrganizationsByPerson(person.getBaseId(), lang));
        model.addAttribute("projects", memberRepository.findProjectsByPerson(person.getBaseId(), lang));
        return "person";
    }
}
