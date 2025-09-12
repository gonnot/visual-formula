/*
 * Visual Formula (Prototype)
 * ==========================
 *
 *    Copyright (C) 2012, 2012 by Gonnot Boris
 *
 *    ------------------------------------------------------------------------
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *    implied. See the License for the specific language governing permissions
 *    and limitations under the License.
 */

package org.gonnot.prototype.visualformula;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 *
 */
public class VisualFormulaBuilder {
    private List<String> lines = new ArrayList<String>();


    public static VisualFormulaBuilder init() {
        return new VisualFormulaBuilder();
    }


    @SuppressWarnings({"InstanceMethodNamingConvention"})
    public VisualFormulaBuilder line(String line) {
        this.lines.add(line);
        return this;
    }


    public VisualFormula<Integer> compile() {
        return compile(integerFormula());
    }


    public static FormulaType<Integer> integerFormula() {
        return new FormulaType<Integer>();
    }


    public static FormulaType<Double> doubleFormula() {
        return new FormulaType<Double>();
    }


    public static FormulaType<BigDecimal> bigDecimalFormula() {
        return new FormulaType<BigDecimal>();
    }


    @SuppressWarnings({"UnusedParameters"})
    public <T> VisualFormula<T> compile(FormulaType<T> formulaType) {
        VLexer lexer = new VLexer();

        List<VToken> tokens = new ArrayList<VToken>();
        for (String line : lines) {
            tokens.addAll(lexer.parse(line));
        }

        VParser parser = new VParser();
        VNode node = parser.buildTrees(tokens);

        return new VisualFormula<T>(node);
    }


    public static VNodeVisitor<Integer, Integer> integerEvaluator() {
        return new VNodeVisitorInteger();
    }


    public static VNodeVisitor<String, Object> stringDumpEvaluator() {
        return new VNodeVisitorDump();
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public static class FormulaType<T> {
        private FormulaType() {
        }
    }

    public static class VisualFormula<VARIABLE_TYPE> {
        private VNode node;
        private FormulaContext<VARIABLE_TYPE> context = new FormulaContext<VARIABLE_TYPE>();


        public VisualFormula(VNode node) {
            this.node = node;
        }


        @SuppressWarnings({"unchecked"})
        public Integer compute() {
            // TODO this method should disappear as soon as the public API becomes clearer
            return executeWith((VNodeVisitor<Integer, ? super VARIABLE_TYPE>)integerEvaluator());
        }


        public <T> T executeWith(VNodeVisitor<T, ? super VARIABLE_TYPE> visitor) {
            visitor.init(context);
            return node.visit(visitor);
        }


        public String dumpTree() {
            return node.visit(stringDumpEvaluator());
        }


        public VisualFormula<VARIABLE_TYPE> variable(String name, VARIABLE_TYPE value) {
            context.declare(name, value);
            return this;
        }
    }
}
