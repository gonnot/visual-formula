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
            return node.visit(new VNodeVisitorInteger());
        }


        public String dumpTree() {
            return node.visit(new VNodeVisitorDump());
        }
    }
}
