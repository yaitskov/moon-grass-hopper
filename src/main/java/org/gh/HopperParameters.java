package org.gh;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HopperParameters {
    private double maxJumpSpeedMs;
    private double maxTurnAngleDeg;
    private double maxJumpAngleDeg;
    private double maxSlopAngleDeg;

    private double powerHop;
    private double batteryCapacity;
    private double chargeTime;
}
