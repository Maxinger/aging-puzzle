package by.maxi.puzzle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PuzzleGenerator {

    // https://www.codeproject.com/Articles/395453/Html5-Jigsaw-Puzzle

    private static int BASE_SIZE = 100;

    private static final int[][] BASE_COORDS = new int[][]{
            { 0, 0, 35, 15, 37, 5 },
            { 37, 5, 40, 0, 38, -5 },
            { 38, -5, 20, -20, 50, -20 },
            { 50, -20, 80, -20, 62, -5 },
            { 62, -5, 60, 0, 63, 5 },
            { 63, 5, 65, 15, 100, 0 }
    };


    public static void main(String[] args) {
        try {
            int width = 600, height = 400;

            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bi.createGraphics();

            g2d.setColor(Color.BLACK);

            double x0 = 200;
            double y0 = 200;
            double ratio = 1;
            int topTab = 1;
            int rightTab = 1;
            int bottomTab = 1;
            int leftTab = -1;

            double tileSize = BASE_SIZE * ratio;
            double[][] coords = new double[6][6];
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    coords[i][j] = BASE_COORDS[i][j] * ratio;
                }
            }

            GeneralPath path = new GeneralPath();
            path.moveTo(x0, y0);

            // top
            for (int i = 0; i < 6; i++) {
                path.curveTo(x0 + coords[i][0], y0 + topTab * coords[i][1],
                        x0 + coords[i][2], y0 + topTab * coords[i][3],
                        x0 + coords[i][4], y0 + topTab * coords[i][5]);

            }

            // right
            x0 += tileSize;
            for (int i = 0; i < 6; i++) {
                path.curveTo(x0 - rightTab * coords[i][1], y0 + coords[i][0],
                        x0 - rightTab * coords[i][3], y0 + coords[i][2],
                        x0 - rightTab * coords[i][5], y0 + coords[i][4]);
            }

            // bottom
            y0 += tileSize;
            for (int i = 0; i < 6; i++) {
                path.curveTo(x0 - coords[i][0], y0 + bottomTab * coords[i][1],
                        x0 - coords[i][2], y0 + bottomTab * coords[i][3],
                        x0 - coords[i][4], y0 + bottomTab * coords[i][5]);
            }

            // left
            x0 -= tileSize;
            for (int i = 0; i < 6; i++) {
                path.curveTo(x0 + leftTab * coords[i][1], y0 - coords[i][0],
                        x0 + leftTab * coords[i][3], y0 - coords[i][2],
                        x0 + leftTab * coords[i][5], y0 - coords[i][4]);
            }

            g2d.draw(path);
            g2d.fill(path);

            ImageIO.write(bi, "PNG", new File("output.png"));

        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }
}
