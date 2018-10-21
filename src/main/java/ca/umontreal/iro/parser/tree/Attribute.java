package ca.umontreal.iro.parser.tree;

import static java.lang.String.format;

public class Attribute extends DataItem {
    public Attribute(String id, String type) {
        super(id, type);
    }

    String details() {
        return format("    %s : %s", id, type);
    }
}
