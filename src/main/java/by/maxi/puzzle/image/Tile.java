package by.maxi.puzzle.image;

import by.maxi.puzzle.model.Area;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
