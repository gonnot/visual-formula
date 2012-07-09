package org.gonnot.prototype.visualformula;
import org.gonnot.prototype.visualformula.VNode.VBinaryNode;
/**
 *
 */
class VNodeFactory {

    private VNodeFactory() {
    }


    static VNode number(VToken token) {
        return new VNode.VNumber(token);
    }


    static VBinaryNode add(VToken operator) {
        return new VBinaryNode(operator, VNode.BASIC_OPERATION);
    }


    public static VBinaryNode minus(VToken operator) {
        return new VBinaryNode(operator, VNode.BASIC_OPERATION);
    }


    public static VBinaryNode multiply(VToken operator) {
        return new VBinaryNode(operator, VNode.PRIORITY_OPERATION);
    }


    public static VBinaryNode divide(VToken operator) {
        return new VBinaryNode(operator, VNode.PRIORITY_OPERATION);
    }
}
