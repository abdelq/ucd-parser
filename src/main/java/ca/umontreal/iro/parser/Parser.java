package ca.umontreal.iro.parser;

import ca.umontreal.iro.UCDBaseVisitor;
import ca.umontreal.iro.UCDLexer;
import ca.umontreal.iro.UCDParser;
import ca.umontreal.iro.UCDParser.*;
import ca.umontreal.iro.parser.tree.*;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.IOException;

import static ca.umontreal.iro.parser.tree.Multiplicity.valueOf;
import static org.antlr.v4.runtime.CharStreams.fromFileName;
import static org.antlr.v4.runtime.CharStreams.fromString;

public class Parser {
    /**
     * Error listener for use by the parser.
     */
    private ANTLRErrorListener errorListener;

    public Parser() {
        errorListener = ThrowingErrorListener.INSTANCE;
    }

    /**
     * @param listener error listener
     */
    public Parser(ANTLRErrorListener listener) {
        errorListener = listener;
    }

    /**
     * Parses a stream of characters following the UCD format.
     *
     * @param stream source of characters for the lexer
     * @return use case model
     */
    private Model parseModel(CharStream stream) {
        var lexer = new UCDLexer(stream);
        var parser = new UCDParser(new CommonTokenStream(lexer));

        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        return parser.model().accept(new ModelVisitor());
    }

    /**
     * Parses a file following the UCD format.
     *
     * @param file file to parse
     * @return use case model
     * @throws IOException if an input or output exception occurred
     */
    public Model parseModel(File file) throws IOException {
        return parseModel(fromFileName(file.getPath()));
    }

    /**
     * Parses a string following the UCD format.
     *
     * @param string string to parse
     * @return use case model
     */
    public Model parseModel(String string) {
        return parseModel(fromString(string));
    }

    private class ModelVisitor extends UCDBaseVisitor<Model> {
        @Override
        public Model visitModel(ModelContext ctx) {
            var declarationVisitor = new DeclarationVisitor();
            return new Model(
                    ctx.ID().getText(),
                    ctx.declaration().parallelStream().map(declarationVisitor::visit)
            );
        }
    }

    private class DeclarationVisitor extends UCDBaseVisitor<Declaration> {
        @Override
        public Declaration visitDeclaration(DeclarationContext ctx) {
            ParseTree tree;
            if ((tree = ctx.classDeclaration()) != null) {
                return tree.accept(new ClassDeclarationVisitor());
            } else if ((tree = ctx.association()) != null) {
                return tree.accept(new AssociationVisitor());
            } else if ((tree = ctx.aggregation()) != null) {
                return tree.accept(new AggregationVisitor());
            } else if ((tree = ctx.generalization()) != null) {
                return tree.accept(new GeneralizationVisitor());
            }
            return null;
        }
    }

    private class ClassDeclarationVisitor extends UCDBaseVisitor<ClassDeclaration> {
        @Override
        public ClassDeclaration visitClassDeclaration(ClassDeclarationContext ctx) {
            var operationVisitor = new OperationVisitor();
            return new ClassDeclaration(
                    ctx.ID().getText(),
                    ctx.attribute().parallelStream().map(attr ->
                            new Attribute(attr.ID().getText(), attr.type().getText())
                    ),
                    ctx.operation().parallelStream().map(operationVisitor::visit)
            );
        }
    }

    private class AssociationVisitor extends UCDBaseVisitor<Association> {
        @Override
        public Association visitAssociation(AssociationContext ctx) {
            var roleVisitor = new RoleVisitor();
            return new Association(
                    ctx.ID().getText(),
                    ctx.role(0).accept(roleVisitor),
                    ctx.role(1).accept(roleVisitor)
            );
        }
    }

    private class AggregationVisitor extends UCDBaseVisitor<Aggregation> {
        @Override
        public Aggregation visitAggregation(AggregationContext ctx) {
            var roleVisitor = new RoleVisitor();
            return new Aggregation(
                    ctx.container().accept(roleVisitor),
                    ctx.part().parallelStream().map(roleVisitor::visit)
            );
        }
    }

    private class GeneralizationVisitor extends UCDBaseVisitor<Generalization> {
        @Override
        public Generalization visitGeneralization(GeneralizationContext ctx) {
            return new Generalization(
                    ctx.ID().getText(),
                    ctx.subclass().parallelStream().map(ParseTree::getText)
            );
        }
    }

    private class OperationVisitor extends UCDBaseVisitor<Operation> {
        @Override
        public Operation visitOperation(OperationContext ctx) {
            return new Operation(
                    ctx.ID().getText(),
                    ctx.argument().parallelStream().map(arg ->
                            new Argument(arg.ID().getText(), arg.type().getText())
                    ),
                    ctx.type().getText()
            );
        }
    }

    private class RoleVisitor extends UCDBaseVisitor<Role> {
        @Override
        public Role visitRole(RoleContext ctx) {
            return new Role(
                    ctx.ID().getText(),
                    valueOf(ctx.multiplicity().getText())
            );
        }
    }
}
