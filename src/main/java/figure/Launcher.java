package figure;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        System.setProperty("prism.order", "sw");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Modele m = Modele.getInstance();
        m.start(stage);
    }
}
