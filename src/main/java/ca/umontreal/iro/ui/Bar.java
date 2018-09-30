package ca.umontreal.iro.ui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;

public class Bar extends MenuBar {
    public Bar(Stage stage) {
        var fileMenu = new Menu("File");

        var fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("UCD Files", "*.ucd"),
            new ExtensionFilter("All Files", "*.*")
        );

        var open = new MenuItem("Open");
        open.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                // TODO
            }
        });

        var exit = new MenuItem("Exit");
        exit.setOnAction(event -> System.exit(0));

        fileMenu.getItems().addAll(open, exit);
        this.getMenus().addAll(fileMenu);
    }
}
