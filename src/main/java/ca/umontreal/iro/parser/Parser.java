package ca.umontreal.iro.parser;

import ca.umontreal.iro.UCDBaseVisitor;
import ca.umontreal.iro.UCDLexer;
import ca.umontreal.iro.UCDParser;
import ca.umontreal.iro.UCDParser.*;
import ca.umontreal.iro.parser.tree.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.IOException;

public class Parser {
    public static Model parse(File file) throws IOException {
        var lexer = new UCDLexer(CharStreams.fromFileName(file.getPath()));
        var parser = new UCDParser(new CommonTokenStream(lexer));

        // TODO Manage parsing errors with a dialog

        return new ModelVisitor().visit(parser.model());
    }

    private static class ModelVisitor extends UCDBaseVisitor<Model> {
        @Override
        public Model visitModel(ModelContext ctx) {
            var visitor = new DeclarationVisitor();
            return new Model(
                ctx.ID().getText(),
                ctx.declaration().stream().map(visitor::visitDeclaration)
            );
        }
    }

    private static class DeclarationVisitor extends UCDBaseVisitor<Declaration> {
        @Override
        public Declaration visitDeclaration(DeclarationContext ctx) {
            Declaration decl = null;

            if (ctx.classDecl() != null)
                decl = ctx.classDecl().accept(new ClassDeclVisitor());
            else if (ctx.association() != null)
                decl = ctx.association().accept(new AssociationVisitor());
            else if (ctx.aggregation() != null)
                decl = ctx.aggregation().accept(new AggregationVisitor());
            else if (ctx.generalization() != null)
                decl = ctx.generalization().accept(new GeneralizationVisitor());

            return decl;
        }
    }

    private static class ClassDeclVisitor extends UCDBaseVisitor<ClassDecl> {
        @Override
        public ClassDecl visitClassDecl(ClassDeclContext ctx) {
            var visitor = new OperationVisitor();
            return new ClassDecl(
                ctx.ID().getText(),
                ctx.dataItem().stream().map(attr ->
                    new Attribute(attr.ID().getText(), attr.type().getText())
                ),
                ctx.operation().stream().map(visitor::visitOperation)
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
                ctx.parts().role().stream().map(visitor::visitRole)
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
                ctx.dataItem().stream().map(arg ->
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
