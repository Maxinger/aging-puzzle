package org.agingpuzzle.web.link;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Component
public class LinkVisualizer {

    public static final String ICONS_FILE = "/icons.csv";
    public static final String DEFAULT_KEY = "default";

    /**
     * Supports following patterns:
     * url
     * key#url
     * key_lang#url
     */
    private static final Pattern LINK_PATTERN = Pattern.compile("((\\w+?)(_(\\w+))?#)?(\\[(.+)\\])?(.+)");

    private String defaultImage;
    private Map<String, String> imagesByPattern = new HashMap<>();
    private Map<String, String> imagesByKey = new HashMap<>();

    @PostConstruct
    private void init() {
        Scanner scanner = new Scanner(getClass().getResourceAsStream(ICONS_FILE));
        scanner.nextLine();
        while (scanner.hasNext()) {
            String[] values = scanner.nextLine().split(",");
            String type = values[0];
            String key = values[1];
            String image = values[2];
            if ("key".equals(type)) {
                imagesByKey.put(key, image);
            } else if ("pattern".equals(type)) {
                imagesByPattern.put(key, image);
            } else {
                log.warn("Unsupported icon type: " + type);
            }
        }
        defaultImage = imagesByKey.get(DEFAULT_KEY);
        scanner.close();
    }

    public LinksHolder getLinksHolder(String links, String language) {
        LinksHolder holder = new LinksHolder();
        if (links != null) {
            Arrays.stream(links.split("\n"))
                    .map(String::trim)
                    .map(link -> getLink(link, language))
                    .filter(Objects::nonNull)
                    .forEach(holder::add);
        }
        return holder;
    }

    public Link getLink(String link, String language) {
        var matcher = LINK_PATTERN.matcher(link);
        if (matcher.find()) {
            String key = matcher.group(2);
            String lang = matcher.group(4);
            String text = matcher.group(6);
            String url = matcher.group(7);

            if (key == null) {
                String image = imagesByPattern.keySet().stream()
                        .filter(url::contains)
                        .findFirst()
                        .map(imagesByPattern::get)
                        .orElse(defaultImage);
                return new Link(url, image, text);
            } else {
                if (lang != null && !lang.equals(language)) {
                    return null;
                }
                String image = imagesByKey.getOrDefault(key, defaultImage);
                return new Link(url, image, text);
            }
        } else {
            log.error("Invalid link: " + link);
            return null;
        }
    }
}
