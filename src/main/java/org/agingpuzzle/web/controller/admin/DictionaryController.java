package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.service.DictionaryService;
import org.agingpuzzle.web.controller.AbstractController;
import org.agingpuzzle.web.form.DictionaryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/{lang}/admin/dictionaries")
public class DictionaryController extends AbstractController {

    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping
    public String editPage(Model model) {
        model.addAttribute("dictionaries", new DictionaryForm(dictionaryService.getCSV()));

        return "admin/dictionaries";
    }

    @PostMapping("/save")
    public String saveReview(@ModelAttribute("dictionaries") DictionaryForm dictionaryForm,
                             BindingResult result) {

        try {
            dictionaryService.updateFromCSV(dictionaryForm.getCsv());
        } catch (Exception e) {
            log.error("Failed to process dictionaries CSV", e);
            result.rejectValue("csv", "dictionaries.invalid", "Invalid CSV");
        }

        return "admin/dictionaries";
    }
}
