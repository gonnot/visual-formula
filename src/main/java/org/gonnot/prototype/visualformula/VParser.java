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


    public VNode buildTrees(List<VToken> tokens) {
        Deque<VBinaryNode> allOperators = new ArrayDeque<VBinaryNode>();
        VBinaryNode lastOperator = null;

        for (int i = 0, tokensSize = tokens.size(); i < tokensSize; i++) {
            VToken token = tokens.get(i);
            switch (token.getType()) {
                case ADD:
                    lastOperator = VNode.add(token, stack.pop());
                    allOperators.add(lastOperator);
                    stack.add(lastOperator);
                    break;
                case MINUS:
                    lastOperator = VNode.minus(token, stack.pop());
                    allOperators.add(lastOperator);
                    stack.add(lastOperator);
                    break;
                case MULTIPLY:
                    VBinaryNode multiply = VNode.multiply(token);
                    if (lastOperator == null) {
                        multiply.setLeftOperand(stack.pop());
                        stack.add(multiply);
                    }
                    else if (lastOperator.getPriority() < multiply.getPriority()) {
                        multiply.setLeftOperand(lastOperator.getRightOperand());
                        lastOperator.setRightOperand(multiply);
                    }
                    else if (lastOperator.getPriority() == multiply.getPriority()) {
                        multiply.setLeftOperand(lastOperator);

                        VBinaryNode parentNode = findParentNode(lastOperator, allOperators);
                        if (parentNode == null) {
                            stack.pop();
                            stack.add(multiply);
                        }
                        else if (parentNode.getLeftOperand() == lastOperator) {
                            parentNode.setLeftOperand(multiply);
                        }
                        else if (parentNode.getRightOperand() == lastOperator) {
                            parentNode.setRightOperand(multiply);
                        }
                    }
                    else {
                        multiply.setLeftOperand(lastOperator.getRightOperand());
                        lastOperator.setRightOperand(multiply);
                    }
                    lastOperator = multiply;
                    allOperators.add(lastOperator);
                    break;
                case NUMBER:
                    VNode number = VNode.number(token);
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


    private static VBinaryNode findParentNode(VBinaryNode operator, Deque<VBinaryNode> lastOperators) {
        for (VBinaryNode node : lastOperators) {
            if (node.getLeftOperand() == operator || node.getRightOperand() == operator) {
                return node;
            }
        }
        return null;
    }
}
