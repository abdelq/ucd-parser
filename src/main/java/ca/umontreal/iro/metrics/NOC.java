package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.tree.ClassDeclaration;

import static java.lang.String.format;

public class NOC implements Metric {
    public int metric;

    public NOC(ClassDeclaration declaration) {
        metric = declaration.getTreeItem().getChildren().size();
    }

    public String getDescription() {
        return "Nombre de sous-classes directes de la classe.";
    }

    public Number getValue() {
        return metric;
    }

    @Override
    public String toString() {
        return format("NOC : %d", metric);
    }
}
