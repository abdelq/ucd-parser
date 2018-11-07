package ca.umontreal.iro.parser.tree;

import ca.umontreal.iro.metrics.*;
import javafx.scene.control.TreeItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static ca.umontreal.iro.App.getModel;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class ClassDeclaration implements Declaration {
    private final String id;
    private final List<Attribute> attributes;
    private final List<Operation> operations;

    private List<Association> associations;
    private List<Aggregation> aggregations;
    private String details;
    private List<Metric> metrics;
    private TreeItem<ClassDeclaration> treeItem;

    public ClassDeclaration(String id, Stream<Attribute> attributes, Stream<Operation> operations) {
        this.id = id;
        this.attributes = attributes.collect(toList());
        this.operations = operations.collect(toList());

        this.treeItem = new TreeItem<>(this);
    }

    public String getId() {
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
            associations = getModel().getAssociations()
                    .filter(asso -> asso.matches(id)).collect(toList());
        }
        return associations;
    }

    public List<Aggregation> getAggregations() {
        if (aggregations == null) {
            aggregations = getModel().getAggregations()
                    .filter(aggr -> aggr.matches(id)).collect(toList());
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
                    new NOA(this), new ITC(this, getModel().getClasses()),
                    new ETC(this, getModel().getClasses()), new CAC(this),
                    new DIT(this), new CLD(this),
                    new NOC(this), new NOD(this));
        }
        return metrics;
    }

    public TreeItem<ClassDeclaration> getTreeItem() {
        return treeItem;
    }

    /**
     * Compares the class identifier to another.
     *
     * @param id identifier to compare
     * @return if the two identifiers match
     */
    public boolean matches(String id) {
        return this.id.equals(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
