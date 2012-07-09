package org.gonnot.prototype.visualformula;
/**
 *
 */
class VNodeVisitorDump implements VNodeVisitor<String> {
    public String visitNumber(String numberInString) {
        return numberInString;
    }


    public String visitAdd(VNode leftOperand, VNode rightOperand) {
        return "(" + safeVisit(leftOperand) + " + " + safeVisit(rightOperand) + ")";
    }


    public String visitMinus(VNode leftOperand, VNode rightOperand) {
        return "(" + safeVisit(leftOperand) + " - " + safeVisit(rightOperand) + ")";
    }


    public String visitMultiply(VNode leftOperand, VNode rightOperand) {
        return "(" + safeVisit(leftOperand) + " x " + safeVisit(rightOperand) + ")";
    }


    private String safeVisit(VNode operand) {
        if (operand == null) {
            return "null";
        }
        return operand.visit(this);
    }
}
