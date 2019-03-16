package by.maxi.puzzle.image;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Area {

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public String getCoords() {
        return String.format("%d,%d,%d,%d", x1, y1, x2, y2);
    }
}
