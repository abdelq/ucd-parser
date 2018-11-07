package ca.umontreal.iro.parser.tree;

import ca.umontreal.iro.App;
import ca.umontreal.iro.metrics.*;
import javafx.scene.control.TreeItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.join;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class ClassDeclaration implements Declaration {
    public final String id; // TODO Private
    private final List<Attribute> attributes;
    private final List<Operation> operations;

    private List<Association> associations;
    private List<Aggregation> aggregations;
    private String details;
    private List<Metric> metrics;
    public TreeItem<ClassDeclaration> treeItem; // TODO Private

    public ClassDeclaration(String id, Stream<Attribute> attributes, Stream<Operation> operations) {
        this.id = id;
        this.attributes = attributes.collect(toList());
        this.operations = operations.collect(toList());

        this.treeItem = new TreeItem<>(this);
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public List<Association> getAssociations() {
        if (associations == null) {
            associations = App.getModel().getAssociations().filter(asso -> asso.matches(id)).collect(toList());
        }
        return associations;
    }

    public List<Aggregation> getAggregations() {
        if (aggregations == null) {
            aggregations = App.getModel().getAggregations().filter(aggr -> aggr.matches(id)).collect(toList());
        }
        return aggregations;
    }

    public String getDetails() {
        if (details == null) {
            StringBuilder builder = new StringBuilder();

            builder.append("CLASS ").append(id).append(lineSeparator());

            builder.append("ATTRIBUTES").append(lineSeparator()).append(
                    attributes.stream().map(Attribute::details)
                              .collect(joining("," + lineSeparator()))
            );
            if (attributes.size() > 0) {
                builder.append(lineSeparator());
            }

            builder.append("OPERATIONS").append(lineSeparator()).append(
                    operations.stream().map(Operation::details)
                              .collect(joining("," + lineSeparator()))
            );
            if (operations.size() > 0) {
                builder.append(lineSeparator());
            }

            builder.append(";");

            details = builder.toString();
        }
        return details;
    }

    public List<Metric> getMetrics() {
        if (metrics == null) {
            metrics = Arrays.asList(new ANA(this), new NOM(this),
                    new NOA(this), new ITC(this, App.getModel().getClasses()),
                    new ETC(this, App.getModel().getClasses()), new CAC(this),
                    new DIT(this), new CLD(this),
                    new NOC(this), new NOD(this));
        }
        return metrics;
    }


    /**
     * TODO.
     *
     * @param id identifier to compare
     * @return TODO
     */
    public boolean matches(String id) {
        return this.id.equals(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
