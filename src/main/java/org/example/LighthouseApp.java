package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LighthouseApp extends Application {

    // размеры окна
    private static final double WIDTH = 1024;
    private static final double HEIGHT = 576;

    // ключевые уровни
    private static final double seaY = 406;  // линия горизонта
    private static final double rockY = 360; // уровень скалы

    // компоненты сцены
    private Sky sky;
    private Sea sea;
    private Rock rock;
    private Lighthouse lighthouse;
    private Boat boat;

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // создаем объекты сцены
        sky = new Sky(WIDTH, HEIGHT, seaY);
        sea = new Sea(WIDTH, HEIGHT, seaY);
        rock = new Rock(WIDTH, HEIGHT, rockY);
        lighthouse = new Lighthouse();
        boat = new Boat();

        // рисуем всю сцену
        render(gc);

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("Компьютерная графика - Рисунок «Ночной маяк на скале»");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // отрисовка всех слоев сцены от фона к переднему плану
    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        // фон (небо, луна, звёзды)
        sky.draw(gc);

        // море и рябь
        sea.draw(gc);

        // скала справа
        rock.draw(gc);

        // башня маяка
        lighthouse.draw(gc);

        // корабль
        boat.draw(gc);

        // луч света от маяка
        lighthouse.drawBeam(gc);
    }
}
