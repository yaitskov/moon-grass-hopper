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

    public static FlatCoordinate cof(double x, double y) {
        return new FlatCoordinate(x, y);
    }

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

    public FlatCoordinate scale(double c) {
        return FlatCoordinate
                .builder()
                .x(x * c)
                .y(y * c)
                .build();
    }

    public FlatCoordinate rotateCcw(double angleDeg) {
        final double angleRad = Math.toRadians(angleDeg);
        return FlatCoordinate.builder()
                .x(x * Math.cos(angleRad) - y * Math.sin(angleRad))
                .y(x * Math.sin(angleRad) + y * Math.cos(angleRad))
                .build();
    }

    public FlatCoordinate plus(FlatCoordinate shift) {
        return FlatCoordinate.builder()
                .x(x + shift.getX())
                .y(y + shift.getY())
                .build();
    }

    public String toString() {
        return "(" + x + ";" + y + ")";
    }
}
