package ca.umontreal.iro.parser.tree;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Operation {
    public String id;
    public List<Argument> arguments;
    public String type;

    public Operation(String id, Stream<Argument> arguments, String type) {
        this.id = id;
        this.arguments = arguments.collect(toList());
        this.type = type;
    }
}