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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Platform.exit;
import static javafx.scene.control.Alert.AlertType;

public class TopPane extends MenuBar {
    private FileChooser fileChooser = openFileChooser();

    public TopPane() {
        getMenus().addAll(fileMenu()/*, viewMenu()*/);
    }

    private FileChooser openFileChooser() {
        var chooser = new FileChooser();
        chooser.setTitle("Open File");
        chooser.getExtensionFilters().addAll(
                new ExtensionFilter("UCD Files", "*.ucd"),
                new ExtensionFilter("All Files", "*.*")
        );
        return chooser;
    }

    private Menu fileMenu() {
        var menu = new Menu("Fichier");

        var openItem = new MenuItem("Ouvrir");
        openItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        openItem.setOnAction(event -> open());

        var closeItem = new MenuItem("Fermer");
        closeItem.setAccelerator(KeyCombination.keyCombination("Ctrl+W"));
        closeItem.setOnAction(event -> close());

        var separatorItem = new SeparatorMenuItem();

        var exitItem = new MenuItem("Quitter");
        exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        exitItem.setOnAction(event -> exit());

        menu.getItems().addAll(openItem, closeItem, separatorItem, exitItem);

        return menu;
    }

    private Menu viewMenu() {
        var menu = new Menu("Affichage");

        var attributesItem = new CheckMenuItem("Attributs");
        attributesItem.setSelected(true);
        attributesItem.setOnAction(e -> {
            // TODO
        });

        var operationsItem = new CheckMenuItem("Méthodes");
        operationsItem.setSelected(true);
        operationsItem.setOnAction(e -> {
            // TODO
        });

        var associationsItem = new CheckMenuItem("Associations");
        associationsItem.setSelected(true);
        associationsItem.setOnAction(e -> {
            // TODO
        });

        var aggregationsItem = new CheckMenuItem("Aggrégations");
        aggregationsItem.setSelected(true);
        aggregationsItem.setOnAction(e -> {
            // TODO
        });

        menu.getItems().addAll(attributesItem, operationsItem, associationsItem, aggregationsItem);

        return menu;
    }

    private void open() {
        File file = fileChooser.showOpenDialog(getScene().getWindow());
        if (file != null) {
            try {
                App.model = Parser.parseFile(file);
                LeftPane.classes.setExpanded(true);

                // XXX
                var trees = new ArrayList<TreeItem<ClassDecl>>();

                List<Generalization> generalizations = App.model.getDeclarationsOf(Generalization.class);
                List<ClassDecl> classes = App.model.getDeclarationsOf(ClassDecl.class);
                List<ClassDecl> classesInTree = new ArrayList<>();

                for (Generalization generalization : generalizations) {
                    for (ClassDecl classDecl : classes) {
                        if (classDecl.id.equals(generalization.id)) {
                            var treeItem = new TreeItem<>(classDecl);
                            for (ClassDecl subclass : classes) {
                                if (generalization.subclasses.contains(subclass.id)) {
                                    treeItem.getChildren().add(new TreeItem<>(subclass));
                                    classesInTree.add(subclass);
                                }
                            }
                            treeItem.setExpanded(true);
                            trees.add(treeItem);
                            classesInTree.add(classDecl);
                            break;
                        }
                    }
                }

                for (ClassDecl classDecl : classes) {
                    if (!classesInTree.contains(classDecl))
                        trees.add(new TreeItem<>(classDecl));
                }

                LeftPane.classes.getChildren().addAll(trees);
            } catch (IOException e) {
                new Alert(AlertType.ERROR, String.format("%s: %s", e.getClass().getSimpleName(), e.getMessage())).show();
            }
        }
    }

    private void close() {
        LeftPane.classes.getChildren().clear();
        CenterPane.attributes.getItems().clear();
        CenterPane.operations.getItems().clear();
        CenterPane.associations.getItems().clear();
        CenterPane.aggregations.getItems().clear();
    }
}