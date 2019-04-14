package by.maxi.puzzle.service;

import by.maxi.puzzle.model.Area;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TileView {

    final Area area;
    final String coords;
}
