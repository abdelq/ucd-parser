package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.tree.ClassDeclaration;
import javafx.scene.control.TreeItem;

import static java.lang.String.format;

public class CLD implements Metric {
    public int metric;

    public CLD(ClassDeclaration declaration) {
        metric = maxDepth(declaration.treeItem);
    }

    private int maxDepth(TreeItem<ClassDeclaration> treeItem) {
        var children = treeItem.getChildren();
        if (children.size() == 0) {
            return 0;
        }

        var depths = children.stream().mapToInt(this::maxDepth);
        return depths.max().getAsInt() + 1; // XXX
    }

    public String getDescription() {
        return "Taille du chemin le plus long reliant la classe à " +
                "une classe feuille dans le graphe d'héritage.";
    }

    public Number getValue() {
        return metric;
    }

    @Override
    public String toString() {
        return format("CLD : %d", metric);
    }
}
