package org.agingpuzzle.web;

import java.util.Optional;
import java.util.regex.Pattern;

public final class WebUtils {

    public static final String[] SUPPORTED_LANGUAGES = {"en", "ru"};

    private static final Pattern LANGUAGE_PATTERN = Pattern.compile("^/([a-z]{2})");

    public static Optional<String> getLanguageFromUrl(String url) {
        var matcher = LANGUAGE_PATTERN.matcher(url);
        return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
    }

    public static String replaceLanguage(String url, String lang) {
        var matcher = LANGUAGE_PATTERN.matcher(url);
        return matcher.replaceFirst("/" + lang);
    }

}
