package org.agingpuzzle.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class LinkVisualizer {

    public static final String ICONS_FILE = "/icons.csv";
    public static final String DEFAULT_KEY = "default";
    public static final String HOME_KEY = "home";
    public static final String HOME_PREFIX = "home:";

    private String defaultImage;
    private String homeImage;
    private Map<String, String> images = new HashMap<>();

    @PostConstruct
    private void init() {
        Scanner scanner = new Scanner(getClass().getResourceAsStream(ICONS_FILE));
        scanner.nextLine();
        while (scanner.hasNext()) {
            String[] values = scanner.nextLine().split(",");
            String key = values[0];
            String value = values[1];
            switch (key) {
                case DEFAULT_KEY:
                    defaultImage = value;
                    break;
                case HOME_KEY:
                    homeImage = value;
                    break;
                default:
                    images.put(key, value);
            }
        }
        scanner.close();
    }


    @Getter
    @AllArgsConstructor
    public class Link {
        private String url;
        private String image;
    }

    public Link getLink(String url) {
        if (url.startsWith(HOME_PREFIX)) {
            return new Link(url.substring(HOME_PREFIX.length()), homeImage);
        } else {
            String image = images.keySet().stream()
                    .filter(url::contains)
                    .findFirst()
                    .map(images::get)
                    .orElse(defaultImage);
            return new Link(url, image);
        }
    }
}
