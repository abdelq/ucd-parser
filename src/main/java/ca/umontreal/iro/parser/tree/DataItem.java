package ca.umontreal.iro.parser.tree;

import static java.lang.String.format;

public class DataItem {
    public final String id;
    public final String type;

    DataItem(String id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String toString() {
        return format("%s %s", type, id);
    }
}
