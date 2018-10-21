package org.gh;

import static org.gh.FlatCoordinate.cof;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.util.List;

public class NavigatorBasicTest {
    public Navigator navigator(HeightMap heightMap) {
        return Navigator
                .builder()
                .heightMap(heightMap)
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
        final List<Hop> hops = navigator(new FlatHeightMap())
                .findRoute(cof(0, 0), cof(100, 0));
        assertThat(hops.size(),
                allOf(greaterThan(3),
                        lessThan(5)));
        assertThat(hops,
                everyItem(
                        allOf(
                                hasProperty("turnAngle", equalTo(0.0)),
                                hasProperty("speedMs", closeTo(5.0, 0.001)),
                                hasProperty("source",
                                        hasProperty("y", closeTo(0.0, 0.001))))));
    }

    @Test
    public void oneTurnRestStraight() {
        final List<Hop> hops = navigator(CircleHeightMap
                .builder()
                .radius(10)
                .matchScore(10000)
                .center(FlatCoordinate.cof(50, 0))
                .build())
                .findRoute(cof(0, 0), cof(100, 0));
        assertThat(hops,
                hasItems(
                        allOf(
                                hasProperty("turnAngle", equalTo(0.0)),
                                hasProperty("speedMs", closeTo(5.0, 0.001))),
                        allOf(
                                hasProperty("turnAngle", equalTo(45.0)),
                                hasProperty("speedMs", closeTo(5.0, 0.001)))));
    }
}
