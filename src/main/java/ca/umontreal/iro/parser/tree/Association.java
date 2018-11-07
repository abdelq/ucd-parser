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
     * TODO.
     *
     * @param id identifier to compare
     * @return TODO
     */
    boolean matches(String id) {
        return firstRole.matches(id) || secondRole.matches(id);
    }

    @Override
    public String toString() {
        return format("%s : %s, %s", id, firstRole, secondRole);
    }
}
