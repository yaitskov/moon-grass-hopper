package org.gh;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class FlatHeightMap extends HeightMap {
    private double matchScore;

    public double score(FlatCoordinate c) {
        return matchScore;
    }
}
