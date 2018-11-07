package ca.umontreal.iro.parser.tree;

import static java.lang.String.format;

public class Argument extends DataItem {
    public Argument(String id, String type) {
        super(id, type);
    }

    public String getType() {
        return type;
    }

    /**
     * Formatted string corresponding to the argument in the details section.
     *
     * @return detail string for argument
     */
    String details() {
        return format("%s : %s", id, type);
    }

    /**
     * Compares the argument's type to a class identifier.
     *
     * @param id identifier to compare
     * @return if the two identifiers match
     */
    public boolean matches(String id) {
        return type.equals(id);
    }
}
