package org.gh;

import lombok.RequiredArgsConstructor;

import java.nio.ByteBuffer;

@RequiredArgsConstructor
public class BinHeightMap extends HeightMap {
    private final int size;
    private final ByteBuffer buffer;

    @Override
    public double score(FlatCoordinate c) {
        int index = (int) c.getY() * size + (int) c.getX();
        index *= 4;
        if (c.getX() < 0
                || index >= buffer.capacity()
                || c.getY() < 0) {
            return Double.MAX_VALUE;
        }
        return buffer.getInt(index);
    }
}
