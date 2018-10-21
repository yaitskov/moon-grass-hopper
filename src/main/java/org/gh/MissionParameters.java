package org.gh;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MissionParameters {
    private double allowedErrorM;
    private int maxHops;
    private double gravitaionKgMs = 9.8;
}
