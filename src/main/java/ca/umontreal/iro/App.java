package ca.umontreal.iro;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        var box = new VBox();
        var scene = new Scene(box, 640, 480);

        var menuBar = new MenuBar();
        var file = new Menu("File");

        var open = new MenuItem("Open");
        var fileChooser = new FileChooser();
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    // TODO
                }
            }
        });

        var exit = new MenuItem("Exit");
        exit.setOnAction((event) -> {
            System.exit(0);
        });

        file.getItems().addAll(open, exit);
        menuBar.getMenus().addAll(file);
        box.getChildren().addAll(menuBar);

        stage.setTitle("UCD Parser");
        stage.setScene(scene);
        stage.show();
    }
}
