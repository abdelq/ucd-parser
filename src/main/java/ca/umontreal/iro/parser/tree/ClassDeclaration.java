package ca.umontreal.iro.parser.tree;

import ca.umontreal.iro.App;
import ca.umontreal.iro.metrics.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.join;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.toList;

public class ClassDeclaration implements Declaration {
    public final String id;
    private final List<Attribute> attributes;
    private final List<Operation> operations;

    private List<Association> associations;
    private List<Aggregation> aggregations;
    private StringBuilder details;
    private List<Metric> metrics;

    public ClassDeclaration(String id, Stream<Attribute> attributes, Stream<Operation> operations) {
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
        if (associations == null) {
            associations = App.getModel().associations.filter(asso ->
                    asso.firstRole.id.equals(id) || asso.secondRole.id.equals(id)
            ).collect(toList());
        }
        return associations;
    }

    public List<Aggregation> getAggregations() {
        if (aggregations == null) {
            aggregations = App.getModel().aggregations.filter(aggr ->
                    aggr.container.id.equals(id) || aggr.parts.parallelStream().anyMatch(part -> part.id.equals(id))
            ).collect(toList());
        }
        return aggregations;
    }

    public String getDetails() {
        if (details == null) {
            details = new StringBuilder();

            details.append("CLASS ").append(id).append(lineSeparator());

            details.append("ATTRIBUTES").append(lineSeparator()).append(join(
                    "," + lineSeparator(),
                    attributes.parallelStream().map(Attribute::details).collect(toList())
            ));
            if (attributes.size() > 0) {
                details.append(lineSeparator());
            }

            details.append("OPERATIONS").append(lineSeparator()).append(join(
                    "," + lineSeparator(),
                    operations.parallelStream().map(Operation::details).collect(toList())
            ));
            if (operations.size() > 0) {
                details.append(lineSeparator());
            }

            details.append(lineSeparator()).append(";");
        }

        return details.toString();
    }

    public List<Metric> getMetrics() {
        if (metrics == null) {
            metrics = Arrays.asList(new ANA(this), new NOM(this),
                    new NOA(this), new ITC(this), new ETC(this), new CAC(this),
                    new DIT(this), new CLD(this), new NOC(this), new NOD(this));
        }
        return metrics;
    }
}
