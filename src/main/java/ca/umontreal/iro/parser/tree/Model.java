package ca.umontreal.iro.parser.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Model {
    public final String id;

    public List<ClassDecl> classes = new ArrayList<>();
    List<Association> associations = new ArrayList<>();
    List<Aggregation> aggregations = new ArrayList<>();
    public List<Generalization> generalizations = new ArrayList<>();

    public Model(String id, Stream<Declaration> declarations) {
        this.id = id;
        for (Declaration decl : declarations.collect(toList())) {
            if (decl instanceof ClassDecl)
                classes.add((ClassDecl) decl);
            else if (decl instanceof Association)
                associations.add((Association) decl);
            else if (decl instanceof Aggregation)
                aggregations.add((Aggregation) decl);
            else if (decl instanceof Generalization)
                generalizations.add((Generalization) decl);
        }
    }
}
