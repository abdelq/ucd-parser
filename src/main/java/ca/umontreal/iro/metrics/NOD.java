package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.tree.ClassDeclaration;
import javafx.scene.control.TreeItem;
import jdk.nashorn.api.tree.Tree;

import java.util.List;

import static java.lang.String.format;

public class NOD implements Metric {
    public int metric;

    public NOD(ClassDeclaration declaration) {
        metric = numChildren(declaration.treeItem);
    }

    public String getDescription() {
        return "Nombre de sous-classes directes et indirectes de la classe.";
    }

    public Number getValue() {
        return metric;
    }

    private int numChildren(TreeItem<ClassDeclaration> treeItem) {
        List<TreeItem<ClassDeclaration>> children = treeItem.getChildren();
        return children.size() + children.parallelStream().mapToInt(this::numChildren).sum();
    }

    @Override
    public String toString() {
        return format("NOD : %d", metric);
    }
}
