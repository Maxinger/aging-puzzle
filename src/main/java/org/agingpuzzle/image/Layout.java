package org.agingpuzzle.image;

import org.agingpuzzle.model.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.IntSupplier;

public class Layout {

    final Optional<Tile>[][] tiles;
    final int tileSize;

    public Layout(Optional<Area>[][] areas, int tileSize) {
        this.tileSize = tileSize;

        int rows = areas.length;
        int cols = areas[0].length;
        this.tiles = new Optional[rows][cols];

        int[] tabs = {1, -1};
        Random rand = new Random();
        IntSupplier tabSupplier = () -> tabs[rand.nextInt(2)];


        Tile[][] tiles = new Tile[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int top = row == 0 ? 0 : -tiles[row - 1][col].bottomTab;
                int left = col == 0 ? 0 : -tiles[row][col - 1].rightTab;
                int bottom = row == rows - 1 ? 0 : tabSupplier.getAsInt();

                int right = 0;
                if (col < cols - 1) {
                    if (top >= 0 && right >= 0 && bottom >= 0) {
                        right = -1;
                    } else if (top <= 0 && right <= 0 && bottom <= 0) {
                        right = 1;
                    } else {
                        right = tabSupplier.getAsInt();
                    }
                }

                Optional<Area> area = areas[row][col];
                Tile tile = new Tile(area.orElse(null), top, right, left, bottom);
                tiles[row][col] = tile;
                this.tiles[row][col] = Optional.ofNullable(area.isPresent() ? tile : null);
            }
        }
    }

    public <T> List<T> getPuzzleView(BiFunction<Area, String, T> func) {
        List<T> result = new ArrayList<>();

        float padding = 0.1f;

        int rows = tiles.length;
        int cols = tiles[0].length;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Optional<Tile> tile = tiles[row][col];
                if (tile.isPresent()) {
                    int x1 = Math.round((col + padding) * tileSize);
                    int y1 = Math.round((row + padding) * tileSize);

                    int x2 = Math.round((col + 1 - padding) * tileSize);
                    int y2 = Math.round((row + 1 - padding) * tileSize);

                    String coords = String.format("%d,%d,%d,%d", x1, y1, x2, y2);

                    result.add(func.apply(tile.get().area, coords));
                }
            }
        }

        return result;
    }

}
