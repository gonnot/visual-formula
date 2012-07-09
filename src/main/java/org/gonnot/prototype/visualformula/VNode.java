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


    public abstract <T> T visit(VNodeVisitor<T> visitor);


    static class VNumber extends VNode {

        VNumber(VToken token) {
            super(token);
        }


        @Override
        public <T> T visit(VNodeVisitor<T> visitor) {
            return visitor.visitNumber(token.getTokenInString());
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


        VBinaryNode(VToken token, VNode leftOperand, int priority) {
            super(token);
            this.leftOperand = leftOperand;
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
        public <T> T visit(VNodeVisitor<T> visitor) {
            if (token.getType() == VTokenType.ADD) {
                return visitor.visitAdd(leftOperand, rightOperand);
            }
            else if (token.getType() == VTokenType.MINUS) {
                return visitor.visitMinus(leftOperand, rightOperand);
            }
            else if (token.getType() == VTokenType.MULTIPLY) {
                return visitor.visitMultiply(leftOperand, rightOperand);
            }
            throw new InternalError("Unknown token found " + token.getType());
        }
    }
}
