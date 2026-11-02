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
        drawStars(gc);
    }

    // небо на весь экран
    private void drawSky(GraphicsContext gc) {
        gc.setFill(skyColor);
        gc.fillRect(0, 0, width, height);
    }

    // луна со свечением вокруг
    private void drawMoon(GraphicsContext gc) {
        double moonX = 213;
        double moonY = 110;
        double r = 29;

        // ореол
        gc.setFill(Color.color(1.0, 1.0, 1.0, 0.08));
        gc.fillOval(moonX - 38, moonY - 38, 76, 76);

        // сама луна
        gc.setFill(Color.WHITE);
        gc.fillOval(moonX - r, moonY - r, r * 2, r * 2);
    }

    // россыпь звезд
    private void drawStars(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        for (Star s : stars) {
            gc.fillOval(s.x - s.radius, s.y - s.radius, s.radius * 2, s.radius * 2);

            // для ярких звездочек рисуем маленький блеск
            if (s.radius > 2.0) {
                gc.setStroke(Color.color(1.0, 1.0, 1.0, 0.7));
                gc.setLineWidth(0.8);
                gc.strokeLine(s.x - 3, s.y, s.x + 3, s.y);
                gc.strokeLine(s.x, s.y - 3, s.x, s.y + 3);
            }
        }
    }
}
