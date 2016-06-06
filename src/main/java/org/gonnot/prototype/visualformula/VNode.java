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

import org.gonnot.prototype.visualformula.VToken.VTokenType;

/**
 *
 */
abstract class VNode {
    static final int BASIC_OPERATION = 0;
    static final int PRIORITY_OPERATION = 10;
    protected VToken token;


    protected VNode(VToken token) {
        this.token = token;
    }


    public abstract <VISITOR_RESULT, VARIABLE_TYPE> VISITOR_RESULT visit(VNodeVisitor<VISITOR_RESULT, VARIABLE_TYPE> visitor);


    public int getRow() {
        return token.getRow();
    }


    public boolean isVisuallyJustAbove(VNode node) {
        // TODO beware - not intuitive - index starts from 0 (higher) to n (lower) -> same remarks for isVisuallyJustBelow()
        return getHighestRow() == node.getRow() - 1;
    }


    public boolean isVisuallyJustBelow(VNode node) {
        return getLowestRow() == node.getRow() + 1;
    }


    public boolean isWithinNodeBoundary(VNode node) {
        int startColumn = node.token.getStartColumn();
        int endColumn = node.token.getEndColumn();
        return token.getStartColumn() >= startColumn && token.getEndColumn() <= endColumn;
    }


    private int getLowestRow() {
        return visit(new ExtractRowVisitor(Integer.MAX_VALUE) {
            @Override
            protected int compareRow(int left, int right) {
                return left < right ? left : right;
            }
        });
    }


    private int getHighestRow() {
        return visit(new ExtractRowVisitor(Integer.MIN_VALUE) {
            @Override
            protected int compareRow(int left, int right) {
                return left > right ? left : right;
            }
        });
    }


    static class VOperand extends VNode {

        VOperand(VToken token) {
            super(token);
        }


        @Override
        public <T, U> T visit(VNodeVisitor<T, U> visitor) {
            if (token.getType() == VTokenType.NUMBER) {
                return visitor.visitNumber(token.getTokenInString(), this);
            }
            else if (token.getType() == VTokenType.VARIABLE) {
                return visitor.visitVariable(token.getTokenInString(), this);
            }
            throw new InternalError("Unknown token found " + token.getType());
        }


        @Override
        public String toString() {
            return token.getTokenInString();
        }
    }

    static class VBinaryNode extends VNode {
        private VNode leftOperand;
        private final int priority;
        private VNode rightOperand;


        VBinaryNode(VToken token, int priority) {
            super(token);
            this.priority = priority;
        }


        public VNode getLeftOperand() {
            return leftOperand;
        }


        public void setLeftOperand(VNode leftOperand) {
            this.leftOperand = leftOperand;
        }


        public VNode getRightOperand() {
            return rightOperand;
        }


        public void setRightOperand(VNode rightOperand) {
            this.rightOperand = rightOperand;
        }


        public int getPriority() {
            return priority;
        }


        @Override
        public <T, U> T visit(VNodeVisitor<T, U> visitor) {
            if (token.getType() == VTokenType.ADD) {
                return visitor.visitAdd(leftOperand, rightOperand, this);
            }
            else if (token.getType() == VTokenType.MINUS) {
                return visitor.visitMinus(leftOperand, rightOperand, this);
            }
            else if (token.getType() == VTokenType.MULTIPLY) {
                return visitor.visitMultiply(leftOperand, rightOperand, this);
            }
            else if (token.getType() == VTokenType.DIVIDE || token.getType() == VTokenType.VISUAL_DIVIDE) {
                return visitor.visitDivide(leftOperand, rightOperand, this);
            }
            throw new InternalError("Unknown token found " + token.getType());
        }


        @Override
        public String toString() {
            return token.getType().toString() + "(" + getRow() + "," + token.getStartColumn() + "-" + token.getEndColumn() + ")";
        }
    }
}
