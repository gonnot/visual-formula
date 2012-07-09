package org.gonnot.prototype.visualformula;
/**
 *
 */
class VNodeVisitorInteger implements VNodeVisitor<Integer> {
    public Integer visitNumber(String numberInString) {
        return Integer.decode(numberInString);
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
