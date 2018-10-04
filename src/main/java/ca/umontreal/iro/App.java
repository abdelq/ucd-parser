package ca.umontreal.iro;

import ca.umontreal.iro.panes.CenterPane;
import ca.umontreal.iro.panes.LeftPane;
import ca.umontreal.iro.panes.TopPane;
import ca.umontreal.iro.parser.tree.Model;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
    public static Model model;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);

        root.setTop(new TopPane());
        root.setLeft(new LeftPane());
        root.setCenter(new CenterPane());

        primaryStage.setTitle("UCD Parser");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
