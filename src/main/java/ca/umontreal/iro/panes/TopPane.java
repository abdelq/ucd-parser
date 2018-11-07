package ca.umontreal.iro.panes;

import ca.umontreal.iro.App;
import ca.umontreal.iro.parser.Parser;
import ca.umontreal.iro.parser.tree.ClassDeclaration;
import ca.umontreal.iro.parser.tree.Generalization;
import ca.umontreal.iro.parser.tree.Model;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static javafx.application.Platform.exit;
import static javafx.scene.control.Alert.AlertType;
import static javafx.scene.input.KeyCombination.keyCombination;

public class TopPane extends MenuBar {
    /**
     * Selector for UCD files.
     */
    private final FileChooser fileChooser = new FileChooser();
    /**
     * Parser for the UCD format.
     */
    private final Parser parser = new Parser();
    /**
     * Error dialog for failed parses.
     */
    private final Alert alert = new Alert(AlertType.ERROR);

    public TopPane() {
        getMenus().addAll(createFileMenu(), createMetricsMenu());
    }

    /**
     * Creates the File menu.
     *
     * @return file menu
     */
    private Menu createFileMenu() {
        MenuItem openItem = new MenuItem("Ouvrir");
        openItem.setAccelerator(keyCombination("Ctrl+O"));
        openItem.setOnAction(e -> open());

        MenuItem clearItem = new MenuItem("Fermer");
        clearItem.setAccelerator(keyCombination("Ctrl+W"));
        clearItem.setOnAction(e -> close());

        SeparatorMenuItem separatorItem = new SeparatorMenuItem();

        MenuItem exitItem = new MenuItem("Quitter");
        exitItem.setAccelerator(keyCombination("Ctrl+Q"));
        exitItem.setOnAction(e -> exit());

        return new Menu("Fichier", null,
                openItem, clearItem, separatorItem, exitItem);
    }

    /**
     * Sets a new use case model and updates the interface.
     */
    private void open() {
        // Open the dialog
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().setAll(
                new ExtensionFilter("UCD Files", "*.ucd"),
                new ExtensionFilter("All Files", "*.*")
        );

        File file = fileChooser.showOpenDialog(getScene().getWindow());
        if (file == null) {
            return;
        }

        // Parse the file
        Model model;
        try {
            model = parser.parseModel(file);
        } catch (Exception e) {
            alert.setHeaderText(file.getName());
            alert.setContentText(e.getMessage());
            alert.show();

            return;
        }

        // Generate the hierarchy tree of classes
        List<TreeItem<ClassDeclaration>> treeItems = model.getClasses().map(decl -> decl.treeItem).collect(toList());

        for (Generalization gen : model.getGeneralizations().collect(toList())) {
            // TODO Match all children or throw error
            List<TreeItem<ClassDeclaration>> children = treeItems.stream().filter(item ->
                    gen.getSubclasses().contains(item.getValue().id)
            ).collect(toList());
            treeItems.removeIf(children::contains);

            Optional<TreeItem<ClassDeclaration>> parent = treeItems.stream().filter(item ->
                    classExists(gen.getId(), item)
            ).findAny();
            if (!parent.isPresent()) {
                alert.setHeaderText(file.getName());
                alert.setContentText("Could not find parent class" + gen.getId());
                alert.show();

                return;
            }

            parent.get().setExpanded(true);
            parent.get().getChildren().addAll(children);
        }

        App.setModel(model);

        LeftPane.classes.getChildren().setAll(treeItems);
        CenterPane.clear();
        RightPane.clear();
        BottomPane.clear();

        getMenus().get(1).getItems().get(0).setDisable(false); // Enables export of metrics
    }

    /**
     * Recursive lookup for an identifier in a tree of class declarations.
     *
     * @param id   identifier to look for
     * @param item tree item to look in
     * @return if a class declaration with the identifier exists
     */
    private boolean classExists(String id, TreeItem<ClassDeclaration> item) {
        if (item.getValue().matches(id)) {
            return true;
        }

        return item.getChildren().parallelStream().anyMatch(child -> classExists(id, child));
    }

    /**
     * Removes the current use case model and clears the interface.
     */
    private void close() {
        App.setModel(null);

        LeftPane.clear();
        CenterPane.clear();
        RightPane.clear();
        BottomPane.clear();

        getMenus().get(1).getItems().get(0).setDisable(true); // Disables export of metrics
    }

    /**
     * Creates the Metrics menu.
     *
     * @return metrics menu
     */
    private Menu createMetricsMenu() {
        MenuItem exportItem = new MenuItem("Exporter");
        exportItem.setOnAction(e -> export());
        exportItem.setDisable(true);

        return new Menu("Métriques", null, exportItem);
    }

    /**
     * Generates a CSV file with metrics for each class.
     */
    private void export() {
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().setAll(
                new ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showSaveDialog(getScene().getWindow());
        if (file != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(App.getModel().getClasses().map(decl ->
                        decl.id + "," + decl.getMetrics().stream().map(metric -> {
                            if (metric.getValue() instanceof Double) {
                                return format("%.2f", metric.getValue().doubleValue());
                            }
                            return metric.getValue().toString();
                        }).collect(joining(","))
                ).collect(joining(lineSeparator())));
                writer.close();
            } catch (Exception e) {
                alert.setHeaderText(file.getName());
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
    }
}
