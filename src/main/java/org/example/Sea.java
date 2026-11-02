package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sea {

    private static final Color seaColor = Color.rgb(19, 27, 76);          // вода
    private static final Color waveColor = Color.color(0.25, 0.42, 0.75, 0.55); // волны

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

    // создаем волны
    private void initWaves() {
        Random random = new Random(100);
        for (int i = 0; i < 22; i++) {
            double x = 30 + random.nextDouble() * (width - 120);
            double y = seaY + 15 + random.nextDouble() * (height - seaY - 30);
            double len = 35 + random.nextDouble() * 60;
            waves.add(new Wave(x, y, len));
        }
    }

    public void draw(GraphicsContext gc) {
        drawSea(gc);
        drawMoonlightTrack(gc);
        drawWaves(gc);
        drawRockFoam(gc);
    }

    // море
    private void drawSea(GraphicsContext gc) {
        gc.setFill(seaColor);
        gc.fillRect(0, seaY, width, height - seaY);
    }

    // лунная дорожка
    private void drawMoonlightTrack(GraphicsContext gc) {
        double moonX = 213;

        // свечение воды
        gc.setFill(Color.color(0.40, 0.55, 0.80, 0.08));
        double[] glowX = { moonX - 25, moonX + 25, moonX + 70, moonX - 70 };
        double[] glowY = { seaY, seaY, height, height };
        gc.fillPolygon(glowX, glowY, 4);

        // дорожка из волн
        gc.setStroke(Color.color(1.0, 0.98, 0.85, 0.38));
        gc.setLineWidth(2.0);

        int count = 12;
        for (int i = 0; i < count; i++) {
            double y = seaY + 10 + i * 13;
            double spread = 12 + i * 4.5;
            double shift = ((i * 13) % 9) - 4;

            gc.strokeLine(moonX - spread + shift, y, moonX + spread + shift, y);
        }
    }

    // волны
    private void drawWaves(GraphicsContext gc) {
        gc.setStroke(waveColor);
        gc.setLineWidth(2.0);

        for (Wave wave : waves) {
            gc.strokeLine(wave.x, wave.y, wave.x + wave.length, wave.y);
        }
    }

    // пена у скалы
    private void drawRockFoam(GraphicsContext gc) {
        gc.setStroke(Color.color(0.85, 0.95, 1.0, 0.65));
        gc.setLineWidth(2.0);

        // пена у кромки
        gc.strokeLine(715, 408, 735, 408);
        gc.strokeLine(695, 432, 725, 432);
        gc.strokeLine(675, 462, 705, 462);
        gc.strokeLine(610, 497, 640, 497);
        gc.strokeLine(545, 565, 580, 565);

        // пена у воды
        gc.setFill(Color.color(0.9, 0.98, 1.0, 0.55));
        gc.fillOval(730, 406, 3, 3);
        gc.fillOval(718, 430, 4, 3);
        gc.fillOval(698, 460, 4, 3);
        gc.fillOval(632, 495, 4, 3);
        gc.fillOval(565, 563, 5, 4);
    }
}
