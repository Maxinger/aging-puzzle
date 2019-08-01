package org.agingpuzzle.web;

import java.util.Map;
import java.util.Optional;

public class UrlVisualizer {

    private Map<String, String> images = Map.of(
            "linkedin.com", "linkedin" // <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/"                 title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/"                 title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
    );

    public Optional<String> getImage(String url) {
        return images.keySet().stream().filter(s -> url.contains(s)).findFirst();
    }
}
