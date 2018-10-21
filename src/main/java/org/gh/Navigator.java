package org.gh;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Builder
public class Navigator {
    private final HeightMap heightMap;
    private final SlopMap slopMap;
    private final HopperParameters hopperParameters;
    private final MissionParameters missionParameters;

    public List<Hop> findRoute(FlatCoordinate source, FlatCoordinate destination) {
        final List<Hop> result = new ArrayList<>();
        FlatCoordinate current = new FlatCoordinate(source.getX(), source.getY());
        double baseTurn = 15;
        double jumpAngleDeg = 45;
        double timeToFall = timeToFall(
                sin(toRadians(jumpAngleDeg)) * hopperParameters.getMaxJumpSpeedMs(),
                missionParameters.getGravitaionKgMs());

        double maxDistanceFlat = timeToFall
                * cos(toRadians(jumpAngleDeg))
                * hopperParameters.getMaxJumpSpeedMs();

        double turnAngle = 0.0;

        while (true) {
            double distance = destination.distance(current);
            if (distance < maxDistanceFlat) {
                break;
            }

            final FlatCoordinate direction = destination.direction(current).one()
                    .rotateCcw(turnAngle);
            final FlatCoordinate possibleNext = current.plus(
                    direction.scale(maxDistanceFlat));

            double h = heightMap.score(current);
            double nH = heightMap.score(possibleNext);

            if (nH > h) {
                // find where nH is reached
                Optional<FlatCoordinate> oPossi = findXWhereH(direction, current, h, nH, 45);
                if (oPossi.isPresent()) {
                    result.add(Hop
                            .builder()
                            .jumpAngle(jumpAngleDeg)
                            .speedMs(hopperParameters.getMaxJumpSpeedMs())
                            .turnAngle(turnAngle)
                            .source(current)
                            .build());
                    current = oPossi.get();
                    turnAngle = 0;
                    jumpAngleDeg = 45;
                    log.info("Increase jump angel {}", jumpAngleDeg);
                    timeToFall = timeToFall(
                            sin(toRadians(jumpAngleDeg)) * hopperParameters.getMaxJumpSpeedMs(),
                            missionParameters.getGravitaionKgMs());
                    maxDistanceFlat = timeToFall
                            * cos(toRadians(jumpAngleDeg))
                            * hopperParameters.getMaxJumpSpeedMs();
                } else if (jumpAngleDeg < 86) { //if (turnAngle > 60) {
                    jumpAngleDeg += 1;
                    log.info("Increase jump angel {}", jumpAngleDeg);
                    timeToFall = timeToFall(
                            sin(toRadians(jumpAngleDeg)) * hopperParameters.getMaxJumpSpeedMs(),
                            missionParameters.getGravitaionKgMs());
                    maxDistanceFlat = timeToFall
                            * cos(toRadians(jumpAngleDeg))
                            * hopperParameters.getMaxJumpSpeedMs();
                } else if (turnAngle < 50) {
                    turnAngle += baseTurn;
                } else {
                    if (result.isEmpty()) {
                        throw new RuntimeException("no way");
                    } else {
                        final Hop hop = result.get(result.size() - 1);
                        turnAngle = hop.getTurnAngle() + baseTurn;
                        jumpAngleDeg = hop.getJumpAngle();
                        FlatCoordinate wasSource = hop.getSource();
                        result.remove(result.size() - 1);
                        current = wasSource;
                    }
                }
            } else {
                // flat moon ;)
                result.add(Hop
                        .builder()
                        .jumpAngle(jumpAngleDeg)
                        .speedMs(hopperParameters.getMaxJumpSpeedMs())
                        .turnAngle(turnAngle)
                        .source(current)
                        .build());
                current = possibleNext;
                turnAngle = 0;
                jumpAngleDeg = 45;
            }
        }
        return result;
    }

    private double timeToFall(double startSpeed, double acceleration) {
        return 2 * startSpeed / acceleration;
    }

    private Optional<FlatCoordinate> findXWhereH(
            FlatCoordinate unit, FlatCoordinate c, double h, double hN, int angdeg) {
        double halfG = - missionParameters.getGravitaionKgMs() / 2;
        double v0y = sin(toRadians(angdeg))
                * hopperParameters.getMaxJumpSpeedMs();
        double d = v0y * v0y - 4 * halfG * (h - hN);

        if (d <= 0) {
            return Optional.empty(); //
        }
        double dS = Math.sqrt(d);
        double t1 = (-v0y + dS) / (2 * halfG);

        final double a1 = diff(t1, v0y, halfG);
        if (a1 < 0) {
//        if (t1 > 0) {
            return Optional.of(c.plus(unit.scale(x(t1, angdeg))));
        }
        double t2 = (-v0y - dS) / (2 * halfG);
        final double a2 = diff(t2, v0y, halfG);
        if (a2 < 0) {
//        if (t2 > 0) {
            return Optional.of(c.plus(unit.scale(x(t2, angdeg))));
        }
        throw new IllegalArgumentException("no decrease");
//        throw new IllegalArgumentException("negative time");
    }

    private double diff(double t, double v0y, double halfG) {
        return 2 * halfG * t + v0y;
    }

    private double x(double t, int angdeg) {
        return t * cos(toRadians(angdeg))
                * hopperParameters.getMaxJumpSpeedMs();
    }
}
