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
 *
 */
class VToken {
    private VTokenType type;
    private int column;
    private String tokenInString;

    static enum VTokenType {
        ADD(true, true),
        MINUS(true, true),
        MULTIPLY(true, true),
        DIVIDE(true, true),
        VISUAL_DIVIDE,
        VARIABLE,
        NUMBER, LEFT_PARENTHESIS, RIGHT_PARENTHESIS;
        private boolean operatorInline;
        private boolean operator;


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


    public static VToken number(String number, int column) {
        return new VToken(VTokenType.NUMBER, number, column);
    }


    public static VToken variables(String name, int column) {
        return new VToken(VTokenType.VARIABLE, name, column);
    }


    public static VToken add(int column) {
        return new VToken(VTokenType.ADD, column);
    }


    public static VToken minus(int column) {
        return new VToken(VTokenType.MINUS, column);
    }


    public static VToken multiply(int column) {
        return new VToken(VTokenType.MULTIPLY, column);
    }


    public static VToken divide(int column) {
        return new VToken(VTokenType.DIVIDE, column);
    }


    public static VToken visualDivide(int column) {
        return new VToken(VTokenType.VISUAL_DIVIDE, column);
    }

    public static VToken leftParenthesis(int column) {
        return new VToken(VTokenType.LEFT_PARENTHESIS, column);
    }

    public static VToken rightParenthesis(int column) {
        return new VToken(VTokenType.RIGHT_PARENTHESIS, column);
    }

    protected VToken(VTokenType type, int column) {
        this(type, null, column);
    }


    protected VToken(VTokenType type, String tokenInString, int column) {
        this.type = type;
        this.column = column;
        this.tokenInString = tokenInString;
    }


    public VTokenType getType() {
        return type;
    }


    public String getTokenInString() {
        return tokenInString;
    }


    public int getColumn() {
        return column;
    }


    public boolean isOperatorInline() {
        return type.isOperatorInline();
    }

    public boolean isOperator() {
        return getType().isOperator();
    }

    public boolean isOperand() {
        return getType().isOperand();
    }

    @Override
    public String toString() {
        return "token{" + type + (tokenInString != null ? tokenInString : "") + "}";
    }

    //TODO[Refactoring] AM: pas tres content du nom de la methode ni de l'endroit pour le mettre...
    public boolean preceeds(VToken token){
          return precedence() >= token.precedence();
    }

    private int precedence() {
        int precedence = 0;
        if (VTokenType.ADD.equals(getType()) || VTokenType.MINUS.equals(getType())) {
            precedence = 1;
        } else if (VTokenType.MULTIPLY.equals(getType()) || VTokenType.DIVIDE.equals(getType())) {
            precedence = 2;
        }
        return precedence;
    }
}
