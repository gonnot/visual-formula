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
class VNodeVisitorDump implements VNodeVisitor<String, Object> {
    public void init(FormulaContext formulaContext) {
    }

    public String visitNumber(String numberInString, VNode currentNode) {
        return numberInString;
    }

    public String visitVariable(String variableName, VNode currentNode) {
        return variableName;
    }

    public String visitAdd(VNode leftOperand, VNode rightOperand, VNode currentNode) {
        return visitBinaryOperator(leftOperand, " + ", rightOperand);
    }

    public String visitMinus(VNode leftOperand, VNode rightOperand, VNode currentNode) {
        return visitBinaryOperator(leftOperand, " - ", rightOperand);
    }

    public String visitMultiply(VNode leftOperand, VNode rightOperand, VNode currentNode) {
        return visitBinaryOperator(leftOperand, " x ", rightOperand);
    }

    public String visitDivide(VNode leftOperand, VNode rightOperand, VNode currentNode) {
        return visitBinaryOperator(leftOperand, " / ", rightOperand);
    }

    private String visitBinaryOperator(VNode leftOperand, String operatorString, VNode rightOperand) {
        return "(" + safeVisit(leftOperand) + operatorString + safeVisit(rightOperand) + ")";
    }

    private String safeVisit(VNode operand) {
        if (operand == null) {
            return "null";
        }
        return operand.visit(this);
    }
}
