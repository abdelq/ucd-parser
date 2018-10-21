package ca.umontreal.iro.panes;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class BottomPane extends VBox {
    /**
     * Detail section of the GUI.
     */
    static final TextArea details = new TextArea();

    public BottomPane() {
        details.setEditable(false);
        details.setPrefRowCount(24);

        getChildren().addAll(new Label("Détails"), details);
        setPadding(new Insets(8));
    }

    /**
     * Clears details.
     */
    static void clear() {
        details.clear();
    }
}
