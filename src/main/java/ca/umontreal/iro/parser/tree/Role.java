package ca.umontreal.iro.parser.tree;

import static java.lang.String.format;

public class Role {
    public final String id;
    private final Multiplicity multiplicity;

    public Role(String id, Multiplicity multiplicity) {
        this.id = id;
        this.multiplicity = multiplicity;
    }

    @Override
    public String toString() {
        return format("%s %s", id, multiplicity);
    }
}
