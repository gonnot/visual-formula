package org.gonnot.prototype.visualformula;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import org.gonnot.prototype.visualformula.VNode.VBinaryNode;
/**
 *
 */
class VParser {
    private Deque<VNode> stack = new ArrayDeque<VNode>();
    private Deque<VBinaryNode> allOperators = new ArrayDeque<VBinaryNode>();
    private VBinaryNode lastOperator = null;


    public VNode buildTrees(List<VToken> tokens) {
        for (int i = 0, tokensSize = tokens.size(); i < tokensSize; i++) {
            VToken token = tokens.get(i);
            switch (token.getType()) {
                case ADD:
                    handleNode(VNodeFactory.add(token));
                    break;
                case MINUS:
                    handleNode(VNodeFactory.minus(token));
                    break;
                case MULTIPLY:
                    handleNode(VNodeFactory.multiply(token));
                    break;
                case DIVIDE:
                    handleNode(VNodeFactory.divide(token));
                    break;
                case NUMBER:
                    VNode number = VNodeFactory.number(token);
                    if (lastOperator == null || lastOperator.getRightOperand() != null) {
                        stack.add(number);
                    }
                    else {
                        lastOperator.setRightOperand(number);
                    }
                    break;
            }
        }
        return stack.getFirst();
    }


    private void handleNode(VBinaryNode binaryNode) {
        if (lastOperator == null) {
            binaryNode.setLeftOperand(stack.pop());
            stack.add(binaryNode);
        }
        else if (lastOperator.getPriority() < binaryNode.getPriority()) {
            binaryNode.setLeftOperand(lastOperator.getRightOperand());
            lastOperator.setRightOperand(binaryNode);
        }
        else if (lastOperator.getPriority() == binaryNode.getPriority()) {
            binaryNode.setLeftOperand(lastOperator);

            VBinaryNode parentNode = findParentNode(lastOperator, allOperators);
            if (parentNode == null) {
                stack.pop();
                stack.add(binaryNode);
            }
            else if (parentNode.getLeftOperand() == lastOperator) {
                parentNode.setLeftOperand(binaryNode);
            }
            else if (parentNode.getRightOperand() == lastOperator) {
                parentNode.setRightOperand(binaryNode);
            }
        }
        else {
            binaryNode.setLeftOperand(stack.pop());
            stack.add(binaryNode);
        }
        lastOperator = binaryNode;
        allOperators.add(lastOperator);
    }


    private static VBinaryNode findParentNode(VBinaryNode operator, Deque<VBinaryNode> lastOperators) {
        for (VBinaryNode node : lastOperators) {
            if (node.getLeftOperand() == operator || node.getRightOperand() == operator) {
                return node;
            }
        }
        return null;
    }
}
