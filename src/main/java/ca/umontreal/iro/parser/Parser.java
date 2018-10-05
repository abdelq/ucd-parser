package ca.umontreal.iro.parser;

import ca.umontreal.iro.UCDBaseVisitor;
import ca.umontreal.iro.UCDLexer;
import ca.umontreal.iro.UCDParser;
import ca.umontreal.iro.UCDParser.*;
import ca.umontreal.iro.parser.tree.*;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.IOException;

public class Parser {
    /**
     * Error listener for use by the parser.
     */
    private BaseErrorListener errorListener;

    public Parser() {
        errorListener = AlertErrorListener.INSTANCE;
    }

    /**
     * @param listener error listener
     */
    public Parser(BaseErrorListener listener) {
        errorListener = listener;
    }

    /**
     * Parses a stream of characters following the UCD format.
     *
     * @param stream source of characters for the lexer
     * @return use case model
     */
    private Model parseModel(CharStream stream) throws Exception {
        UCDLexer lexer = new UCDLexer(stream);
        UCDParser parser = new UCDParser(new CommonTokenStream(lexer));

        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        Model model = new ModelVisitor().visit(parser.model());
        if (parser.getNumberOfSyntaxErrors() > 0)
            throw new Exception(); // XXX

        return model;
    }

    /**
     * Parses a file following the UCD format.
     *
     * @param file file to parse
     * @return use case model
     * @throws IOException if an input or output exception occured
     */
    public Model parseModel(File file) throws Exception {
        return parseModel(CharStreams.fromFileName(file.getPath()));
    }

    /**
     * Parses a string following the UCD format.
     *
     * @param string string to parse
     * @return use case model
     */
    public Model parseModel(String string) throws Exception {
        return parseModel(CharStreams.fromString(string));
    }

    private class ModelVisitor extends UCDBaseVisitor<Model> {
        @Override
        public Model visitModel(ModelContext ctx) {
            DeclarationVisitor declarationVisitor = new DeclarationVisitor();
            return new Model(
                    ctx.ID().getText(),
                    ctx.declaration().stream().map(declarationVisitor::visit)
            );
        }
    }

    private class DeclarationVisitor extends UCDBaseVisitor<Declaration> {
        @Override
        public Declaration visitDeclaration(DeclarationContext ctx) {
            if (ctx.classDecl() != null)
                return ctx.classDecl().accept(new ClassDeclVisitor());
            else if (ctx.association() != null)
                return ctx.association().accept(new AssociationVisitor());
            else if (ctx.aggregation() != null)
                return ctx.aggregation().accept(new AggregationVisitor());
            else if (ctx.generalization() != null)
                return ctx.generalization().accept(new GeneralizationVisitor());
            return null;
        }
    }

    private class ClassDeclVisitor extends UCDBaseVisitor<ClassDecl> {
        @Override
        public ClassDecl visitClassDecl(ClassDeclContext ctx) {
            OperationVisitor operationVisitor = new OperationVisitor();
            return new ClassDecl(
                    ctx.ID().getText(),
                    ctx.attribute().stream().map(attr ->
                            new Attribute(attr.ID().getText(), attr.type().getText())
                    ),
                    ctx.operation().stream().map(operationVisitor::visit)
            );
        }
    }

    private class AssociationVisitor extends UCDBaseVisitor<Association> {
        @Override
        public Association visitAssociation(AssociationContext ctx) {
            RoleVisitor roleVisitor = new RoleVisitor();
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
            RoleVisitor roleVisitor = new RoleVisitor();
            return new Aggregation(
                    ctx.container.accept(roleVisitor),
                    ctx.part().stream().map(roleVisitor::visit)
            );
        }
    }

    private class GeneralizationVisitor extends UCDBaseVisitor<Generalization> {
        @Override
        public Generalization visitGeneralization(GeneralizationContext ctx) {
            return new Generalization(
                    ctx.ID().getText(),
                    ctx.subclass().stream().map(ParseTree::getText)
            );
        }
    }

    private class OperationVisitor extends UCDBaseVisitor<Operation> {
        @Override
        public Operation visitOperation(OperationContext ctx) {
            return new Operation(
                    ctx.ID().getText(),
                    ctx.argument().stream().map(arg ->
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
                    Multiplicity.valueOf(ctx.multiplicity().getText())
            );
        }
    }
}
