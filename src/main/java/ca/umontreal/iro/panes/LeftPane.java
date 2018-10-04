package ca.umontreal.iro.panes;

import ca.umontreal.iro.App;
import ca.umontreal.iro.parser.tree.Aggregation;
import ca.umontreal.iro.parser.tree.Association;
import ca.umontreal.iro.parser.tree.ClassDecl;
import ca.umontreal.iro.parser.tree.Role;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;

public class LeftPane extends HBox {
    public static final TreeItem<ClassDecl> classes = new TreeItem<>(new ClassDecl("Classes"));

    public LeftPane() {
        TreeView<ClassDecl> treeView = new TreeView<>(classes);
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (App.model == null)
                return;

            CenterPane.attributes.getItems().setAll(newValue.getValue().attributes);
            CenterPane.operations.getItems().setAll(newValue.getValue().operations);

            // XXX
            CenterPane.associations.getItems().clear();
            for (Association assoc : App.model.getDeclarationsOf(Association.class)) {
                if (assoc.firstRole.id.equals(newValue.getValue().id))
                    CenterPane.associations.getItems().add(assoc);
                else if (assoc.secondRole.id.equals(newValue.getValue().id))
                    CenterPane.associations.getItems().add(assoc);
            }

            // XXX
            CenterPane.aggregations.getItems().clear();
            for (Aggregation aggr : App.model.getDeclarationsOf(Aggregation.class)) {
                if (aggr.container.id.equals(newValue.getValue().id)) {
                    CenterPane.aggregations.getItems().add(aggr);
                    continue;
                }
                for (Role part : aggr.parts) {
                    if (part.id.equals(newValue.getValue().id))
                        CenterPane.aggregations.getItems().add(aggr);
                }
            }
        });

        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setPadding(new Insets(0, 0, 0, 8));

        getChildren().addAll(treeView, separator);
        setPadding(new Insets(8, 0, 8, 8));
    }
}
