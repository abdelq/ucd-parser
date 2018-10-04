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
    public static final ListView<Attribute> attributes = new ListView<>();
    public static final ListView<Operation> operations = new ListView<>();
    public static final ListView<Association> associations = new ListView<>();
    public static final ListView<Aggregation> aggregations = new ListView<>();

    public CenterPane() {
        setPadding(new Insets(8));
        setHgap(8);
        setVgap(8);

        VBox attributesBox = new VBox(new Label("Attributs"), attributes);
        add(attributesBox, 0, 0);
        setHgrow(attributesBox, Priority.ALWAYS);
        setVgrow(attributesBox, Priority.ALWAYS);

        VBox operationsBox = new VBox(new Label("Méthodes"), operations);
        add(operationsBox, 1, 0);
        setHgrow(operationsBox, Priority.ALWAYS);
        setVgrow(operationsBox, Priority.ALWAYS);

        VBox associationsBox = new VBox(new Label("Associations"), associations);
        add(associationsBox, 0, 1);
        setHgrow(associationsBox, Priority.ALWAYS);
        setVgrow(associationsBox, Priority.ALWAYS);

        VBox aggregationsBox = new VBox(new Label("Aggrégations"), aggregations);
        add(aggregationsBox, 1, 1);
        setHgrow(aggregationsBox, Priority.ALWAYS);
        setVgrow(aggregationsBox, Priority.ALWAYS);
    }
}
