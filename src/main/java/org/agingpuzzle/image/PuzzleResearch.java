package org.agingpuzzle.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

@Component
public class PuzzleResearch {

    @Value("${image.dir}")
    private String imageDir;

    // https://www.codeproject.com/Articles/395453/Html5-Jigsaw-Puzzle
    private static final int[][] BASE_COORDS = new int[][]{
            {0, 0, 35, 15, 37, 5},
            {37, 5, 40, 0, 38, -5},
            {38, -5, 20, -20, 50, -20},
            {50, -20, 80, -20, 62, -5},
            {62, -5, 60, 0, 63, 5},
            {63, 5, 65, 15, 100, 0}
    };

    private Random rand = new Random();

    public static void main(String[] args) throws IOException {
        File outputFile = new File("research.png");
        int size = 300;
        double scale = 1.5;

        double[][] coords = new double[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                coords[i][j] = BASE_COORDS[i][j] * scale;
            }
        }

        BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();


        double x0 = (size - scale * 100) / 2;
        double y0 = x0;
        int tab = -1;

        GeneralPath path = new GeneralPath();
        path.moveTo(x0, y0);

        for (int i = 0; i < 6; i++) {
            path.curveTo(x0 + coords[i][0], y0 + tab * coords[i][1],
                    x0 + coords[i][2], y0 + tab * coords[i][3],
                    x0 + coords[i][4], y0 + tab * coords[i][5]);
        }

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, size, size);
        g2d.setColor(Color.BLACK);
        g2d.draw(path);
        ImageIO.write(bi, "PNG", outputFile);

    }

}
