package by.maxi.puzzle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PuzzleGenerator {

    // https://www.codeproject.com/Articles/395453/Html5-Jigsaw-Puzzle

    private static final int[][] CURVY_COORDS = new int[][]{
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

            GeneralPath path = new GeneralPath();
            path.moveTo(x0, y0);

            for (int i = 0; i < 6; i++) {
                path.curveTo(x0 + CURVY_COORDS[i][0] * ratio, y0 + CURVY_COORDS[i][1] * ratio,
                        x0 + CURVY_COORDS[i][2] * ratio, y0 + CURVY_COORDS[i][3] * ratio,
                        x0 + CURVY_COORDS[i][4] * ratio, y0 + CURVY_COORDS[i][5] * ratio);

            }

            x0 += 150;
            for (int i = 0; i < 6; i++) {
                path.curveTo(x0 - CURVY_COORDS[i][1] * ratio, y0 - CURVY_COORDS[i][0] * ratio,
                        x0 - CURVY_COORDS[i][3] * ratio, y0 - CURVY_COORDS[i][2] * ratio,
                        x0 - CURVY_COORDS[i][5] * ratio, y0 - CURVY_COORDS[i][4] * ratio);
            }

            path.closePath();
            g2d.draw(path);
            g2d.fill(path);

            ImageIO.write(bi, "PNG", new File("f:\\work\\workspace\\puzzle\\output.png"));

        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }
}
