/*
 * Visual Formula (Prototype)
 * ==========================
 *
 *    Copyright (C) 2012, 2012 by Gonnot Boris
 *
 *    ------------------------------------------------------------------------
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *    implied. See the License for the specific language governing permissions
 *    and limitations under the License.
 */

package org.gonnot.prototype.visualformula;

import java.util.*;

import org.gonnot.prototype.visualformula.VNode.VBinaryNode;

/**
 *
 */
class VParser {
    private Deque<VNode> stack = new ArrayDeque<VNode>();

    public VNode buildTrees(List<VToken> tokens) {
        List<VToken> postFixedTokens = convertToPostFix(tokens);

        for (VToken token : postFixedTokens) {
            if (token.isOperand()) {
                handleNode(token);
            } else if (token.isOperator()) {
                handleBinaryNode(VNodeFactory.binary(token));
            }
        }
        return stack.getFirst();
    }

    private void handleNode(VToken token) {
        stack.push(VNodeFactory.operand(token));
    }

    private void handleBinaryNode(VBinaryNode add) {
        add.setRightOperand(stack.pop());
        add.setLeftOperand(stack.pop());
        stack.push(add);
    }

    private List<VToken> convertToPostFix(List<VToken> tokens) {
        List<VToken> result = new ArrayList<VToken>();
        Stack<VToken> myStack = new Stack<VToken>();

        for (VToken token : tokens) {
            switch (token.getType()) {
                case LEFT_PARENTHESIS:
                    myStack.push(token);
                    break;
                case RIGHT_PARENTHESIS:
                    while (!myStack.peek().getType().equals(VToken.VTokenType.LEFT_PARENTHESIS)) {
                        result.add(myStack.pop());
                    }
                    myStack.pop();  // pop the left parenthesis
                    break;
                case NUMBER:
                case VARIABLE:
                    result.add(token);
                    break;
                default:
                    while (!myStack.empty() && myStack.peek().preceeds(token)) {
                        result.add(myStack.pop());
                    }
                    myStack.push(token);
                    break;
            }
        }
        while (!myStack.empty()) {
            result.add(myStack.pop());
        }
        return result;
    }
}
