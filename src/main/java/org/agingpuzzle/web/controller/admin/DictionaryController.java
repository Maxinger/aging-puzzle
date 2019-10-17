package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.service.DictionaryService;
import org.agingpuzzle.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/{lang}/admin/dictionaries")
public class DictionaryController extends AbstractController {

    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping
    public String editPage(Model model) {
        model.addAttribute("csv", dictionaryService.getCSV());

        return "admin/dictionaries";
    }

    @PostMapping("/save")
    public String saveReview(@RequestParam String csv, BindingResult result) {

        result.rejectValue("csv", "Invalid CSV");
//        dictionaryService.updateFromCSV(csv);

        return "admin/dictionaries";
    }
}
