package ca.umontreal.iro.panes;

import ca.umontreal.iro.parser.tree.Aggregation;
import ca.umontreal.iro.parser.tree.Association;
import ca.umontreal.iro.parser.tree.Attribute;
import ca.umontreal.iro.parser.tree.Operation;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CenterPane extends GridPane {
    public static ListView<Attribute> attributes = new ListView<>();
    public static ListView<Operation> operations = new ListView<>();
    public static ListView<Association> associations = new ListView<>();
    public static ListView<Aggregation> aggregations = new ListView<>();

    public CenterPane() {
        setPadding(new Insets(8));
        setHgap(8);
        setVgap(8);

        // TODO Resize to full size

        add(new VBox(new Label("Attributs"), attributes), 0, 0);
        add(new VBox(new Label("Méthodes"), operations), 1, 0);
        add(new VBox(new Label("Associations"), associations), 0, 1);
        add(new VBox(new Label("Aggrégations"), aggregations), 1, 1);
    }
}
