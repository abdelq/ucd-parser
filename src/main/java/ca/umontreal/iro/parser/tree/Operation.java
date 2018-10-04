package ca.umontreal.iro.parser.tree;

import java.util.List;
import java.util.stream.Stream;

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

    @Override
    public String toString() {
        List<String> args =  arguments.stream().map(DataItem::toString).collect(toList());
        return String.format("%s %s(%s)", type, id, String.join(", ", args));
    }
}
