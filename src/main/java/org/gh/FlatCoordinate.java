package org.gh;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlatCoordinate {
    private double x;
    private double y;

    public double length() {
        return Math.sqrt(x*x + y*y);
    }

    public double distance(FlatCoordinate current) {
        final double dx = x - current.x;
        final double dy = y - current.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public FlatCoordinate direction(FlatCoordinate source) {
        return FlatCoordinate
                .builder()
                .x(x - source.getX())
                .y(y - source.getY())
                .build();
    }

    public FlatCoordinate one() {
        final double l = length();
        return FlatCoordinate
                .builder()
                .x(x / l)
                .y(y / l)
                .build();
    }
}
