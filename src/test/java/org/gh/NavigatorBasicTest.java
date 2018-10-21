package org.gh;

import static org.gh.FlatCoordinate.cof;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class NavigatorBasicTest {
    Navigator sut;

    @Before
    public void setUp() {
        sut = Navigator
                .builder()
                .heightMap(new HeightMap())
                .slopMap(new SlopMap())
                .missionParameters(MissionParameters
                        .builder()
                        .gravitaionKgMs(1)
                        .build())
                .hopperParameters(HopperParameters
                        .builder()
                        .maxJumpAngleDeg(45)
                        .maxJumpSpeedMs(5)
                        .maxTurnAngleDeg(110)
                        .build())
                .build();
    }

    @Test
    public void sunnyDay() {
        // 7 seconds , 35 m
        final List<Hop> hops = sut.findRoute(cof(0, 0), cof(100, 0));
        assertThat(hops.size(),
                allOf(greaterThan(2),
                        lessThan(4)));
        assertThat(hops, hasItem(hasProperty("y", equalTo(0.0))));
    }
}
