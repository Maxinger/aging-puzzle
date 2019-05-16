package org.agingpuzzle.web;

import java.util.regex.Pattern;

public final class WebUtils {

    public static final String[] SUPPORTED_LANGUAGES = {"en", "ru"};

    private static final Pattern LANGUAGE_PATTERN = Pattern.compile("^/([a-z]{2})/");

    public static String getLanguageFromUrl(String url) {
        var matcher = LANGUAGE_PATTERN.matcher(url);
        return matcher.find() ? matcher.group(1) : SUPPORTED_LANGUAGES[0];
    }

}
