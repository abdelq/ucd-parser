package ca.umontreal.iro.metrics;

import ca.umontreal.iro.App;
import ca.umontreal.iro.parser.tree.ClassDeclaration;

import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class ETC implements Metric {
    public long metric;

    public ETC(ClassDeclaration declaration) {
        Stream<ClassDeclaration> otherClasses = App.getModel().getClasses().filter(decl -> decl != declaration);
        Stream<String> arguments = otherClasses.flatMap(decl ->
                decl.getOperations().parallelStream().flatMap(op ->
                        op.arguments.parallelStream().map(arg -> arg.type)
                )
        );
        metric = arguments.filter(arg -> arg.equals(declaration.id)).count();
    }

    public String getDescription() {
        return "Nombre de fois où la classe apparaît comme type des arguments " +
                "dans les méthodes des autres classes du diagramme.";
    }

    public Number getValue() {
        return metric;
    }

    @Override
    public String toString() {
        return format("ETC : %d", metric);
    }
}
