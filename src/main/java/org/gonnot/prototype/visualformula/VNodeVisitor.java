package org.gonnot.prototype.visualformula;
/**
 *
 */
interface VNodeVisitor<T> {
    public T visitNumber(String numberInString);


    public T visitAdd(VNode leftOperand, VNode rightOperand);


    public T visitMinus(VNode leftOperand, VNode rightOperand);


    public T visitMultiply(VNode leftOperand, VNode rightOperand);


    public T visitDivide(VNode leftOperand, VNode rightOperand);
}