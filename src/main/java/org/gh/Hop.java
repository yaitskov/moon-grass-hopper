package org.gh;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Hop {
    private double turnAngle;
    private double jumpAngle;
    private double speedMs;
    private FlatCoordinate source;
}
