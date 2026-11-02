package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Lighthouse {

    private static final double lighthouseX = 868; // центр маяка
    private static final double topY = 160;        // верх башни
    private static final double lanternY = 130;    // фонарь
    private static final double rockY = 360;       // уровень площадки скалы

    private static final Color redStripe = Color.rgb(226, 40, 36);        // красные полосы
    private static final Color whiteStripe = Color.WHITE;                 // белые полосы
    private static final Color lanternGlass = Color.rgb(250, 220, 65);    // стекло фонаря
    private static final Color lanternLight = Color.rgb(255, 255, 200);   // лампа внутри
    private static final Color beamColor = Color.color(1.0, 0.94, 0.45, 0.35); // луч света

    // башня маяка с фонарем
    public void draw(GraphicsContext gc) {
        double baseY = rockY;
        double baseWidth = 100;
        double topWidth = 50;

        int stripeCount = 5;
        double stripeHeight = (baseY - topY) / stripeCount;

        // рисуем 5 полос трапеции
        for (int i = 0; i < stripeCount; i++) {
            double y1 = topY + i * stripeHeight;
            double y2 = topY + (i + 1) * stripeHeight;

            double w1 = topWidth + (baseWidth - topWidth) * ((y1 - topY) / (baseY - topY));
            double w2 = topWidth + (baseWidth - topWidth) * ((y2 - topY) / (baseY - topY));

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

        // балкончик под фонарной комнатой
        gc.setFill(redStripe);
        gc.fillRect(lighthouseX - 40, 146, 80, 14);

        // стеклянная кабина со светом
        gc.setFill(lanternGlass);
        gc.fillRect(lighthouseX - 22, 114, 44, 32);

        // лампа внутри фонаря (шестиугольник)
        drawHexagon(gc, lighthouseX, lanternY, 10, lanternLight);

        // крыша маяка
        gc.setFill(redStripe);
        gc.fillRect(lighthouseX - 28, 100, 56, 14);

        // шпиль на крыше
        gc.fillRect(lighthouseX - 3, 95, 6, 5);

        // свечение фонаря
        drawLanternGlow(gc);
    }

    // правильный шестиугольник для лампы
    private void drawHexagon(GraphicsContext gc, double cx, double cy, double r, Color color) {
        double[] hx = new double[6];
        double[] hy = new double[6];
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i);
            hx[i] = cx + r * Math.cos(angle);
            hy[i] = cy + r * Math.sin(angle);
        }
        gc.setFill(color);
        gc.fillPolygon(hx, hy, 6);
    }

    // световой луч маяка
    public void drawBeam(GraphicsContext gc) {
        double currentAngle = 158.0;
        double spreadAngle = 18.0;
        double beamLength = 1150.0;

        double angle1Rad = Math.toRadians(currentAngle - spreadAngle / 2.0);
        double angle2Rad = Math.toRadians(currentAngle + spreadAngle / 2.0);

        double x1 = lighthouseX + beamLength * Math.cos(angle1Rad);
        double y1 = lanternY + beamLength * Math.sin(angle1Rad);
        double x2 = lighthouseX + beamLength * Math.cos(angle2Rad);
        double y2 = lanternY + beamLength * Math.sin(angle2Rad);

        // конус света
        gc.setFill(beamColor);
        double[] beamPx = { lighthouseX, x1, x2 };
        double[] beamPy = { lanternY, y1, y2 };
        gc.fillPolygon(beamPx, beamPy, 3);
    }

    // ореол света вокруг фонаря
    private void drawLanternGlow(GraphicsContext gc) {
        double glowRadius = 16.0;

        // мягкое внешнее свечение
        gc.setFill(Color.color(1.0, 0.95, 0.5, 0.28));
        gc.fillOval(lighthouseX - glowRadius, lanternY - glowRadius, glowRadius * 2, glowRadius * 2);

        // яркая серединка
        gc.setFill(Color.color(1.0, 1.0, 0.8, 0.45));
        gc.fillOval(lighthouseX - 8, lanternY - 8, 16, 16);
    }
}
