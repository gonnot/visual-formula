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


    public abstract <T, U> T visit(VNodeVisitor<T, U> visitor);


    static class VOperand extends VNode {

        VOperand(VToken token) {
            super(token);
        }


        @Override
        public <T, U> T visit(VNodeVisitor<T, U> visitor) {
            return switch (token.getType()) {
                case NUMBER -> visitor.visitNumber(token.getTokenInString());
                case VARIABLE -> visitor.visitVariable(token.getTokenInString());
                default -> throw new InternalError("Unknown token found " + token.getType());
            };
        }


        @Override
        public String toString() {
            return token.getTokenInString();
        }
    }
    static class VBinaryNode extends VNode {
        private VNode leftOperand;
        private int priority;
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
            return switch (token.getType()) {
                case ADD -> visitor.visitAdd(leftOperand, rightOperand);
                case MINUS -> visitor.visitMinus(leftOperand, rightOperand);
                case MULTIPLY -> visitor.visitMultiply(leftOperand, rightOperand);
                case DIVIDE -> visitor.visitDivide(leftOperand, rightOperand);
                default -> throw new InternalError("Unknown token found " + token.getType());
            };
        }
    }
}
