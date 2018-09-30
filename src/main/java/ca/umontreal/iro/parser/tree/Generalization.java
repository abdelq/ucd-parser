package ca.umontreal.iro.parser.tree;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Generalization implements Declaration {
    public String id;
    public List<String> subclasses;

    public Generalization(String id, Stream<String> subclasses) {
        this.id = id;
        this.subclasses = subclasses.collect(toList());
    }
}
