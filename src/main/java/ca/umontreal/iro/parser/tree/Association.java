package ca.umontreal.iro.parser.tree;

import static java.lang.String.format;

public class Association implements Declaration {
    private final String id;
    private final Role firstRole;
    private final Role secondRole;

    public Association(String id, Role firstRole, Role secondRole) {
        this.id = id;
        this.firstRole = firstRole;
        this.secondRole = secondRole;
    }

    /**
     * Compares the class identifier for the association's roles to another.
     *
     * @param id identifier to compare
     * @return if the identifier is present
     */
    boolean matches(String id) {
        return firstRole.matches(id) || secondRole.matches(id);
    }

    @Override
    public String toString() {
        return format("%s : %s, %s", id, firstRole, secondRole);
    }
}
