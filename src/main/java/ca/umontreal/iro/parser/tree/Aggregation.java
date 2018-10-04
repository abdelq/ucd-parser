package ca.umontreal.iro.parser.tree;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Aggregation implements Declaration {
    public Role container;
    public List<Role> parts;

    public Aggregation(Role container, Stream<Role> parts) {
        this.container = container;
        this.parts = parts.collect(toList());
    }

    @Override
    public String toString() {
        return container + " - " + parts;
    }
}
