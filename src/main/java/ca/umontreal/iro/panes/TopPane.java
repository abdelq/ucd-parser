package ca.umontreal.iro.panes;

import ca.umontreal.iro.App;
import ca.umontreal.iro.parser.Parser;
import ca.umontreal.iro.parser.tree.ClassDeclaration;
import ca.umontreal.iro.parser.tree.Model;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("UCD Files", "*.ucd"),
                new ExtensionFilter("All Files", "*.*")
        );

        getMenus().addAll(createFileMenu(), createMetricsMenu());
    }

    /**
     * Creates the File menu.
     *
     * @return file menu
     */
    private Menu createFileMenu() {
        var openItem = new MenuItem("Ouvrir");
        openItem.setAccelerator(keyCombination("Ctrl+O"));
        openItem.setOnAction(e -> open());

        var clearItem = new MenuItem("Fermer");
        clearItem.setAccelerator(keyCombination("Ctrl+W"));
        clearItem.setOnAction(e -> close());

        var separatorItem = new SeparatorMenuItem();

        var exitItem = new MenuItem("Quitter");
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
        var file = fileChooser.showOpenDialog(getScene().getWindow());
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
        var treeItems = model.classes.map(TreeItem::new).collect(toList());

        model.generalizations.forEach(gen -> {
            var children = treeItems.parallelStream().filter(item ->
                    gen.subclasses.contains(item.getValue().id)
            ).collect(toList());
            treeItems.removeIf(children::contains);

            var parent = treeItems.parallelStream().filter(item ->
                    classExists(gen.id, item)
            ).findAny().get(); // XXX
            parent.setExpanded(true);
            parent.getChildren().addAll(children);
        });

        App.setModel(model);
        LeftPane.classes.getChildren().setAll(treeItems);
        CenterPane.clear();
        RightPane.clear();
        BottomPane.clear();
    }

    /**
     * Recursive lookup for an identifier in a tree of class declarations.
     *
     * @param id identifier to look for
     * @param item tree item to look in
     * @return if a class declaration with the identifier exists
     */
    private boolean classExists(String id, TreeItem<ClassDeclaration> item) {
        if (item.getValue().id.equals(id)) {
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
    }

    /**
     * Creates the Metrics menu.
     *
     * @return metrics menu
     */
    private Menu createMetricsMenu() {
        var exportItem = new MenuItem("Exporter");
        exportItem.setOnAction(e -> export());

        return new Menu("Métriques", null, exportItem);
    }

    /**
     * Generates a CSV file with metrics for each class.
     */
    private void export() {
        //App.getModel().classes.map(decl -> decl.id + "," + decl.getMetrics()); // TODO
    }
}
