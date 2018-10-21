package org.gh;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SumHeightMap extends HeightMap {
    private final List<HeightMap> maps;

    @Override
    public double score(FlatCoordinate c) {
        return maps.stream().mapToDouble(m -> m.score(c)).sum();
    }
}
