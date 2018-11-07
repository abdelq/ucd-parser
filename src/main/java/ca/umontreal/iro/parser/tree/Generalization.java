package ca.umontreal.iro.parser.tree;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Generalization implements Declaration {
    private final String id;
    private final List<String> subclasses;

    public Generalization(String id, Stream<String> subclasses) {
        this.id = id;
        this.subclasses = subclasses.collect(toList());
    }

    public String getId() {
        return id;
    }

    public List<String> getSubclasses() {
        return subclasses;
    }
}
