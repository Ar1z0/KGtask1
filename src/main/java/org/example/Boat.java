package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Boat {

    private static final double boatX = 160;
    private static final double boatY = 405;

    private static final Color boatColor = Color.rgb(133, 90, 50);        // корпус корабля
    private static final Color deckColor = Color.rgb(105, 70, 38);        // борт палубы
    private static final Color mastColor = Color.rgb(110, 75, 40);        // мачта
    private static final Color flagColor = Color.rgb(225, 45, 40);        // красный флаг
    private static final Color portholeGlow = Color.rgb(255, 240, 150);   // свет в иллюминаторах

    // корабль
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(boatX, boatY);

        // мачта
        gc.setFill(mastColor);
        gc.fillRect(-2, -85, 4, 85);

        // флаг на мачте
        gc.setFill(flagColor);
        double[] flagX = { -2, -24, -2 };
        double[] flagY = { -85, -78, -71 };
        gc.fillPolygon(flagX, flagY, 3);

        // канаты к носу и корме
        gc.setStroke(Color.rgb(65, 45, 25));
        gc.setLineWidth(1.0);
        gc.strokeLine(0, -82, 68, 0);
        gc.strokeLine(0, -82, -62, 0);

        // паруса
        gc.setFill(Color.WHITE);
        // правый парус (большой)
        double[] mainSailX = { 2, 48, 2 };
        double[] mainSailY = { -80, -5, -5 };
        gc.fillPolygon(mainSailX, mainSailY, 3);

        // левый парус (малый)
        double[] jibSailX = { -3, -42, -3 };
        double[] jibSailY = { -75, -5, -5 };
        gc.fillPolygon(jibSailX, jibSailY, 3);

        // корпус корабля
        gc.setFill(boatColor);
        double[] hullX = { -65, 70, 50, -45 };
        double[] hullY = { 0, 0, 24, 24 };
        gc.fillPolygon(hullX, hullY, 4);

        // полоса по борту
        gc.setStroke(deckColor);
        gc.setLineWidth(2.0);
        gc.strokeLine(-65, 1, 70, 1);

        // иллюминаторы
        for (int i = 0; i < 3; i++) {
            double px = -22 + i * 22;
            double py = 12;

            // обод
            gc.setFill(Color.rgb(180, 140, 60));
            gc.fillOval(px - 5, py - 5, 10, 10);

            // свет внутри
            gc.setFill(portholeGlow);
            gc.fillOval(px - 3, py - 3, 6, 6);
        }

        // рябь под кораблем
        gc.setStroke(Color.color(0.40, 0.60, 0.90, 0.6));
        gc.setLineWidth(1.5);
        gc.strokeLine(-55, 26, -10, 26);
        gc.strokeLine(5, 26, 60, 26);

        gc.restore();
    }
}
