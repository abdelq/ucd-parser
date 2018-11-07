package ca.umontreal.iro.parser.tree;

import static java.lang.String.format;

public class Argument extends DataItem {
    public Argument(String id, String type) {
        super(id, type);
    }

    /**
     * Formatted string corresponding to the argument in the details section.
     *
     * @return detail string for argument
     */
    String details() {
        return format("%s : %s", id, type);
    }
}
