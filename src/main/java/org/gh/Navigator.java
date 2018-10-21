package org.gh;

import lombok.Builder;
import org.gh.action.TravelAction;

import java.util.ArrayList;
import java.util.List;

@Builder
public class Navigator {
    private final HeightMap heightMap;
    private final HopperParameters hopperParameters;
    private final MissionParameters missionParameters;
    private final List<TravelAction> arsenal;

    public List<Hop> findRoute(FlatCoordinate source, FlatCoordinate destination) {
        final List<Hop> result = new ArrayList<>();


        FlatCoordinate current = new FlatCoordinate(source.getX(), source.getY());

        FlatCoordinate originDirection = destination.direction(source).one();

        while (true) {
            double distance = destination.distance(current);
            if (distance <= missionParameters.getAllowedErrorM()) {
                break;
            }


        }
        return result;
    }
}
