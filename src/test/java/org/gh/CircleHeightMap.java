package org.gh;

import lombok.Builder;

@Builder
public class CircleHeightMap extends HeightMap {
    private final double matchScore;
    private final FlatCoordinate center;
    private final double radius;

    public double score(FlatCoordinate c) {
        final double d = center.distance(c);
        if (d <= radius) {
            return matchScore;
        }
        return 0;
    }
}
