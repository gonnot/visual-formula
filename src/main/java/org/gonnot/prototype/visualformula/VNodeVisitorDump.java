package org.gonnot.prototype.visualformula;
/**
 *
 */
class VNodeVisitorDump implements VNodeVisitor<String> {
    public String visitNumber(String numberInString) {
        return numberInString;
    }


    public String visitAdd(VNode leftOperand, VNode rightOperand) {
        return visitBinaryOperator(leftOperand, " + ", rightOperand);
    }


    public String visitMinus(VNode leftOperand, VNode rightOperand) {
        return visitBinaryOperator(leftOperand, " - ", rightOperand);
    }


    public String visitMultiply(VNode leftOperand, VNode rightOperand) {
        return visitBinaryOperator(leftOperand, " x ", rightOperand);
    }


    public String visitDivide(VNode leftOperand, VNode rightOperand) {
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
