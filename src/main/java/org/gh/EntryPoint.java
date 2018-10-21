package org.gh;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.List;

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

        final File file = new File(heightMapFile);
        InputStream is = new FileInputStream(file);
        byte[] b = new byte[(int) file.length()];
        is.read(b);


        final ByteBuffer wrap = ByteBuffer.wrap(b);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        HeightMap heightMap = new BinHeightMap(5000, wrap);

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

        ObjectMapper om = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);

        final List<Hop> route = navigator.findRoute(FlatCoordinate.cof(sourceX, sourceY),
                FlatCoordinate.cof(destX, destY));
        om.writeValue(
                new File(outPutFile),
                route);
    }
}
