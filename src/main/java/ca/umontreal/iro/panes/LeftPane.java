package ca.umontreal.iro.panes;

import ca.umontreal.iro.parser.tree.ClassDecl;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LeftPane extends VBox {
    /**
     * Class section of the GUI
     */
    static final TreeItem<ClassDecl> classes = new TreeItem<>();

    public LeftPane() {
        TreeView<ClassDecl> treeView = new TreeView<>(classes);
        treeView.setShowRoot(false);
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ClassDecl decl = newValue.getValue();

                CenterPane.attributes.getItems().setAll(decl.getAttributes());
                CenterPane.operations.getItems().setAll(decl.getOperations());
                CenterPane.associations.getItems().setAll(decl.getAssociations());
                CenterPane.aggregations.getItems().setAll(decl.getAggregations());
                BottomPane.details.setText(decl.getDetails());
            }
        });

        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setPadding(new Insets(0, 0, 0, 8));

        HBox hBox = new HBox(treeView, separator);
        setVgrow(hBox, Priority.ALWAYS);

        getChildren().addAll(new Label("Classes"), hBox);
        setPadding(new Insets(8, 0, 8, 8));
    }
}
