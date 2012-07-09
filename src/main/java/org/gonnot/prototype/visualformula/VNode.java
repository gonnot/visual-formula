package org.gonnot.prototype.visualformula;
import org.gonnot.prototype.visualformula.VToken.VTokenType;
/**
 *
 */
abstract class VNode {
    protected VToken token;


    static VNode number(VToken token) {
        return new VNumber(token);
    }


    static VNode add(VToken operator, VNode operandA, VNode operandB) {
        return new VBinaryNode(operator, operandA, operandB);
    }


    public static VNode minus(VToken operator, VNode operandA, VNode operandB) {
        return new VBinaryNode(operator, operandA, operandB);
    }


    public static VNode multiply(VToken operator, VNode operandA, VNode operandB) {
        return new VBinaryNode(operator, operandA, operandB);
    }


    protected VNode(VToken token) {
        this.token = token;
    }


    public abstract Integer visit(VNodeVisitor visitor);


    private static class VNumber extends VNode {

        VNumber(VToken token) {
            super(token);
        }


        @Override
        public Integer visit(VNodeVisitor visitor) {
            return visitor.visitNumber(token.getTokenInString());
        }
    }
    private static class VBinaryNode extends VNode {

        private VNode operandA;
        private VNode operandB;


        VBinaryNode(VToken token, VNode operandA, VNode operandB) {
            super(token);
            this.operandA = operandA;
            this.operandB = operandB;
        }


        @Override
        public Integer visit(VNodeVisitor visitor) {
            if (token.getType() == VTokenType.ADD) {
                return visitor.visitAdd(operandA, operandB);
            }
            else if (token.getType() == VTokenType.MINUS) {
                return visitor.visitMinus(operandA, operandB);
            }
            else if (token.getType() == VTokenType.MULTIPLY) {
                return visitor.visitMultiply(operandA, operandB);
            }
            throw new InternalError("Unknown token found " + token.getType());
        }
    }
}
