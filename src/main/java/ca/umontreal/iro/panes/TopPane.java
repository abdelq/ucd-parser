package ca.umontreal.iro.panes;

import ca.umontreal.iro.App;
import ca.umontreal.iro.parser.Parser;
import ca.umontreal.iro.parser.tree.ClassDecl;
import ca.umontreal.iro.parser.tree.Generalization;
import ca.umontreal.iro.parser.tree.Model;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javafx.application.Platform.exit;
import static javafx.scene.control.Alert.AlertType;

public class TopPane extends MenuBar {
    private final FileChooser fileChooser = new FileChooser();
    private final Parser parser = new Parser();

    public TopPane() {
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("UCD Files", "*.ucd"),
                new ExtensionFilter("All Files", "*.*")
        );

        getMenus().addAll(createFileMenu()/*, createViewMenu()*/);
    }

    /**
     * Creates the File menu.
     *
     * @return file menu
     */
    private Menu createFileMenu() {
        Menu menu = new Menu("Fichier");

        MenuItem openItem = new MenuItem("Ouvrir");
        openItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        openItem.setOnAction(event -> open());

        MenuItem clearItem = new MenuItem("Fermer");
        clearItem.setAccelerator(KeyCombination.keyCombination("Ctrl+W"));
        clearItem.setOnAction(event -> clear());

        MenuItem separatorItem = new SeparatorMenuItem();

        MenuItem exitItem = new MenuItem("Quitter");
        exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        exitItem.setOnAction(event -> exit());

        menu.getItems().addAll(openItem, clearItem, separatorItem, exitItem);

        return menu;
    }

    /**
     * Creates the View menu.
     *
     * @return view menu
     */
    private Menu createViewMenu() {
        Menu menu = new Menu("Affichage");

        CheckMenuItem attributesItem = new CheckMenuItem("Attributs");
        attributesItem.setSelected(true);
        attributesItem.setOnAction(e -> {
            // TODO
        });

        CheckMenuItem operationsItem = new CheckMenuItem("Méthodes");
        operationsItem.setSelected(true);
        operationsItem.setOnAction(e -> {
            // TODO
        });

        CheckMenuItem associationsItem = new CheckMenuItem("Associations");
        associationsItem.setSelected(true);
        associationsItem.setOnAction(e -> {
            // TODO
        });

        CheckMenuItem aggregationsItem = new CheckMenuItem("Aggrégations");
        aggregationsItem.setSelected(true);
        aggregationsItem.setOnAction(e -> {
            // TODO
        });

        CheckMenuItem detailsItem = new CheckMenuItem("Détails");
        detailsItem.setSelected(true);
        detailsItem.setOnAction(e -> {
            // TODO
        });

        menu.getItems().addAll(attributesItem, operationsItem,
                associationsItem, aggregationsItem,
                detailsItem);

        return menu;
    }

    /**
     * Opens a new file from the dialog and updates the interface.
     */
    private void open() {
        // Open the dialog
        File file = fileChooser.showOpenDialog(getScene().getWindow());
        if (file == null)
            return;

        // Parse the file
        Model model;
        try {
            model = parser.parseModel(file);
        } catch (IOException e) {
            new Alert(
                    AlertType.ERROR,
                    String.format("%s: %s", e.getClass().getSimpleName(), e.getMessage())
            ).show();
            return;
        } catch (Exception e) {
            return; // XXX
        }

        // Create the class tree
        // FIXME What about generalization of generalization
        Collection<TreeItem<ClassDecl>> treeItems = new ArrayList<>();
        List<ClassDecl> classesInTree = new ArrayList<>();

        for (Generalization gen : model.generalizations) {
            for (ClassDecl cls : model.classes) {
                if (cls.id.equals(gen.id)) {
                    TreeItem<ClassDecl> treeItem = new TreeItem<>(cls);
                    for (ClassDecl subcls : model.classes) {
                        if (gen.subclasses.contains(subcls.id)) {
                            treeItem.getChildren().add(new TreeItem<>(subcls));
                            classesInTree.add(subcls);
                        }
                    }
                    treeItem.setExpanded(true);
                    treeItems.add(treeItem);
                    classesInTree.add(cls);
                    break;
                }
            }
        }

        for (ClassDecl cls : model.classes) {
            if (!classesInTree.contains(cls))
                treeItems.add(new TreeItem<>(cls));
        }

        // Update the interface
        App.model = model;
        ((Stage) getScene().getWindow()).setTitle(App.model.id + " - UCD Parser"); // XXX

        LeftPane.classes.getChildren().addAll(treeItems);
        CenterPane.attributes.getItems().clear();
        CenterPane.operations.getItems().clear();
        CenterPane.associations.getItems().clear();
        CenterPane.aggregations.getItems().clear();
        BottomPane.details.clear();
    }

    /**
     * Clears the interface.
     */
    private void clear() {
        App.model = null;
        ((Stage) getScene().getWindow()).setTitle("UCD Parser"); // XXX

        LeftPane.classes.getChildren().clear();
        CenterPane.attributes.getItems().clear();
        CenterPane.operations.getItems().clear();
        CenterPane.associations.getItems().clear();
        CenterPane.aggregations.getItems().clear();
        BottomPane.details.clear();
    }
}
