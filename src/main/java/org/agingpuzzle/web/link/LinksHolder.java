package org.agingpuzzle.web.link;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LinksHolder {
    private List<Link> withText = new ArrayList<>();
    private List<Link> noText = new ArrayList<>();

    public void add(Link link) {
        if (link.getText() != null) {
            withText.add(link);
        } else {
            noText.add(link);
        }
    }
}
