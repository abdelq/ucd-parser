package ca.umontreal.iro.parser;

import ca.umontreal.iro.UCDBaseVisitor;
import ca.umontreal.iro.UCDLexer;
import ca.umontreal.iro.UCDParser;
import ca.umontreal.iro.UCDParser.*;
import ca.umontreal.iro.parser.tree.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.IOException;

public class Parser {
    public static Model parseFile(File file) throws IOException {
        var lexer = new UCDLexer(CharStreams.fromFileName(file.getPath()));
        var parser = new UCDParser(new CommonTokenStream(lexer));

        parser.removeErrorListener(ConsoleErrorListener.INSTANCE);
        parser.addErrorListener(AlertErrorListener.INSTANCE);

        return new ModelVisitor().visit(parser.model());
    }

    public static Model parseString(String string) {
        var lexer = new UCDLexer(CharStreams.fromString(string));
        var parser = new UCDParser(new CommonTokenStream(lexer));

        return new ModelVisitor().visit(parser.model());
    }

    private static class ModelVisitor extends UCDBaseVisitor<Model> {
        @Override
        public Model visitModel(ModelContext ctx) {
            var visitor = new DeclarationVisitor();
            return new Model(
                    ctx.ID().getText(),
                    ctx.declaration().stream().map(visitor::visit)
            );
        }
    }

    private static class DeclarationVisitor extends UCDBaseVisitor<Declaration> {
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

    private static class ClassDeclVisitor extends UCDBaseVisitor<ClassDecl> {
        @Override
        public ClassDecl visitClassDecl(ClassDeclContext ctx) {
            var visitor = new OperationVisitor();
            return new ClassDecl(
                    ctx.ID().getText(),
                    ctx.attribute().stream().map(attr ->
                            new Attribute(attr.ID().getText(), attr.type().getText())
                    ),
                    ctx.operation().stream().map(visitor::visit)
            );
        }
    }

    private static class AssociationVisitor extends UCDBaseVisitor<Association> {
        @Override
        public Association visitAssociation(AssociationContext ctx) {
            var visitor = new RoleVisitor();
            return new Association(
                    ctx.ID().getText(),
                    ctx.role(0).accept(visitor),
                    ctx.role(1).accept(visitor)
            );
        }
    }

    private static class AggregationVisitor extends UCDBaseVisitor<Aggregation> {
        @Override
        public Aggregation visitAggregation(AggregationContext ctx) {
            var visitor = new RoleVisitor();
            return new Aggregation(
                    ctx.container.accept(visitor),
                    ctx.parts().role().stream().map(visitor::visit)
            );
        }
    }

    private static class GeneralizationVisitor extends UCDBaseVisitor<Generalization> {
        @Override
        public Generalization visitGeneralization(GeneralizationContext ctx) {
            return new Generalization(
                    ctx.ID().getText(),
                    ctx.subclasses().ID().stream().map(ParseTree::getText)
            );
        }
    }

    private static class OperationVisitor extends UCDBaseVisitor<Operation> {
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

    private static class RoleVisitor extends UCDBaseVisitor<Role> {
        @Override
        public Role visitRole(RoleContext ctx) {
            return new Role(
                    ctx.ID().getText(),
                    Multiplicity.valueOf(ctx.multiplicity().getText())
            );
        }
    }
}
