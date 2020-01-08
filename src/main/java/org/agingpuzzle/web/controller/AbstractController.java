package org.agingpuzzle.web.controller;

import org.agingpuzzle.repo.TranslatableRepository;
import org.agingpuzzle.web.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class AbstractController {

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    protected void addTranslations(String lang, Model model, TranslatableRepository translatableRepository) {
        Map<String, Set<Long>> translations = new LinkedHashMap<>();
        for (String language : LanguageUtils.getSupportedLanguages()) {
            if (!language.equals(lang)) {
                translations.put(language, translatableRepository.findAllIdsByLanguage(language));
            }
        }
        model.addAttribute("translations", translations);
    }

    protected Supplier<ResponseStatusException> notFound() {
        return () -> new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    protected String getMessage(String lang, String key, Object... params) {
        return messageSource.getMessage(key, params, LanguageUtils.getLocale(lang));
    }
}
