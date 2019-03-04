package by.maxi.puzzle.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class PuzzleGenerator {

    // https://www.codeproject.com/Articles/395453/Html5-Jigsaw-Puzzle
    private static final int[][] BASE_COORDS = new int[][]{
            { 0, 0, 35, 15, 37, 5 },
            { 37, 5, 40, 0, 38, -5 },
            { 38, -5, 20, -20, 50, -20 },
            { 50, -20, 80, -20, 62, -5 },
            { 62, -5, 60, 0, 63, 5 },
            { 63, 5, 65, 15, 100, 0 }
    };

    private static int BASE_SIZE = 100;


    public void render(Layout layout, String outputPath) throws IOException {
        int rows = layout.tiles.length;
        int cols = layout.tiles[0].length;

        int width = layout.tileSize * cols;
        int height = layout.tileSize * rows;

        double scale = (double) layout.tileSize / BASE_SIZE;
        double[][] coords = new double[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                coords[i][j] = BASE_COORDS[i][j] * scale;
            }
        }

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setColor(Color.BLACK);

        Random rand = new Random();
        Supplier<Color> colorSupplier = () -> {
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            return new Color(r, g, b);
        };

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Tile tile = layout.tiles[row][col];
                System.out.println(tile);

                double x0 = col * layout.tileSize;
                double y0 = row * layout.tileSize;

                GeneralPath path = new GeneralPath();
                path.moveTo(x0, y0);

                // top
                for (int i = 0; i < 6; i++) {
                    path.curveTo(x0 + coords[i][0], y0 + tile.topTab * coords[i][1],
                            x0 + coords[i][2], y0 + tile.topTab * coords[i][3],
                            x0 + coords[i][4], y0 + tile.topTab * coords[i][5]);

                }

                // right
                x0 += layout.tileSize;
                for (int i = 0; i < 6; i++) {
                    path.curveTo(x0 - tile.rightTab * coords[i][1], y0 + coords[i][0],
                            x0 - tile.rightTab * coords[i][3], y0 + coords[i][2],
                            x0 - tile.rightTab * coords[i][5], y0 + coords[i][4]);
                }

                // bottom
                y0 += layout.tileSize;
                for (int i = 0; i < 6; i++) {
                    path.curveTo(x0 - coords[i][0], y0 - tile.bottomTab * coords[i][1],
                            x0 - coords[i][2], y0 - tile.bottomTab * coords[i][3],
                            x0 - coords[i][4], y0 - tile.bottomTab * coords[i][5]);
                }

                // left
                x0 -= layout.tileSize;
                for (int i = 0; i < 6; i++) {
                    path.curveTo(x0 + tile.leftTab * coords[i][1], y0 - coords[i][0],
                            x0 + tile.leftTab * coords[i][3], y0 - coords[i][2],
                            x0 + tile.leftTab * coords[i][5], y0 - coords[i][4]);
                }

                g2d.setColor(colorSupplier.get());
                g2d.fill(path);

                g2d.setColor(Color.BLACK);
                g2d.draw(path);
            }
        }

        ImageIO.write(bi, "PNG", new File(outputPath));
    }

    public Layout generateLayout(int tileSize, int rows, int cols) {
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

                tiles[row][col] = new Tile(top, right, left, bottom);
            }
        }

        return new Layout(tileSize, tiles);
    }
}
