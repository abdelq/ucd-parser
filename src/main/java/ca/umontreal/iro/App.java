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
    private static Model model;
    /**
     * Primary stage.
     */
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        stage.setTitle("UCD Parser");
        stage.setScene(new Scene(new BorderPane(
                new VBox(new CenterPane(), new BottomPane()),
                new TopPane(), new RightPane(), null, new LeftPane()
        )));
        stage.show();
    }

    /**
     * TODO
     *
     * @return
     */
    public static Model getModel() {
        return model;
    }

    /**
     * TODO
     *
     * @param model
     */
    public static void setModel(Model model) {
        if ((App.model = model) != null) {
            stage.setTitle(model.id + " - UCD Parser");
        } else {
            stage.setTitle("UCD Parser");
        }
    }
}
