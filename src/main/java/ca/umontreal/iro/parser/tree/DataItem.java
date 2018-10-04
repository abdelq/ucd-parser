package ca.umontreal.iro.parser.tree;

class DataItem {
    private final String id;
    private final String type;

    DataItem(String id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s %s", type, id);
    }
}
