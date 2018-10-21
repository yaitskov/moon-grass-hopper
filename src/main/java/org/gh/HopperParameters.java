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
public class HopperParameters {
    private double maxJumpSpeedMs;
    private double maxTurnAngleDeg;
    private double maxJumpAngleDeg;
    private double maxSlopAngleDeg;
}
