package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rock {

    private static final Color rockColor = Color.rgb(75, 68, 60); // цвет скалы

    private final double width;
    private final double height;
    private final double rockY;

    public Rock(double width, double height, double rockY) {
        this.width = width;
        this.height = height;
        this.rockY = rockY;
    }

    // скала справа
    public void draw(GraphicsContext gc) {
        double[] xPoints = {
            750,        // левый край площадки под маяком
            710,        // уступы скалы
            690,
            625,
            560,        // спуск в воду
            width,
            width
        };

        double[] yPoints = {
            rockY,      // уровень площадки
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
}
