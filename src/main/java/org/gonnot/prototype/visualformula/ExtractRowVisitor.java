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
abstract class ExtractRowVisitor implements VNodeVisitor<Integer, Object> {
    private final int rowIndexForNull;


    ExtractRowVisitor(int rowIndexForNull) {
        this.rowIndexForNull = rowIndexForNull;
    }


    public void init(FormulaContext formulaContext) {
    }


    public Integer visitNumber(String numberInString, VNode currentNode) {
        return currentNode.getRow();
    }


    public Integer visitVariable(String variableName, VNode currentNode) {
        return currentNode.getRow();
    }


    public Integer visitAdd(VNode leftOperand, VNode rightOperand, VNode currentNode) {
        return compareNodesRowIndex(leftOperand, rightOperand, currentNode);
    }


    public Integer visitMinus(VNode leftOperand, VNode rightOperand, VNode currentNode) {
        return compareNodesRowIndex(leftOperand, rightOperand, currentNode);
    }


    public Integer visitMultiply(VNode leftOperand, VNode rightOperand, VNode currentNode) {
        return compareNodesRowIndex(leftOperand, rightOperand, currentNode);
    }


    public Integer visitDivide(VNode leftOperand, VNode rightOperand, VNode currentNode) {
        return compareNodesRowIndex(leftOperand, rightOperand, currentNode);
    }


    private Integer compareNodesRowIndex(VNode leftOperand, VNode rightOperand, VNode currentNode) {
        int left = getRowIndex(leftOperand);
        int right = getRowIndex(rightOperand);
        int current = currentNode.getRow();
        return compareRow(compareRow(left, right), current);
    }


    private Integer getRowIndex(VNode node) {
        if (node == null) {
            return rowIndexForNull;
        }
        return node.visit(this);
    }


    protected abstract int compareRow(int left, int right);
}
