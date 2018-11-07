package ca.umontreal.iro.parser.tree;

import java.util.Objects;

import static java.lang.String.format;

public class DataItem {
    final String id;
    final String type;

    DataItem(String id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String toString() {
        return format("%s %s", type, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DataItem item = (DataItem) obj;
        return Objects.equals(id, item.id) &&
                Objects.equals(type, item.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
