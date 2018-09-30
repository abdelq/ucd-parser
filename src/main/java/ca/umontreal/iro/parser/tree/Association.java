package ca.umontreal.iro.parser.tree;

public class Association implements Declaration {
    public String id;
    public Role firstRole;
    public Role secondRole;

    public Association(String id, Role firstRole, Role secondRole) {
        this.id = id;
        this.firstRole = firstRole;
        this.secondRole = secondRole;
    }
}
