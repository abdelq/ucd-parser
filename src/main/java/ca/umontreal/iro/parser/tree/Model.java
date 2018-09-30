package ca.umontreal.iro.parser.tree;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Model {
    public String id;
    public List<Declaration> declarations;

    public Model(String id, Stream<Declaration> declarations) {
        this.id = id;
        this.declarations = declarations.collect(toList());
    }
}
