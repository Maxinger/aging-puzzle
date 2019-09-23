package org.agingpuzzle.image;

import org.agingpuzzle.model.Area;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.agingpuzzle.model.Image;

@RequiredArgsConstructor
@Getter
public class Tile {

    final Area area;
    final int topTab;
    final int rightTab;
    final int leftTab;
    final int bottomTab;

    public String getCode() {
        return area.getName().substring(0, 2);
    }

    public Image getImage() {
        return area.getImage();
    }

    @Override
    public String toString() {
        return "Tile{" +
                "topTab=" + topTab +
                ", rightTab=" + rightTab +
                ", leftTab=" + leftTab +
                ", bottomTab=" + bottomTab +
                '}';
    }
}
