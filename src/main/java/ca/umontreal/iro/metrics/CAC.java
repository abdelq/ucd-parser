package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.tree.ClassDeclaration;
import javafx.scene.control.TreeItem;

import static java.lang.String.format;

public class CAC implements Metric {
    public int metric;

    public CAC(ClassDeclaration declaration) {
        TreeItem<ClassDeclaration> item = declaration.getTreeItem();
        while (item != null && item.getValue() != null) {
            metric += item.getValue().getAssociations().size() +
                    item.getValue().getAggregations().size();
            item = item.getParent();
        }
    }

    public String getDescription() {
        return "Nombre d'associations (incluant les agrégations) " +
                "locales/héritées auxquelles participe la classe.";
    }

    public Number getValue() {
        return metric;
    }

    @Override
    public String toString() {
        return format("CAC : %d", metric);
    }
}
