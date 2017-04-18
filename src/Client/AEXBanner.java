package Client;

/**
 * Created by Ken on 28-3-2017.
 */
import Server.Effectenbeurs;
import Server.IEffectenbeurs;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.rmi.RemoteException;


public class AEXBanner extends Application{

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 100;
    private static final int NANO_TICKS = 20000000;
    // FRAME_RATE = 1000000000/NANO_TICKS = 50;

    private Text text;
    private double textLength;
    private double textPosition;
    private BannerController controller;
    private AnimationTimer animationTimer;

    public AEXBanner(IEffectenbeurs beurs) {
        try {
            controller = new BannerController(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        setEffectenBeurs(beurs);
    }

    @Override
    public void start(Stage primaryStage) throws RemoteException {
        System.out.println("Starting banner");


        Font font = new Font("Arial", HEIGHT);
        text = new Text();
        text.setFont(font);
        text.setFill(Color.BLACK);

        Pane root = new Pane();
        root.getChildren().add(text);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("AEX banner");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.toFront();


        // Start animation: text moves from right to left
        animationTimer = new AnimationTimer() {
            private long prevUpdate;

            @Override
            public void handle(long now) {
                long lag = now - prevUpdate;
                if (lag >= NANO_TICKS) {
                    if (textPosition < 0 - text.getLayoutBounds().getWidth()){
                       textPosition = WIDTH;
                    }
                    double newPosition = textPosition - 10;
                    text.relocate(newPosition,0);
                    textPosition = newPosition;
                    prevUpdate = now;
                }
            }
            @Override
            public void start() {
                prevUpdate = System.nanoTime();
                textPosition = WIDTH;
                text.relocate(textPosition, 0);
                setKoersen("Fetching data...");
                super.start();
            }
        };
        animationTimer.start();
    }

    void setKoersen(String koersen) {
        text.setText(koersen);
        textLength = text.getLayoutBounds().getWidth();
    }

    public void setEffectenBeurs(IEffectenbeurs beurs) {
        controller.setEffectenbeurs(beurs);
    }

    @Override
    public void stop() {
        animationTimer.stop();
        controller.stop();
    }
}
