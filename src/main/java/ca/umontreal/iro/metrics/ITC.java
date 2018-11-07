package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.tree.ClassDeclaration;
import ca.umontreal.iro.parser.tree.Operation;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class ITC implements Metric {
    public long metric;

    public ITC(ClassDeclaration declaration, Stream<ClassDeclaration> declarations) {
        List<String> types = declarations
                .filter(decl -> decl != declaration)
                .map(decl -> decl.id)
                .collect(toList());
        metric = declaration.getOperations().parallelStream()
                .flatMap(Operation::getArgumentsType)
                .filter(types::contains)
                .count();
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
