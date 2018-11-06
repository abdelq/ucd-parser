package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.tree.ClassDeclaration;

import static java.lang.String.format;

public class ANA implements Metric {
    public double metric;

    public ANA(ClassDeclaration declaration) {
        var avg = declaration.getOperations().stream().mapToInt(op -> op.arguments.size()).average();
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
