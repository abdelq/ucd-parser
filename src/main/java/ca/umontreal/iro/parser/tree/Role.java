package ca.umontreal.iro.parser.tree;

import static java.lang.String.format;

public class Role {
    private final String id;
    private final Multiplicity multiplicity;

    public Role(String id, Multiplicity multiplicity) {
        this.id = id;
        this.multiplicity = multiplicity;
    }

    /**
     * Compares the role's class identifier to another.
     *
     * @param id identifier to compare
     * @return if the two identifiers match
     */
    boolean matches(String id) {
        return this.id.equals(id);
    }

    @Override
    public String toString() {
        return format("%s %s", id, multiplicity);
    }
}
