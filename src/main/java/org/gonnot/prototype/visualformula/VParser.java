package org.gonnot.prototype.visualformula;
import java.util.ArrayDeque;
import java.util.List;
/**
 *
 */
class VParser {
    private ArrayDeque<VNode> stack = new ArrayDeque<VNode>();


    public VNode buildTrees(List<VToken> tokens) {
        for (int i = 0, tokensSize = tokens.size(); i < tokensSize; i++) {
            VToken token = tokens.get(i);
            switch (token.getType()) {
                case ADD:
                    VNode operandA = stack.pop();
                    VNode operandB = VNode.number(tokens.get(++i));
                    stack.add(VNode.add(token, operandA, operandB));
                    break;
                case MINUS:
                    operandA = stack.pop();
                    operandB = VNode.number(tokens.get(++i));
                    stack.add(VNode.minus(token, operandA, operandB));
                    break;
                case MULTIPLY:
                    operandA = stack.pop();
                    operandB = VNode.number(tokens.get(++i));
                    stack.add(VNode.multiply(token, operandA, operandB));
                    break;
                case NUMBER:
                    stack.add(VNode.number(token));
                    break;
            }
        }
        return stack.getFirst();
    }
}
