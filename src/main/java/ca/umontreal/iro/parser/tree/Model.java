package ca.umontreal.iro.parser.tree;

import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Model {
    public String id;
    public List<Declaration> declarations;

    public Model(String id, Stream<Declaration> declarations) {
        this.id = id;
        this.declarations = declarations.collect(toList());
    }

    public <T> List<T> getDeclarationsOf(Class<T> type) {
        return declarations.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(toList());
    }
}
