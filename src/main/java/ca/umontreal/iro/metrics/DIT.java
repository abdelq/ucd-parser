package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.tree.ClassDeclaration;

import static java.lang.String.format;

public class DIT implements Metric {
    public int metric;

    public DIT(ClassDeclaration declaration) {
        var item = declaration.treeItem;
        while ((item = item.getParent()) != null) {
            metric += 1; // XXX Possibly wrong because of the tree root
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
