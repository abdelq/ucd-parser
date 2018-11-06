package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.tree.ClassDeclaration;

import static java.lang.String.format;

public class CAC implements Metric {
    public int metric;

    public CAC(ClassDeclaration declaration) {
        metric = declaration.getAssociations().size() +
                 declaration.getAggregations().size(); // Locales

        var item = declaration.treeItem;
        while ((item = item.getParent()) != null) {
            if (item.getValue() != null) {
                metric += item.getValue().getAssociations().size() +
                        item.getValue().getAggregations().size(); // Héritées
            }
        }
    }

    public String getDescription() {
        return "Nombre d'associations (incluant les agrégations) locales/héritées " +
                "auxquelles participe la classe.";
    }

    public Number getValue() {
        return metric;
    }

    @Override
    public String toString() {
        return format("CAC : %d", metric);
    }
}
