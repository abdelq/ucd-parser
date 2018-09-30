package ca.umontreal.iro.parser.tree;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ClassDecl implements Declaration {
    public String id;
    public List<Attribute> attributes;
    public List<Operation> operations;

    public ClassDecl(String id, Stream<Attribute> attributes, Stream<Operation> operations) {
        this.id = id;
        this.attributes = attributes.collect(toList());
        this.operations = operations.collect(toList());
    }
}
