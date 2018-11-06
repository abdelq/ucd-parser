package ca.umontreal.iro.parser.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Model {
    public final String id;

    private List<ClassDeclaration> classes = new ArrayList<>();
    private List<Generalization> generalizations = new ArrayList<>();
    private List<Association> associations = new ArrayList<>();
    private List<Aggregation> aggregations = new ArrayList<>();

    public Model(String id, Stream<Declaration> declarations) {
        this.id = id;

        declarations.forEach(decl -> {
            if (decl instanceof ClassDeclaration) {
                classes.add((ClassDeclaration) decl);
            } else if (decl instanceof Generalization) {
                generalizations.add((Generalization) decl);
            } else if (decl instanceof Association) {
                associations.add((Association) decl);
            } else if (decl instanceof Aggregation) {
                aggregations.add((Aggregation) decl);
            }
        });
    }

    public Stream<ClassDeclaration> getClasses() {
        return classes.stream();
    }


    public Stream<Generalization> getGeneralizations() {
        return generalizations.stream();
    }


    public Stream<Association> getAssociations() {
        return associations.stream();
    }


    public Stream<Aggregation> getAggregations() {
        return aggregations.stream();
    }
}
