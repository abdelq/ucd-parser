package ca.umontreal.iro.parser.tree;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.stream.Collectors.toList;

public class Operation {
    public final String id;
    public final List<Argument> arguments;
    public final String type;

    public Operation(String id, Stream<Argument> arguments, String type) {
        this.id = id;
        this.arguments = arguments.collect(toList());
        this.type = type;
    }

    @Override
    public String toString() {
        List<String> args = arguments.stream().map(DataItem::toString).collect(toList());
        return format("%s %s(%s)", type, id, join(", ", args));
    }

    String details() {
        return format("    %s(%s) : %s", id, join(", ", arguments.stream().map(Argument::details).collect(toList())), type);
    }
}
