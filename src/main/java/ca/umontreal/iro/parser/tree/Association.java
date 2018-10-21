package ca.umontreal.iro.parser.tree;

import static java.lang.String.format;

public class Association implements Declaration {
    final Role firstRole;
    final Role secondRole;
    private final String id;

    public Association(String id, Role firstRole, Role secondRole) {
        this.id = id;
        this.firstRole = firstRole;
        this.secondRole = secondRole;
    }

    @Override
    public String toString() {
        return format("%s : %s, %s", id, firstRole, secondRole);
    }
}
