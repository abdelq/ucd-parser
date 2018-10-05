package ca.umontreal.iro.parser.tree;

public class Association implements Declaration {
    public final Role firstRole;
    public final Role secondRole;
    private final String id;

    public Association(String id, Role firstRole, Role secondRole) {
        this.id = id;
        this.firstRole = firstRole;
        this.secondRole = secondRole;
    }

    @Override
    public String toString() {
        return String.format("%s: %s, %s", id, firstRole, secondRole);
    }
}
