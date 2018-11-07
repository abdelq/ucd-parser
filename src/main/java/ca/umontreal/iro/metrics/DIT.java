package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.tree.ClassDeclaration;
import javafx.scene.control.TreeItem;

import static java.lang.String.format;

public class DIT implements Metric {
    public int metric;

    public DIT(ClassDeclaration declaration) {
        // The root of the hierarchy used in the left pane is excluded
        TreeItem<ClassDeclaration> item = declaration.getTreeItem();
        while ((item = item.getParent()) != null && item.getParent() != null) {
            metric += 1;
        }
    }

    public String getDescription() {
        return "Taille du chemin le plus long reliant la classe à " +
                "une classe racine dans le graphe d'héritage.";
    }

    public Number getValue() {
        return metric;
    }

    @Override
    public String toString() {
        return format("DIT : %d", metric);
    }
}
