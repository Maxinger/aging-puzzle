package org.agingpuzzle.web;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class LanguageUtils {

    public static final String DEFAULT_LANGUAGE = "en";

    private static final Map<String, Locale> SUPPORTED_LANGUAGES = new TreeMap<>();

    static {
        SUPPORTED_LANGUAGES.put("en", Locale.US);
        SUPPORTED_LANGUAGES.put("ru", new Locale("ru", "RU"));
    }

    private static List<String> LANG_URLS = LanguageUtils.getSupportedLanguages().stream()
            .map(s -> "/" + s)
            .collect(Collectors.toList());

    private static final Pattern LANGUAGE_PATTERN = Pattern.compile("^/([a-z]{2})");

    public static Set<String> getSupportedLanguages() {
        return SUPPORTED_LANGUAGES.keySet();
    }

    public static Locale getLocale(String lang) {
        return SUPPORTED_LANGUAGES.get(lang);
    }

    public static Optional<String> getLanguageFromUrl(String url) {
        var matcher = LANGUAGE_PATTERN.matcher(url);
        return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
    }

    public static String replaceLanguage(String url, String lang) {
        var matcher = LANGUAGE_PATTERN.matcher(url);
        return matcher.replaceFirst("/" + lang);
    }

    public static boolean isLanguageUrl(String url) {
        return LANG_URLS.stream().anyMatch(s -> url.startsWith(s));
    }

}
