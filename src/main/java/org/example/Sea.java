package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sea {

    private static final Color seaColor = Color.rgb(19, 27, 76);          // вода
    private static final Color waveColor = Color.color(0.25, 0.42, 0.75, 0.55); // полоски волн

    private final double width;
    private final double height;
    private final double seaY;

    // волны на воде
    private static class Wave {
        double x, y;
        double length;

        Wave(double x, double y, double length) {
            this.x = x;
            this.y = y;
            this.length = length;
        }
    }

    private final List<Wave> waves = new ArrayList<>();

    public Sea(double width, double height, double seaY) {
        this.width = width;
        this.height = height;
        this.seaY = seaY;
        initWaves();
    }

    // создаем полоски волн на воде
    private void initWaves() {
        Random random = new Random(100);
        for (int i = 0; i < 18; i++) {
            double x = 30 + random.nextDouble() * (width - 120);
            double y = seaY + 15 + random.nextDouble() * (height - seaY - 30);
            double len = 35 + random.nextDouble() * 60;
            waves.add(new Wave(x, y, len));
        }
    }

    public void draw(GraphicsContext gc) {
        drawSea(gc);
        drawWaves(gc);
    }

    // море в нижней части
    private void drawSea(GraphicsContext gc) {
        gc.setFill(seaColor);
        gc.fillRect(0, seaY, width, height - seaY);
    }

    // полоски ряби на воде
    private void drawWaves(GraphicsContext gc) {
        gc.setStroke(waveColor);
        gc.setLineWidth(2.0);

        for (Wave wave : waves) {
            gc.strokeLine(wave.x, wave.y, wave.x + wave.length, wave.y);
        }
    }
}
