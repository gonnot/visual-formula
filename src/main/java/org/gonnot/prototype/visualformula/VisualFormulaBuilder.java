package org.gonnot.prototype.visualformula;
/**
 *
 */
public class VisualFormulaBuilder {
    public static VisualFormulaBuilder init() {
        return new VisualFormulaBuilder();
    }


    @SuppressWarnings({"InstanceMethodNamingConvention"})
    public VisualFormulaBuilder _(String line) {
        return this;
    }


    public VisualFormula compile() {
        return new VisualFormula();
    }


    /**
     *
     */
    public static class VisualFormula {
        public Integer compute() {
            return 5;
        }
    }
}
