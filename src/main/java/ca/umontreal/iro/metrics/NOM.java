package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.tree.ClassDeclaration;
import ca.umontreal.iro.parser.tree.Operation;
import javafx.scene.control.TreeItem;

import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

public class NOM implements Metric {
    public int metric;

    public NOM(ClassDeclaration declaration) {
        HashSet<Operation> operations = new HashSet<>(declaration.getOperations()); // Locales

        TreeItem<ClassDeclaration> item = declaration.treeItem;
        while ((item = item.getParent()) != null) {
            if (item.getValue() != null) {
                operations.addAll(item.getValue().getOperations()); // Héritées
            }
        }

        metric = operations.size();
    }

    public String getDescription() {
        return "Nombre de méthodes locales/héritées de la classe. " +
                "Dans le cas où une méthode est héritée et redéfinie localement " +
                "(même nom, même ordre et types des arguments et même type de retour)" +
                ", elle ne compte qu'une fois.";
    }

    public Number getValue() {
        return metric;
    }

    @Override
    public String toString() {
        return format("NOM : %d", metric);
    }
}
