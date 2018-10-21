package org.gh;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

import javax.imageio.ImageIO;

public class EntryPoint {
    @SneakyThrows
    public static void main(String[] args) {
        if (args.length != 6) {
            System.err.println("Usage: app.jar <hight-map-rgb.png> <sourceX> <sourceY> <destX> <destY> <output.json>");
            System.exit(1);
        }
        String heightMapFile = args[0];
        double sourceX = Double.parseDouble(args[1]);
        double sourceY = Double.parseDouble(args[2]);
        double destX = Double.parseDouble(args[3]);
        double destY = Double.parseDouble(args[4]);
        String outPutFile = args[5];

        HeightMap heightMap = new PngRgbHeightMap(ImageIO.read(new File(heightMapFile)));

        Navigator navigator = Navigator
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

        ObjectMapper om = new ObjectMapper();

        om.writeValue(
                new File(outPutFile),
                navigator.findRoute(FlatCoordinate.cof(sourceX, sourceY),
                        FlatCoordinate.cof(destX, destY)));
    }
}
