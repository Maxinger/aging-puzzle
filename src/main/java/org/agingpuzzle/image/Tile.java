package org.agingpuzzle.image;

import org.agingpuzzle.model.Area;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class Tile {

    final Optional<Area> area;
    final int topTab;
    final int rightTab;
    final int leftTab;
    final int bottomTab;

    public String getCode() {
        return area.map(a -> a.getName().substring(0, 2)).orElse(null);
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
