package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sky {

    private static final Color skyColor = Color.rgb(22, 65, 110); // ночное небо

    private final double width;
    private final double height;
    private final double seaY;

    // звёзды
    private static class Star {
        double x, y;
        double radius;

        Star(double x, double y, double radius) {
            this.x = x;
            this.y = y;
            this.radius = radius;
        }
    }

    private final List<Star> stars = new ArrayList<>();

    public Sky(double width, double height, double seaY) {
        this.width = width;
        this.height = height;
        this.seaY = seaY;
        initStars();
    }

    // расставляем звезды на небе
    private void initStars() {
        Random random = new Random(42);
        for (int i = 0; i < 55; i++) {
            double x = 15 + random.nextDouble() * (width - 30);
            double y = 15 + random.nextDouble() * (seaY - 40);

            // не ставим звезды на саму луну
            if (Math.hypot(x - 213, y - 110) < 45) {
                continue;
            }

            double r = 1.0 + random.nextDouble() * 1.4;
            stars.add(new Star(x, y, r));
        }
    }

    public void draw(GraphicsContext gc) {
        drawSky(gc);
        drawMoon(gc);
        drawClouds(gc);
        drawShootingStar(gc);
        drawStars(gc);
    }

    // небо
    private void drawSky(GraphicsContext gc) {
        gc.setFill(skyColor);
        gc.fillRect(0, 0, width, height);
    }

    // луна
    private void drawMoon(GraphicsContext gc) {
        double moonX = 213;
        double moonY = 110;
        double r = 29;

        // свечение
        gc.setFill(Color.color(1.0, 1.0, 1.0, 0.08));
        gc.fillOval(moonX - 44, moonY - 44, 88, 88);

        // ореол
        gc.setFill(Color.color(1.0, 1.0, 1.0, 0.14));
        gc.fillOval(moonX - 35, moonY - 35, 70, 70);

        // сама луна
        gc.setFill(Color.rgb(250, 250, 245));
        gc.fillOval(moonX - r, moonY - r, r * 2, r * 2);

        // кратеры
        gc.setFill(Color.color(0.82, 0.85, 0.90, 0.45));
        gc.fillOval(moonX - 11, moonY - 8, 9, 7);
        gc.fillOval(moonX + 5, moonY + 2, 11, 9);
        gc.fillOval(moonX - 4, moonY + 11, 7, 6);
        gc.fillOval(moonX + 9, moonY - 12, 6, 5);
    }

    // облака
    private void drawClouds(GraphicsContext gc) {
        drawCloud(gc, 380, 85, 1.1);
        drawCloud(gc, 100, 185, 0.9);
        drawCloud(gc, 680, 210, 1.2);
    }

    // облако
    private void drawCloud(GraphicsContext gc, double x, double y, double scale) {
        gc.setFill(Color.color(0.20, 0.40, 0.65, 0.22));
        gc.fillOval(x, y, 65 * scale, 24 * scale);
        gc.fillOval(x + 18 * scale, y - 9 * scale, 52 * scale, 28 * scale);
        gc.fillOval(x + 45 * scale, y - 3 * scale, 55 * scale, 22 * scale);
        gc.fillOval(x + 20 * scale, y + 4 * scale, 65 * scale, 18 * scale);
    }

    // падающая звезда
    private void drawShootingStar(GraphicsContext gc) {
        gc.setStroke(Color.color(1.0, 1.0, 1.0, 0.65));
        gc.setLineWidth(1.4);
        gc.strokeLine(630, 42, 675, 62);

        // след звезды
        gc.setFill(Color.WHITE);
        gc.fillOval(674, 61, 2.5, 2.5);
    }

    // россыпь звезд
    private void drawStars(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        for (Star s : stars) {
            gc.fillOval(s.x - s.radius, s.y - s.radius, s.radius * 2, s.radius * 2);

            // блеск для крупных звезд
            if (s.radius > 2.0) {
                gc.setStroke(Color.color(1.0, 1.0, 1.0, 0.7));
                gc.setLineWidth(0.8);
                gc.strokeLine(s.x - 3, s.y, s.x + 3, s.y);
                gc.strokeLine(s.x, s.y - 3, s.x, s.y + 3);
            }
        }
    }
}
