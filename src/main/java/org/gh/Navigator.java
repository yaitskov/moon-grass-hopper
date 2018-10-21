package org.gh;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class Navigator {
    private final HeightMap heightMap;
    private final SlopMap slopMap;
    private final HopperParameters hopperParameters;
    private final MissionParameters missionParameters;

    public List<Hop> findRoute(FlatCoordinate source, FlatCoordinate destination) {
        final List<Hop> result = new ArrayList<>();

        FlatCoordinate current = new FlatCoordinate(source.getX(), source.getY());

        FlatCoordinate originDirection = destination.direction(source).one();
        FlatCoordinate direction = originDirection;

        double timeToFall = timeToFall(
                sin(toRadians(45)) * hopperParameters.getMaxJumpSpeedMs(),
                missionParameters.getGravitaionKgMs());

        double maxDistanceFlat = timeToFall
                * cos(toRadians(45))
                * hopperParameters.getMaxJumpSpeedMs();

        double turnAngle = 0.0;

        while (true) {
            double distance = destination.distance(current);
            if (distance <= maxDistanceFlat) {
                break;
            }

            final FlatCoordinate possibleNext = direction.scale(maxDistanceFlat);

            double h = heightMap.score(current);
            double nH = heightMap.score(possibleNext);

            if (nH > h) {
                if (turnAngle > 90) {
                    // retreat
                    if (result.isEmpty()) {
                        throw new RuntimeException("no way");
                    } else {
                        turnAngle = 45;
                        FlatCoordinate wasSource = result.get(result.size() - 1).getSource();
                        result.remove(result.size() - 1);
                        originDirection = destination.direction(wasSource).one();
                        direction = direction.rotateCcw(45);
                    }
                } else {
                    turnAngle += 45;
                    direction = direction.rotateCcw(45);
                }
            } else {
                // flat moon ;)
                result.add(Hop
                        .builder()
                        .jumpAngle(45.0)
                        .speedMs(hopperParameters.getMaxJumpSpeedMs())
                        .turnAngle(turnAngle)
                        .source(current)
                        .build());
                current = possibleNext;
                turnAngle = 0;
                direction = originDirection;
            }
        }
        return result;
    }

    private double timeToFall(double startSpeed, double acceleration) {
        return 2 * startSpeed / acceleration;
    }
}
