package ca.umontreal.iro.metrics;

import ca.umontreal.iro.parser.Parser;
import ca.umontreal.iro.parser.tree.*;
import javafx.scene.control.TreeItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static ca.umontreal.iro.App.getModel;
import static ca.umontreal.iro.App.setModel;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.empty;
import static java.util.stream.Stream.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MetricsTest {
    private static Parser parser;
    private static String[] metrics;

    @BeforeAll
    static void init() {
        parser = new Parser();
        metrics = new String[]{"ANA", "NOM", "NOA", "ITC", "ETC", "CAC", "DIT", "CLD", "NOC", "NOD"};
    }

    private boolean classExists(String id, TreeItem<ClassDeclaration> item) {
        if (item.getValue().matches(id)) {
            return true;
        }
        return item.getChildren().parallelStream().anyMatch(child -> classExists(id, child));
    }

    private void createHierarchy(Model model) {
        List<TreeItem<ClassDeclaration>> treeItems = model.getClasses()
                .map(ClassDeclaration::getTreeItem).collect(toList());

        for (Generalization gen : model.getGeneralizations().collect(toList())) {
            List<TreeItem<ClassDeclaration>> children = treeItems.stream().filter(item ->
                    gen.getSubclasses().contains(item.getValue().getId())
            ).collect(toList());
            treeItems.removeIf(children::contains);

            TreeItem<ClassDeclaration> parent = treeItems.stream().filter(item ->
                    classExists(gen.getId(), item)
            ).findAny().get();
            parent.getChildren().addAll(children);
        }

        // Pour simuler l'hiérarchie du paneau de gauche
        TreeItem<ClassDeclaration> root = new TreeItem<>();
        root.getChildren().addAll(treeItems);
    }

    @Test
    void testLigue() throws IOException {
        setModel(parser.parseModel(new File("src/test/resources/Ligue.ucd")));
        createHierarchy(getModel());

        ClassDeclaration equipe = getModel().getClasses().filter(decl -> decl.matches("Equipe")).findAny().get();
        ClassDeclaration participant = getModel().getClasses().filter(decl -> decl.matches("Participant")).findAny().get();
        ClassDeclaration joueur = getModel().getClasses().filter(decl -> decl.matches("Joueur")).findAny().get();
        ClassDeclaration entraineur = getModel().getClasses().filter(decl -> decl.matches("Entraineur")).findAny().get();
        ClassDeclaration stade = getModel().getClasses().filter(decl -> decl.matches("Stade")).findAny().get();

        List<Number> equipeMetrics = equipe.getMetrics().stream().map(Metric::getValue).collect(toList());
        List<Number> participantMetrics = participant.getMetrics().stream().map(Metric::getValue).collect(toList());
        List<Number> joueurMetrics = joueur.getMetrics().stream().map(Metric::getValue).collect(toList());
        List<Number> entraineurMetrics = entraineur.getMetrics().stream().map(Metric::getValue).collect(toList());
        List<Number> stadeMetrics = stade.getMetrics().stream().map(Metric::getValue).collect(toList());

        List<Number> equipeRealMetrics = Arrays.asList(1.0 / 3.0, 3, 1, 1L, 1L, 3, 0, 0, 0, 0);
        List<Number> participantRealMetrics = Arrays.asList(0.0, 0, 1, 0L, 0L, 0, 0, 1, 2, 2);
        List<Number> joueurRealMetrics = Arrays.asList(0.5, 2, 3, 0L, 1L, 1, 1, 0, 0, 0);
        List<Number> entraineurRealMetrics = Arrays.asList(0.0, 0, 2, 0L, 0L, 1, 1, 0, 0, 0);
        List<Number> stadeRealMetrics = Arrays.asList(2.0, 1, 2, 1L, 0L, 1, 0, 0, 0, 0);

        for (int i = 0; i < metrics.length; i++) {
            assertEquals(equipeRealMetrics.get(i), equipeMetrics.get(i), "Equipe - " + metrics[i]);
            assertEquals(participantRealMetrics.get(i), participantMetrics.get(i), "Participant - " + metrics[i]);
            assertEquals(joueurRealMetrics.get(i), joueurMetrics.get(i), "Joueur - " + metrics[i]);
            assertEquals(entraineurRealMetrics.get(i), entraineurMetrics.get(i), "Entraineur - " + metrics[i]);
            assertEquals(stadeRealMetrics.get(i), stadeMetrics.get(i), "Stade - " + metrics[i]);
        }
    }

    @Test
    void testANA() {
        Metric metric;

        // Pas d'opérations
        metric = new ANA(new ClassDeclaration("Test", empty(), empty()));
        assertEquals(0.0, metric.getValue());

        // Pas d'arguments
        metric = new ANA(new ClassDeclaration("Test", empty(), of(new Operation("op", empty(), "op"))));
        assertEquals(0.0, metric.getValue());
    }

    @Test
    void testNOM() {
        Metric metric;
        ClassDeclaration parent, child;

        child = new ClassDeclaration("Child", empty(), of(
                new Operation("op1", of(
                        new Argument("arg1", "arg1"),
                        new Argument("arg2", "arg2")
                ), "op1"),
                new Operation("op2", empty(), "op2")
        ));

        // Locales seulement
        metric = new NOM(child);
        assertEquals(2, metric.getValue());

        // Héritées et non redéfinies
        parent = new ClassDeclaration("Parent", empty(), of(
                new Operation("op1", of(
                        new Argument("arg2", "arg2"),
                        new Argument("arg1", "arg1")
                ), "op1"),
                new Operation("op3", empty(), "op3")
        ));
        parent.getTreeItem().getChildren().add(child.getTreeItem());
        metric = new NOM(child);
        assertEquals(4, metric.getValue());

        // Héritées et redéfinies
        parent = new ClassDeclaration("Parent", empty(), of(
                new Operation("op2", empty(), "op2"),
                new Operation("op1", of(
                        new Argument("arg1", "arg1"),
                        new Argument("arg2", "arg2")
                ), "op1")
        ));
        parent.getTreeItem().getChildren().add(child.getTreeItem());
        metric = new NOM(child);
        assertEquals(2, metric.getValue());
    }

    @Test
    void testNOA() {
        Metric metric;

        ClassDeclaration parent = new ClassDeclaration("Parent", of(new Attribute("attr1", "attr1")), empty());
        ClassDeclaration child = new ClassDeclaration("Child", of(new Attribute("attr2", "attr2")), empty());

        // Locales
        metric = new NOA(child);
        assertEquals(1, metric.getValue());

        // Locales et héritées
        parent.getTreeItem().getChildren().add(child.getTreeItem());
        metric = new NOA(child);
        assertEquals(2, metric.getValue());
    }

    @Test
    void testITC() {
        Metric metric;
        ClassDeclaration declaration;
        List<ClassDeclaration> declarations;

        // Pas de méthodes
        declaration = new ClassDeclaration("Test1", empty(), empty());
        declarations = Arrays.asList(declaration,
                new ClassDeclaration("Test2", empty(), empty()),
                new ClassDeclaration("Test3", empty(), empty())
        );

        metric = new ITC(declaration, declarations.stream());
        assertEquals(0L, metric.getValue());

        // Pas d'arguments
        declaration = new ClassDeclaration("Test1", empty(), of(
                new Operation("op1", empty(), "op1")
        ));
        declarations.set(0, declaration);

        metric = new ITC(declaration, declarations.stream());
        assertEquals(0L, metric.getValue());

        // Arguments non reliés à d'autres classes
        declaration = new ClassDeclaration("Test1", empty(), of(
                new Operation("op1", of(new Argument("arg1", "arg1")), "op1")
        ));
        declarations.set(0, declaration);

        metric = new ITC(declaration, declarations.stream());
        assertEquals(0L, metric.getValue());

        // Arguments reliés à d'autres classes
        declaration = new ClassDeclaration("Test1", empty(), of(
                new Operation("op1", of(
                        new Argument("arg1", "Test2"),
                        new Argument("arg2", "Test2")
                ), "op1"),
                new Operation("op2", of(new Argument("arg3", "Test3")), "op2")
        ));
        declarations.set(0, declaration);

        metric = new ITC(declaration, declarations.stream());
        assertEquals(3L, metric.getValue());
    }

    @Test
    void testETC() {
        Metric metric;
        ClassDeclaration declaration = new ClassDeclaration("Test1", empty(), empty());
        List<ClassDeclaration> declarations;

        // Pas de méthodes
        declarations = Arrays.asList(declaration,
                new ClassDeclaration("Test2", empty(), empty()),
                new ClassDeclaration("Test3", empty(), empty())
        );

        metric = new ETC(declaration, declarations.stream());
        assertEquals(0L, metric.getValue());

        // Pas d'arguments
        declarations = Arrays.asList(declaration,
                new ClassDeclaration("Test2", empty(), of(
                        new Operation("op1", empty(), "op1")
                )),
                new ClassDeclaration("Test3", empty(), empty())
        );

        metric = new ETC(declaration, declarations.stream());
        assertEquals(0L, metric.getValue());

        // Arguments non reliés à la classe
        declarations = Arrays.asList(declaration,
                new ClassDeclaration("Test2", empty(), of(
                        new Operation("op1", of(
                                new Argument("arg1", "arg1"),
                                new Argument("arg2", "arg2")
                        ), "op1")
                )),
                new ClassDeclaration("Test3", empty(), empty())
        );

        metric = new ETC(declaration, declarations.stream());
        assertEquals(0L, metric.getValue());

        // Arguments reliés à la classe
        declarations = Arrays.asList(declaration,
                new ClassDeclaration("Test2", empty(), of(
                        new Operation("op1", of(
                                new Argument("arg1", "Test1"),
                                new Argument("arg2", "Test1")
                        ), "op1")
                )),
                new ClassDeclaration("Test3", empty(), of(
                        new Operation("op2", of(
                                new Argument("arg3", "Test1")
                        ), "op2")
                ))
        );

        metric = new ETC(declaration, declarations.stream());
        assertEquals(3L, metric.getValue());
    }

    @Test
    @Disabled
    void testCAC() {
        // Testée suffisemment avec testLigue
        // Plutôt pénible à retester
    }

    @Test
    void testDIT() {
        Metric metric;

        ClassDeclaration root = new ClassDeclaration("Root", empty(), empty());
        ClassDeclaration grandParent = new ClassDeclaration("Grand Parent", empty(), empty());
        ClassDeclaration parent = new ClassDeclaration("Parent", empty(), empty());
        ClassDeclaration child = new ClassDeclaration("Child", empty(), empty());

        root.getTreeItem().getChildren().add(grandParent.getTreeItem());
        grandParent.getTreeItem().getChildren().add(parent.getTreeItem());
        parent.getTreeItem().getChildren().add(child.getTreeItem());

        // Racine (exclue)
        metric = new DIT(root);
        assertEquals(0, metric.getValue());

        // Grand parent
        metric = new DIT(grandParent);
        assertEquals(0, metric.getValue());

        // Parent
        metric = new DIT(parent);
        assertEquals(1, metric.getValue());

        // Enfant
        metric = new DIT(child);
        assertEquals(2, metric.getValue());
    }

    @Test
    void testCLD() {
        Metric metric;

        ClassDeclaration root = new ClassDeclaration("Root", empty(), empty());
        ClassDeclaration grandParent = new ClassDeclaration("Grand Parent", empty(), empty());
        ClassDeclaration parent = new ClassDeclaration("Parent", empty(), empty());
        ClassDeclaration child = new ClassDeclaration("Child", empty(), empty());

        root.getTreeItem().getChildren().add(grandParent.getTreeItem());
        grandParent.getTreeItem().getChildren().add(parent.getTreeItem());
        parent.getTreeItem().getChildren().add(child.getTreeItem());

        // Grand parent
        metric = new CLD(grandParent);
        assertEquals(2, metric.getValue());

        // Parent
        metric = new CLD(parent);
        assertEquals(1, metric.getValue());

        // Enfant
        metric = new CLD(child);
        assertEquals(0, metric.getValue());
    }

    @Test
    void testNOC() {
        // Pas d'enfants
        Metric metric = new NOC(new ClassDeclaration("Test", empty(), empty()));
        assertEquals(0, metric.getValue());
    }

    @Test
    void testNOD() {
        Metric metric;

        // Enfants directs seulement
        ClassDeclaration parent = new ClassDeclaration("Test", empty(), empty());
        parent.getTreeItem().getChildren().addAll(
                new TreeItem(), new TreeItem()
        );
        metric = new NOD(parent);
        assertEquals(2, metric.getValue());

        // Enfants indirects sur un niveau
        TreeItem<ClassDeclaration> child = new TreeItem<>();
        child.getChildren().add(new TreeItem<>());
        parent.getTreeItem().getChildren().add(child);
        metric = new NOD(parent);
        assertEquals(4, metric.getValue());

        // Enfants indirects sur plus qu'un niveau
        child.getChildren().get(0).getChildren().add(new TreeItem<>());
        metric = new NOD(parent);
        assertEquals(5, metric.getValue());
    }
}
