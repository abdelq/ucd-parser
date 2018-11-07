package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.tree.ClassDeclaration;

import java.util.OptionalDouble;

import static java.lang.String.format;

public class ANA implements Metric {
    public double metric;

    public ANA(ClassDeclaration declaration) {
        OptionalDouble avg = declaration.getOperations().parallelStream()
                .mapToInt(op -> op.getArguments().size()).average();
        if (avg.isPresent()) {
            metric = avg.getAsDouble();
        }
    }

    public String getDescription() {
        return "Nombre moyen d'arguments des méthodes locales pour la classe.";
    }

    public Number getValue() {
        return metric;
    }

    @Override
    public String toString() {
        return format("ANA : %.2f", metric);
    }
}
