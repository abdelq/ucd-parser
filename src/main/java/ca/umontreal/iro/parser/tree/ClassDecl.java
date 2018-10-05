package ca.umontreal.iro.parser.tree;

import ca.umontreal.iro.App;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ClassDecl implements Declaration {
    public final String id;
    private final List<Attribute> attributes;
    private final List<Operation> operations;

    public ClassDecl(String id, Stream<Attribute> attributes, Stream<Operation> operations) {
        this.id = id;
        this.attributes = attributes.collect(toList());
        this.operations = operations.collect(toList());
    }

    @Override
    public String toString() {
        return id;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public List<Association> getAssociations() {
        return App.model.associations.stream().filter(asso ->
                asso.firstRole.id.equals(id) || asso.secondRole.id.equals(id)
        ).collect(toList());
    }

    public List<Aggregation> getAggregations() {
        return App.model.aggregations.stream().filter(aggr ->
                aggr.container.id.equals(id) ||
                        aggr.parts.stream().anyMatch(part -> part.id.equals(id))
        ).collect(toList());
    }

    public String getDetails() { // XXX
        StringBuilder builder = new StringBuilder();

        builder.append("CLASS " + id + System.lineSeparator());

        builder.append("ATTRIBUTES" + System.lineSeparator());
        builder.append(String.join(
                "," + System.lineSeparator(),
                attributes.stream().map(attr ->
                        String.format("    %s : %s", attr.id, attr.type)
                ).collect(toList())
        ));

        builder.append(System.lineSeparator() + "OPERATIONS" + System.lineSeparator());
        builder.append(String.join(
                "," + System.lineSeparator(),
                operations.stream().map(op -> {
                            List<String> args = op.arguments.stream().map(arg -> arg.id + " : " + arg.type).collect(toList());
                            return String.format("    %s(%s) : %s", op.id, String.join(", ", args), op.type);
                        }
                ).collect(toList())
        ));

        builder.append(System.lineSeparator() + ";");

        return builder.toString();
    }
}
