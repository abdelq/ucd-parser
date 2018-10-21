package ca.umontreal.iro.panes;

import ca.umontreal.iro.metrics.Metric;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import static javafx.scene.control.Alert.AlertType;

public class RightPane extends VBox {
    /**
     * Metrics section of the GUI.
     */
    static final ListView<Metric> metrics = new ListView<>();
    /**
     * Information dialog about the selected metric.
     */
    private final Alert alert = new Alert(AlertType.INFORMATION);

    public RightPane() {
        metrics.setOnMouseClicked(event -> {
            var metric = metrics.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2 && metric != null) {
                alert.setTitle(metric.getClass().getSimpleName());
                alert.setHeaderText(metric.getName());
                alert.setContentText(metric.getDescription());
                alert.show();
            }
        });
        setVgrow(metrics, Priority.ALWAYS);

        getChildren().addAll(new Label("Métriques"), metrics);
        setPadding(new Insets(8));
    }

    /**
     * Clears metrics.
     */
    static void clear() {
        metrics.getItems().clear();
    }
}
