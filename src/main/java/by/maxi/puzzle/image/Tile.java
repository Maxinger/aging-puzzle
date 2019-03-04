package by.maxi.puzzle.image;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Tile {

    int topTab;
    int rightTab;
    int leftTab;
    int bottomTab;

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
