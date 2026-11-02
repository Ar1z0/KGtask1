package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Boat {

    private static final double boatX = 160;
    private static final double boatY = 405;

    private static final Color boatColor = Color.rgb(133, 90, 50); // корпус кораблика
    private static final Color mastColor = Color.rgb(110, 75, 40); // мачта

    // кораблик с парусами
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(boatX, boatY);

        // мачта
        gc.setFill(mastColor);
        gc.fillRect(-2, -85, 4, 85);

        // паруса
        gc.setFill(Color.WHITE);
        // правый парус (большой)
        double[] mainSailX = { 2, 48, 2 };
        double[] mainSailY = { -80, -5, -5 };
        gc.fillPolygon(mainSailX, mainSailY, 3);

        // левый парус (поменьше)
        double[] jibSailX = { -3, -42, -3 };
        double[] jibSailY = { -75, -5, -5 };
        gc.fillPolygon(jibSailX, jibSailY, 3);

        // корпус
        gc.setFill(boatColor);
        double[] hullX = { -65, 70, 50, -45 };
        double[] hullY = { 0, 0, 24, 24 };
        gc.fillPolygon(hullX, hullY, 4);

        gc.restore();
    }
}
