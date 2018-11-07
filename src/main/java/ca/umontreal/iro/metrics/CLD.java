package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.tree.ClassDeclaration;
import javafx.scene.control.TreeItem;

import java.util.List;
import java.util.OptionalInt;

import static java.lang.String.format;

public class CLD implements Metric {
    public int metric;

    public CLD(ClassDeclaration declaration) {
        metric = maxDepth(declaration.getTreeItem());
    }

    public String getDescription() {
        return "Taille du chemin le plus long reliant la classe à " +
                "une classe feuille dans le graphe d'héritage.";
    }

    public Number getValue() {
        return metric;
    }

    private int maxDepth(TreeItem<ClassDeclaration> treeItem) {
        List<TreeItem<ClassDeclaration>> children = treeItem.getChildren();
        if (children.size() == 0) {
            return 0;
        }

        OptionalInt maxDepth = children.parallelStream().mapToInt(this::maxDepth).max();
        return 1 + (maxDepth.isPresent() ? maxDepth.getAsInt() : 0);
    }

    @Override
    public String toString() {
        return format("CLD : %d", metric);
    }
}
