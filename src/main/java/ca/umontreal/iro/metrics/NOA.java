package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.tree.ClassDeclaration;
import javafx.scene.control.TreeItem;

import static java.lang.String.format;

public class NOA implements Metric {
    public int metric;

    public NOA(ClassDeclaration declaration) {
        TreeItem<ClassDeclaration> item = declaration.getTreeItem();
        while (item != null && item.getValue() != null) {
            metric += item.getValue().getAttributes().size();
            item = item.getParent();
        }
    }

    public String getDescription() {
        return "Nombre d'attributs locaux/hérités de la classe.";
    }

    public Number getValue() {
        return metric;
    }

    @Override
    public String toString() {
        return format("NOA : %d", metric);
    }
}
