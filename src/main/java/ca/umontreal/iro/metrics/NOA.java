package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.tree.ClassDeclaration;

import static java.lang.String.format;

public class NOA implements Metric {
    public int metric;

    public NOA(ClassDeclaration declaration) {
        metric = declaration.getAttributes().size(); // Locaux

        var item = declaration.treeItem;
        while ((item = item.getParent()) != null) {
            if (item.getValue() != null) {
                metric += item.getValue().getAttributes().size(); // Hérités
            }
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
