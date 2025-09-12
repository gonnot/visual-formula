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

/**
 * Token representation using modern Java record features.
 */
record VToken(VTokenType type, String tokenInString, int column) {

    enum VTokenType {
        ADD(true, true),
        MINUS(true, true),
        MULTIPLY(true, true),
        DIVIDE(true, true),
        VISUAL_DIVIDE,
        VARIABLE,
        NUMBER,
        LEFT_PARENTHESIS,
        RIGHT_PARENTHESIS;
        private final boolean operatorInline;
        private final boolean operator;

        VTokenType() {
            this(false, false);
        }

        VTokenType(boolean operatorInline, boolean isOperator) {
            this.operatorInline = operatorInline;
            this.operator = isOperator;
        }

        public boolean isOperatorInline() {
            return operatorInline;
        }

        private boolean isOperator() {
            return operator;
        }

        private boolean isOperand() {
            return !operator;
        }
    }

    static VToken number(String number, int column) {
        return new VToken(VTokenType.NUMBER, number, column);
    }

    static VToken variables(String name, int column) {
        return new VToken(VTokenType.VARIABLE, name, column);
    }

    static VToken add(int column) {
        return new VToken(VTokenType.ADD, null, column);
    }

    static VToken minus(int column) {
        return new VToken(VTokenType.MINUS, null, column);
    }

    static VToken multiply(int column) {
        return new VToken(VTokenType.MULTIPLY, null, column);
    }

    static VToken divide(int column) {
        return new VToken(VTokenType.DIVIDE, null, column);
    }

    static VToken visualDivide(int column) {
        return new VToken(VTokenType.VISUAL_DIVIDE, null, column);
    }

    static VToken leftParenthesis(int column) {
        return new VToken(VTokenType.LEFT_PARENTHESIS, null, column);
    }

    static VToken rightParenthesis(int column) {
        return new VToken(VTokenType.RIGHT_PARENTHESIS, null, column);
    }

    VToken(VTokenType type, int column) {
        this(type, null, column);
    }

    VTokenType getType() {
        return type;
    }

    String getTokenInString() {
        return tokenInString;
    }

    int getColumn() {
        return column;
    }

    boolean isOperatorInline() {
        return type.isOperatorInline();
    }

    boolean isOperator() {
        return type.isOperator();
    }

    boolean isOperand() {
        return type.isOperand();
    }

    @Override
    public String toString() {
        return "token{" + type + (tokenInString != null ? tokenInString : "") + "}";
    }

    //TODO[Refactoring] AM: pas tres content du nom de la methode ni de l'endroit pour le mettre...
    boolean preceeds(VToken token) {
        return precedence() >= token.precedence();
    }

    private int precedence() {
        return switch (type) {
            case ADD, MINUS -> 1;
            case MULTIPLY, DIVIDE -> 2;
            default -> 0;
        };
    }
}
