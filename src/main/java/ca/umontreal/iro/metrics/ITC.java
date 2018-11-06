package ca.umontreal.iro.metrics;

import ca.umontreal.iro.App;
import ca.umontreal.iro.parser.tree.ClassDeclaration;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class ITC implements Metric {
    public long metric;

    public ITC(ClassDeclaration declaration) {
        var types = App.getModel().getClasses()
                .filter(decl -> decl != declaration)
                .map(decl -> decl.id)
                .collect(toList());
        metric = declaration.getOperations().stream()
                .flatMap(op -> op.arguments.stream().map(arg -> arg.type))
                .filter(types::contains)
                .count(); // XXX
    }

    public String getDescription() {
        return "Nombre de fois où d'autres classes du diagramme apparaissent " +
                "comme types des arguments des méthodes de la classe.";
    }

    public Number getValue() {
        return metric;
    }

    @Override
    public String toString() {
        return format("ITC : %d", metric);
    }
}
