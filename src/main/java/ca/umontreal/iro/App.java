package ca.umontreal.iro;

import ca.umontreal.iro.ui.Bar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        var vBox = new VBox(new Bar(stage));

        stage.setTitle("UCD Parser");
        stage.setScene(new Scene(vBox, 640, 480));
        stage.show();
    }
}
