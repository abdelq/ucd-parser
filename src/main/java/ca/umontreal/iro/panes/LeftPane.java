package ca.umontreal.iro.panes;

import ca.umontreal.iro.parser.tree.ClassDeclaration;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LeftPane extends VBox {
    /**
     * Class section of the GUI.
     */
    static final TreeItem<ClassDeclaration> classes = new TreeItem<>();

    public LeftPane() {
        TreeView<ClassDeclaration> treeView = new TreeView<>(classes);
        treeView.setShowRoot(false);
        treeView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null && newValue != oldValue) {
                        ClassDeclaration declaration = newValue.getValue();

                        CenterPane.attributes.getItems().setAll(declaration.getAttributes());
                        CenterPane.operations.getItems().setAll(declaration.getOperations());
                        CenterPane.associations.getItems().setAll(declaration.getAssociations());
                        CenterPane.aggregations.getItems().setAll(declaration.getAggregations());
                        RightPane.metrics.getItems().setAll(declaration.getMetrics());
                        BottomPane.details.setText(declaration.getDetails());
                    }
                });
        setVgrow(treeView, Priority.ALWAYS);

        getChildren().addAll(new Label("Classes"), treeView);
        setPadding(new Insets(8));
    }

    /**
     * Clears class declarations.
     */
    static void clear() {
        classes.getChildren().clear();
    }
}
