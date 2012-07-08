package org.gonnot.prototype.visualformula;
/**
 *
 */
class VNodeVisitor {
    VNodeVisitor() {
    }


    public Integer visitNumber(String numberInString) {
        return Integer.decode(numberInString);
    }


    public Integer visitAdd(VNode operandA, VNode operandB) {
        return operandA.visit(this) + operandB.visit(this);
    }


    public Integer visitMinus(VNode operandA, VNode operandB) {
        return operandA.visit(this) - operandB.visit(this);
    }
}