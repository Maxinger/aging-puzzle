package by.maxi.puzzle.image;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Layout {

    int tileSize;
    Tile[][] tiles;

    public List<Area> getAreas() {
        List<Area> areas = new ArrayList<>();

        float padding = 0.1f;

        int rows = tiles.length;
        int cols = tiles[0].length;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (tiles[row][col] != null) {
                    int x1 = Math.round((col + padding) * tileSize);
                    int y1 = Math.round((row + padding) * tileSize);

                    int x2 = Math.round((col + 1 - padding) * tileSize);
                    int y2 = Math.round((row + 1 - padding) * tileSize);

                    areas.add(new Area(x1, y1, x2, y2));
                }
            }
        }

        return areas;
    }

}
