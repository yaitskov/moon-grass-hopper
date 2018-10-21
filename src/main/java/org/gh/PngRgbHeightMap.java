package org.gh;

import lombok.RequiredArgsConstructor;

import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public class PngRgbHeightMap extends HeightMap {
    private final BufferedImage image;

    @Override
    public double score(FlatCoordinate c) {
        if (c.getX() > image.getWidth() || c.getX() < 0
                || c.getY() > image.getHeight() || c.getY() < 0) {
            return Double.MAX_VALUE;
        }
        return super.score(c);
    }
}
