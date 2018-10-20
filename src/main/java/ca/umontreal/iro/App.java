package ca.umontreal.iro;

import ca.umontreal.iro.panes.*;
import ca.umontreal.iro.parser.tree.Model;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    /**
     * Current use case model.
     */
    public static Model model;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Parseur UCD");

        primaryStage.setScene(new Scene(new BorderPane(
            new VBox(new CenterPane(), new BottomPane()),
            new TopPane(), new RightPane(), null, new LeftPane()
        )));

        primaryStage.show();
    }
}
