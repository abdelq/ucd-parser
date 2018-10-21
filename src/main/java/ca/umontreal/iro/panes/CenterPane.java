package ca.umontreal.iro.panes;

import ca.umontreal.iro.parser.tree.Aggregation;
import ca.umontreal.iro.parser.tree.Association;
import ca.umontreal.iro.parser.tree.Attribute;
import ca.umontreal.iro.parser.tree.Operation;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CenterPane extends GridPane {
    /**
     * Attribute section of the GUI.
     */
    static final ListView<Attribute> attributes = new ListView<>();
    /**
     * Operation section of the GUI.
     */
    static final ListView<Operation> operations = new ListView<>();
    /**
     * Association section of the GUI.
     */
    static final ListView<Association> associations = new ListView<>();
    /**
     * Aggregation section of the GUI.
     */
    static final ListView<Aggregation> aggregations = new ListView<>();

    public CenterPane() {
        var attributesBox = new VBox(new Label("Attributs"), attributes);
        add(attributesBox, 0, 0);
        setHgrow(attributesBox, Priority.ALWAYS);

        var operationsBox = new VBox(new Label("Méthodes"), operations);
        add(operationsBox, 1, 0);
        setHgrow(operationsBox, Priority.ALWAYS);

        var associationsBox = new VBox(new Label("Associations"), associations);
        add(associationsBox, 0, 1);
        setHgrow(associationsBox, Priority.ALWAYS);

        var aggregationsBox = new VBox(new Label("Aggrégations"), aggregations);
        add(aggregationsBox, 1, 1);
        setHgrow(aggregationsBox, Priority.ALWAYS);

        setHgap(8);
        setVgap(8);
        setPadding(new Insets(8, 0, 0, 0));
    }

    /**
     * Clears attributes, operations, associations and aggregations.
     */
    static void clear() {
        attributes.getItems().clear();
        operations.getItems().clear();
        associations.getItems().clear();
        aggregations.getItems().clear();
    }
}
