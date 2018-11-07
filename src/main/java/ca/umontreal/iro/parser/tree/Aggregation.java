package ca.umontreal.iro.parser.tree;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class Aggregation implements Declaration {
    private final Role container;
    private final List<Role> parts;

    public Aggregation(Role container, Stream<Role> parts) {
        this.container = container;
        this.parts = parts.collect(toList());
    }

    /**
     * Compares the class identifier for container and parts to another.
     *
     * @param id identifier to compare
     * @return if the identifier is present
     */
    boolean matches(String id) {
        return container.matches(id) ||
                parts.parallelStream().anyMatch(part -> part.matches(id));
    }

    @Override
    public String toString() {
        return format("%s - %s", container, parts);
    }
}
