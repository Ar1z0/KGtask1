package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LighthouseApp extends Application {

    // размеры окна
    private static final double WIDTH = 1024;
    private static final double HEIGHT = 576;

    // уровни и ключевые точки
    private static final double seaY = 406;         // линия горизонта (уровень моря)
    private static final double rockY = 360;        // высота скалы под маяком
    private static final double lighthouseX = 868;  // центр маяка по горизонтали
    private static final double topY = 160;         // верх башни маяка
    private static final double lanternY = 130;     // центр фонаря маяка
    private static final double boatX = 160;        // координаты кораблика
    private static final double boatY = 405;

    // цвета сцены
    private static final Color skyColor = Color.rgb(22, 65, 110);         // ночное небо
    private static final Color seaColor = Color.rgb(19, 27, 76);          // вода
    private static final Color rockColor = Color.rgb(75, 68, 60);         // скала
    private static final Color redStripe = Color.rgb(226, 40, 36);        // красные полосы маяка
    private static final Color whiteStripe = Color.WHITE;                 // белые полосы
    private static final Color lanternGlass = Color.rgb(250, 220, 65);    // стекло фонаря
    private static final Color lanternLight = Color.rgb(255, 255, 200);   // лампа внутри
    private static final Color beamColor = Color.color(1.0, 0.94, 0.45, 0.35); // полупрозрачный луч
    private static final Color boatColor = Color.rgb(133, 90, 50);        // корпус кораблика
    private static final Color mastColor = Color.rgb(110, 75, 40);        // мачта
    private static final Color waveColor = Color.color(0.25, 0.42, 0.75, 0.55); // полоски волн

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

    private final List<Star> stars = new ArrayList<>();
    private final List<Wave> waves = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // генерируем случайные звезды и волны
        initStars();
        initWaves();

        // рисуем всю сцену
        render(gc);

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("Компьютерная графика - Рисунок «Ночной маяк на скале»");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // расставляем звезды на небе
    private void initStars() {
        Random random = new Random(42);
        for (int i = 0; i < 55; i++) {
            double x = 15 + random.nextDouble() * (WIDTH - 30);
            double y = 15 + random.nextDouble() * (seaY - 40);

            // не ставим звезды на саму луну
            if (Math.hypot(x - 213, y - 110) < 45) {
                continue;
            }

            double r = 1.0 + random.nextDouble() * 1.4;
            stars.add(new Star(x, y, r));
        }
    }

    // создаем полоски волн на воде
    private void initWaves() {
        Random random = new Random(100);
        for (int i = 0; i < 18; i++) {
            double x = 30 + random.nextDouble() * (WIDTH - 120);
            double y = seaY + 15 + random.nextDouble() * (HEIGHT - seaY - 30);
            double len = 35 + random.nextDouble() * 60;
            waves.add(new Wave(x, y, len));
        }
    }

    // общая отрисовка сцены (по слоям от фона к переднему плану)
    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        // фон
        drawSky(gc);
        drawMoon(gc);
        drawStars(gc);

        // средний план
        drawSea(gc);
        drawWaves(gc);

        // передний план
        drawRock(gc);
        drawLighthouse(gc);
        drawBoat(gc);
        drawBeam(gc);
    }

    // небо на весь экран
    private void drawSky(GraphicsContext gc) {
        gc.setFill(skyColor);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
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

    // морская вода в нижней части
    private void drawSea(GraphicsContext gc) {
        gc.setFill(seaColor);
        gc.fillRect(0, seaY, WIDTH, HEIGHT - seaY);
    }

    // волны
    private void drawWaves(GraphicsContext gc) {
        gc.setStroke(waveColor);
        gc.setLineWidth(2.0);
        for (Wave w : waves) {
            gc.strokeLine(w.x, w.y, w.x + w.length, w.y);
        }
    }

    // скала справа
    private void drawRock(GraphicsContext gc) {
        double[] x = { 750, 710, 690, 625, 560, WIDTH, WIDTH };
        double[] y = { rockY, 430, 460, 495, HEIGHT, HEIGHT, rockY };

        gc.setFill(rockColor);
        gc.fillPolygon(x, y, x.length);
    }

    // маяк
    private void drawLighthouse(GraphicsContext gc) {
        double baseWidth = 100;
        double topWidth = 50;
        int stripeCount = 5;
        double stripeHeight = (rockY - topY) / stripeCount;

        // 5 чередующихся полос (сужаются кверху)
        for (int i = 0; i < stripeCount; i++) {
            double y1 = topY + i * stripeHeight;
            double y2 = topY + (i + 1) * stripeHeight;

            double w1 = topWidth + (baseWidth - topWidth) * ((y1 - topY) / (rockY - topY));
            double w2 = topWidth + (baseWidth - topWidth) * ((y2 - topY) / (rockY - topY));

            gc.setFill((i % 2 == 0) ? redStripe : whiteStripe);

            double[] px = {
                lighthouseX - w1 / 2.0,
                lighthouseX + w1 / 2.0,
                lighthouseX + w2 / 2.0,
                lighthouseX - w2 / 2.0
            };
            double[] py = { y1, y1, y2, y2 };

            gc.fillPolygon(px, py, 4);
        }

        // балкон под фонарем
        gc.setFill(redStripe);
        gc.fillRect(lighthouseX - 40, 146, 80, 14);

        // фонарная комната
        gc.setFill(lanternGlass);
        gc.fillRect(lighthouseX - 22, 114, 44, 32);

        // шестиугольная лампа
        drawHexagon(gc, lighthouseX, lanternY, 10, lanternLight);

        // крыша и шпиль
        gc.setFill(redStripe);
        gc.fillRect(lighthouseX - 28, 100, 56, 14);
        gc.fillRect(lighthouseX - 3, 95, 6, 5);

        // ореол света вокруг фонаря
        gc.setFill(Color.color(1.0, 0.95, 0.5, 0.28));
        gc.fillOval(lighthouseX - 16, lanternY - 16, 32, 32);
        gc.setFill(Color.color(1.0, 1.0, 0.8, 0.45));
        gc.fillOval(lighthouseX - 8, lanternY - 8, 16, 16);
    }

    // рисуем правильный шестиугольник
    private void drawHexagon(GraphicsContext gc, double cx, double cy, double r, Color color) {
        double[] x = new double[6];
        double[] y = new double[6];
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i);
            x[i] = cx + r * Math.cos(angle);
            y[i] = cy + r * Math.sin(angle);
        }
        gc.setFill(color);
        gc.fillPolygon(x, y, 6);
    }

    // парусный кораблик
    private void drawBoat(GraphicsContext gc) {
        gc.save();
        gc.translate(boatX, boatY);

        // мачта
        gc.setFill(mastColor);
        gc.fillRect(-2, -85, 4, 85);

        // паруса
        gc.setFill(Color.WHITE);
        // правый парус
        gc.fillPolygon(new double[]{ 2, 48, 2 }, new double[]{ -80, -5, -5 }, 3);
        // левый парус
        gc.fillPolygon(new double[]{ -3, -42, -3 }, new double[]{ -75, -5, -5 }, 3);

        // корпус (трапеция)
        gc.setFill(boatColor);
        double[] hullX = { -65, 70, 50, -45 };
        double[] hullY = { 0, 0, 24, 24 };
        gc.fillPolygon(hullX, hullY, 4);

        gc.restore();
    }

    // световой луч маяка
    private void drawBeam(GraphicsContext gc) {
        double angle = 158.0;       // направление луча (в сторону кораблика)
        double spread = 18.0;      // ширина конуса света
        double length = 1150.0;    // длина луча

        double a1 = Math.toRadians(angle - spread / 2.0);
        double a2 = Math.toRadians(angle + spread / 2.0);

        double x1 = lighthouseX + length * Math.cos(a1);
        double y1 = lanternY + length * Math.sin(a1);
        double x2 = lighthouseX + length * Math.cos(a2);
        double y2 = lanternY + length * Math.sin(a2);

        // полупрозрачный желтый треугольник
        gc.setFill(beamColor);
        gc.fillPolygon(
            new double[]{ lighthouseX, x1, x2 },
            new double[]{ lanternY, y1, y2 },
            3
        );
    }
}
