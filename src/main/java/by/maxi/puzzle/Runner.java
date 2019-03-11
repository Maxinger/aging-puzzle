package by.maxi.puzzle;

import by.maxi.puzzle.image.Layout;
import by.maxi.puzzle.image.PuzzleGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Runner {

    public static void main(String[] args) throws IOException {
        PuzzleGenerator generator = new PuzzleGenerator();

        int[][] empty = {
                {0, 2}, {0, 3},
                {1, 1}, {1, 2}, {1, 3},
                {2, 1}, {2, 2}, {2, 3}, {2, 4},
                {3, 2}, {3, 3}
        };
        Layout layout = generator.generateLayout(200, 4, 6, empty);
        generator.render(layout, "output.png");
    }
}