package ca.umontreal.iro.parser.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Model {
    public final String id;

    public List<ClassDeclaration> classes = new ArrayList<>();
    public List<Generalization> generalizations = new ArrayList<>();
    List<Association> associations = new ArrayList<>();
    List<Aggregation> aggregations = new ArrayList<>();

    public Model(String id, Stream<Declaration> declarations) {
        this.id = id;

        for (var declaration : declarations.collect(toList())) {
            if (declaration instanceof ClassDeclaration) {
                classes.add((ClassDeclaration) declaration);
            } else if (declaration instanceof Association) {
                associations.add((Association) declaration);
            } else if (declaration instanceof Aggregation) {
                aggregations.add((Aggregation) declaration);
            } else if (declaration instanceof Generalization) {
                generalizations.add((Generalization) declaration);
            }
        }
    }
}
