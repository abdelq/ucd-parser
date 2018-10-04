package ca.umontreal.iro.parser.tree;

public class DataItem {
    public String id;
    public String type;

    public DataItem(String id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s %s", type, id);
    }
}
