package ca.umontreal.iro.parser.tree;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class Aggregation implements Declaration {
    public final Role container;
    public final List<Role> parts;

    public Aggregation(Role container, Stream<Role> parts) {
        this.container = container;
        this.parts = parts.collect(toList());
    }

    @Override
    public String toString() {
        return format("%s - %s", container, parts);
    }
}
