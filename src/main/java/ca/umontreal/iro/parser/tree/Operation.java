package ca.umontreal.iro.parser.tree;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Operation {
    private final String id;
    private final List<Argument> arguments;
    private final String type;

    public Operation(String id, Stream<Argument> arguments, String type) {
        this.id = id;
        this.arguments = arguments.collect(toList());
        this.type = type;
    }

    public Stream<Argument> getArguments() {
        return arguments.parallelStream();
    }

    /**
     * Formatted string corresponding to the operation in the details section.
     *
     * @return detail string for operation
     */
    String details() {
        String args = arguments.stream().map(Argument::details).collect(joining(", "));
        return format("    %s(%s) : %s", id, args, type);
    }

    @Override
    public String toString() {
        String args = arguments.stream().map(Argument::toString).collect(joining(", "));
        return format("%s %s(%s)", type, id, args);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Operation op = (Operation) obj;
        return Objects.equals(id, op.id) &&
                Objects.equals(type, op.type) &&
                Objects.equals(arguments, op.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, arguments, type);
    }
}
