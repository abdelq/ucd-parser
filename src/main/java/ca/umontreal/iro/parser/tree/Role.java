package ca.umontreal.iro.parser.tree;

public class Role {
    public String id;
    public Multiplicity multiplicity;

    public Role(String id, Multiplicity multiplicity) {
        this.id = id;
        this.multiplicity = multiplicity;
    }
}
