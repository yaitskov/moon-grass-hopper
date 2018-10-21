package org.gh;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Hop {
    private double turnAngle;
    private double jumpAngle;
    private double speedMs;
    private FlatCoordinate source;
}
