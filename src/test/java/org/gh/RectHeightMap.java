package org.gh;

import lombok.Builder;

@Builder
public class RectHeightMap extends HeightMap {
    private final double matchScore;

    private final double left;
    private final double right;
    private final double top;
    private final double bottom;

    public double score(FlatCoordinate c) {
        if (left <= c.getX() && c.getX() <= right
                && top <= c.getY() && c.getY() <= bottom) {
            return matchScore;
        }
        return 0;
    }
}
