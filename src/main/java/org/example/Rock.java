package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rock {

    private static final Color rockColor = Color.rgb(75, 68, 60);         // базовый цвет скалы
    private static final Color rockShadow = Color.rgb(55, 48, 42);        // тень
    private static final Color rockHighlight = Color.rgb(100, 92, 82);    // освещенный край
    private static final Color grassColor = Color.rgb(55, 78, 45);        // трава
    private static final Color fenceColor = Color.rgb(115, 82, 48);       // ограждение

    private final double width;
    private final double height;
    private final double rockY;

    public Rock(double width, double height, double rockY) {
        this.width = width;
        this.height = height;
        this.rockY = rockY;
    }

    // скала
    public void draw(GraphicsContext gc) {
        drawCliff(gc);
        drawShadow(gc);
        drawHighlightEdge(gc);
        drawGrass(gc);
        drawFence(gc);
    }

    // основа скалы
    private void drawCliff(GraphicsContext gc) {
        double[] xPoints = {
            750,        // левый край площадки
            710,        // первый уступ
            690,        // второй уступ
            625,        // нижний изгиб
            560,        // спуск в воду
            width,
            width
        };

        double[] yPoints = {
            rockY,      // уровень площадки (360)
            430,
            460,
            495,
            height,
            height,
            rockY
        };

        gc.setFill(rockColor);
        gc.fillPolygon(xPoints, yPoints, xPoints.length);
    }

    // тень на скале
    private void drawShadow(GraphicsContext gc) {
        double[] shadowX = { 690, 625, 560, width, width, 750 };
        double[] shadowY = { 460, 495, height, height, 520, 460 };

        gc.setFill(rockShadow);
        gc.fillPolygon(shadowX, shadowY, shadowX.length);
    }

    // освещенная кромка
    private void drawHighlightEdge(GraphicsContext gc) {
        gc.setStroke(rockHighlight);
        gc.setLineWidth(2.5);

        gc.strokeLine(750, rockY, 710, 430);
        gc.strokeLine(710, 430, 690, 460);
        gc.strokeLine(690, 460, 625, 495);
        gc.strokeLine(625, 495, 560, height);
    }

    // трава на краю
    private void drawGrass(GraphicsContext gc) {
        gc.setFill(grassColor);
        gc.fillRoundRect(748, rockY - 2, 70, 5, 2, 2);
    }

    // ограждение
    private void drawFence(GraphicsContext gc) {
        // перекладины
        gc.setStroke(fenceColor);
        gc.setLineWidth(2.2);
        gc.strokeLine(754, rockY - 14, 815, rockY - 14);
        gc.strokeLine(756, rockY - 7, 815, rockY - 7);

        // стойки
        gc.setLineWidth(3.0);
        gc.strokeLine(758, rockY - 18, 758, rockY);
        gc.strokeLine(782, rockY - 18, 782, rockY);
        gc.strokeLine(806, rockY - 18, 806, rockY);
    }
}
