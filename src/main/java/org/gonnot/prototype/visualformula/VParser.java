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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import org.gonnot.prototype.visualformula.VNode.VBinaryNode;

/**
 *
 */
class VParser {
    private final Deque<VNode> stack = new ArrayDeque<VNode>();
    private final Deque<VBinaryNode> allOperators = new ArrayDeque<VBinaryNode>();
    private final List<VBinaryNode> visualDivisions = new ArrayList<VBinaryNode>();
    private VBinaryNode lastOperator = null;


    public VNode buildTrees(List<VToken> tokens, List<String> lines) {

        for (VToken token : tokens) {
            if (lastOperator != null && lastOperator.getRow() != token.getRow()) {
                lastOperator = null;
            }
            switch (token.getType()) {
                case ADD:
                    handleNode(VNodeFactory.add(token));
                    break;
                case MINUS:
                    handleNode(VNodeFactory.minus(token));
                    break;
                case MULTIPLY:
                    handleNode(VNodeFactory.multiply(token));
                    break;
                case DIVIDE:
                    handleNode(VNodeFactory.divide(token));
                    break;
                case VISUAL_DIVIDE:
                    VBinaryNode visualDivision = VNodeFactory.visualDivide(token);
                    visualDivisions.add(visualDivision);
                    handleOperandNode(visualDivision);
                    break;
                case NUMBER:
                case VARIABLE:
                    handleOperandNode(VNodeFactory.operand(token));
                    break;
            }
        }

        sortVisualDivisionByTheDistanceFromTheBaseFormula(lines);
        handleVisualDivisions();
        return stack.getFirst();
    }


    private void handleVisualDivisions() {
        if (visualDivisions.isEmpty()) {
            return;
        }

        for (VBinaryNode division : visualDivisions) {
            for (Iterator<VNode> iterator = stack.iterator(); iterator.hasNext(); ) {
                VNode node = iterator.next();

                if (node == division) {
                    continue;
                }
                if (node.isVisuallyJustAbove(division) && node.isWithinNodeBoundary(division)) {
                    iterator.remove();
                    division.setLeftOperand(node);
                }
                else if (node.isVisuallyJustBelow(division) && node.isWithinNodeBoundary(division)) {
                    iterator.remove();
                    division.setRightOperand(node);
                }

                if (division.getLeftOperand() != null && division.getRightOperand() != null) {
                    break;
                }
            }
        }
    }


    private void sortVisualDivisionByTheDistanceFromTheBaseFormula(List<String> lines) {
        int baseFormulaRow = findBaseFormulaRow(lines);
        Collections.sort(visualDivisions, new RowDistanceComparator(baseFormulaRow));
    }


    private int findBaseFormulaRow(List<String> lines) {
        int rowWithMaxNonWhiteChar = 0;
        int maxLength = 0;
        for (int i = 0; i < lines.size(); i++) {
            int currentLength = lines.get(i).replaceAll("\\s", "").length();
            if (currentLength >= maxLength) {
                maxLength = currentLength;
                rowWithMaxNonWhiteChar = i;
            }
        }
        return rowWithMaxNonWhiteChar;
    }


    private void handleOperandNode(VNode number) {
        if (lastOperator == null || lastOperator.getRightOperand() != null) {
            stack.push(number);
        }
        else {
            lastOperator.setRightOperand(number);
        }
    }


    private void handleNode(VBinaryNode binaryNode) {
        if (lastOperator == null) {
            binaryNode.setLeftOperand(stack.pop());
            stack.push(binaryNode);
        }
        else if (lastOperator.getPriority() < binaryNode.getPriority()) {
            binaryNode.setLeftOperand(lastOperator.getRightOperand());
            lastOperator.setRightOperand(binaryNode);
        }
        else if (lastOperator.getPriority() == binaryNode.getPriority()) {
            binaryNode.setLeftOperand(lastOperator);

            VBinaryNode parentNode = findParentNode(lastOperator, allOperators);
            if (parentNode == null) {
                stack.pop();
                stack.push(binaryNode);
            }
// Unnecessary code - the last operator is always in the right Operand
//            else if (parentNode.getLeftOperand() == lastOperator) {
//                parentNode.setLeftOperand(binaryNode);
//            }
            else if (parentNode.getRightOperand() == lastOperator) {
                parentNode.setRightOperand(binaryNode);
            }
        }
        else {
            binaryNode.setLeftOperand(stack.pop());
            stack.push(binaryNode);
        }
        lastOperator = binaryNode;
        allOperators.add(lastOperator);
    }


    private static VBinaryNode findParentNode(VBinaryNode operator, Deque<VBinaryNode> lastOperators) {
        for (VBinaryNode node : lastOperators) {
            if (node.getLeftOperand() == operator || node.getRightOperand() == operator) {
                return node;
            }
        }
        return null;
    }


    private static class RowDistanceComparator implements Comparator<VBinaryNode> {
        private final int baseFormulaRow;


        RowDistanceComparator(int baseFormulaRow) {
            this.baseFormulaRow = baseFormulaRow;
        }


        public int compare(VBinaryNode nodeA, VBinaryNode nodeB) {
            return new Integer(distance(baseFormulaRow, nodeB.getRow())).compareTo(distance(baseFormulaRow, nodeA.getRow()));
        }


        private int distance(int baseRow, int currentRow) {
            return Math.abs(currentRow - baseRow);
        }
    }
}
