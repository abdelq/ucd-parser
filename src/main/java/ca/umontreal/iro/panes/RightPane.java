package ca.umontreal.iro.panes;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RightPane extends VBox {
    /**
     * Detail section of the GUI
     */
    public RightPane() {
        // XXX No need for button to calculate the metrics, just auto calculate them
        // XXX Revoir padding pis le fait que RightPane et LeftPane sont des VBox et leurs enfants
        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setPadding(new Insets(0, 8, 0, 0));

        HBox hBox = new HBox(separator, new ListView<String>());
        setVgrow(hBox, Priority.ALWAYS);

        getChildren().addAll(new Label("Métriques"), hBox);
        setPadding(new Insets(8, 0, 8, 0));
    }
}
