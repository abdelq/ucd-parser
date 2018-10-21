package ca.umontreal.iro.parser.tree;

import java.util.stream.Stream;

public class Model {
    public final String id;

    public Stream<ClassDeclaration> classes;
    public Stream<Generalization> generalizations;
    Stream<Association> associations;
    Stream<Aggregation> aggregations;

    public Model(String id, Stream<Declaration> declarations) {
        this.id = id;

        classes = declarations.filter(ClassDeclaration.class::isInstance).map(ClassDeclaration.class::cast);
        generalizations = declarations.filter(Generalization.class::isInstance).map(Generalization.class::cast);
        associations = declarations.filter(Association.class::isInstance).map(Association.class::cast);
        aggregations = declarations.filter(Aggregation.class::isInstance).map(Aggregation.class::cast);
    }
}
