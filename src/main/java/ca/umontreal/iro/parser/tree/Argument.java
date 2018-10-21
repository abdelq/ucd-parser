package ca.umontreal.iro.parser.tree;

import static java.lang.String.format;

public class Argument extends DataItem {
    public Argument(String id, String type) {
        super(id, type);
    }

    String details() {
        return format("%s : %s", id, type);
    }
}
