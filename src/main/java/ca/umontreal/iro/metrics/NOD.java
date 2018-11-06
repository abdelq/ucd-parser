package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.tree.ClassDeclaration;
import javafx.scene.control.TreeItem;

import static java.lang.String.format;

public class NOD implements Metric {
    public int metric;

    public NOD(ClassDeclaration declaration) {
        metric = numChildren(declaration.treeItem);
    }

    private int numChildren(TreeItem<ClassDeclaration> treeItem) {
        var children = treeItem.getChildren();
        /*if (children.size() == 0) {
            return 0;
        }*/
        return children.size() + children.stream().mapToInt(this::numChildren).sum(); // XXX
    }

    public String getDescription() {
        return "Nombre de sous-classes directes et indirectes de la classe.";
    }

    public Number getValue() {
        return metric;
    }

    @Override
    public String toString() {
        return format("NOD : %d", metric);
    }
}
