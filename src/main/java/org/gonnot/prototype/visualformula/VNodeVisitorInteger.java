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
/**
 *
 */
class VNodeVisitorInteger implements VNodeVisitor<Integer, Integer> {
    private FormulaContext<? extends Integer> context;


    VNodeVisitorInteger() {
    }


    public void init(FormulaContext<? extends Integer> formulaContext) {
        this.context = formulaContext;
    }


    public Integer visitNumber(String numberInString) {
        return Integer.decode(numberInString);
    }


    public Integer visitVariable(String variableName) {
        return context.getValueOf(variableName);
    }


    public Integer visitAdd(VNode leftOperand, VNode rightOperand) {
        return leftOperand.visit(this) + rightOperand.visit(this);
    }


    public Integer visitMinus(VNode leftOperand, VNode rightOperand) {
        return leftOperand.visit(this) - rightOperand.visit(this);
    }


    public Integer visitMultiply(VNode leftOperand, VNode rightOperand) {
        return leftOperand.visit(this) * rightOperand.visit(this);
    }


    public Integer visitDivide(VNode leftOperand, VNode rightOperand) {
        return leftOperand.visit(this) / rightOperand.visit(this);
    }
}
