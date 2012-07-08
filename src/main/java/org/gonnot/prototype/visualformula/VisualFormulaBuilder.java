package org.gonnot.prototype.visualformula;
import java.util.List;
/**
 *
 */
public class VisualFormulaBuilder {
    private String lines;


    public static VisualFormulaBuilder init() {
        return new VisualFormulaBuilder();
    }


    @SuppressWarnings({"InstanceMethodNamingConvention"})
    public VisualFormulaBuilder _(String line) {
        this.lines = line;
        return this;
    }


    public VisualFormula compile() {
        VLexer lexer = new VLexer();
        List<VToken> tokens = lexer.parse(lines);

        VParser parser = new VParser();
        VNode node = parser.buildTrees(tokens);

        return new VisualFormula(node);
    }


    public static class VisualFormula {
        private VNode node;


        public VisualFormula(VNode node) {
            this.node = node;
        }


        public Integer compute() {
            return node.visit(new VNodeVisitor());
        }
    }
}
