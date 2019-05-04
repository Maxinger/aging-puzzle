package org.agingpuzzle.service;

import org.agingpuzzle.model.Area;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TileView {

    final Area area;
    final String coords;
}
