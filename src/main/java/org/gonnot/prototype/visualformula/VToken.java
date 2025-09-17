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
    private final VTokenType type;
    private final int startColumn;
    private final String tokenInString;
    private int row;

    enum VTokenType {
        ADD,
        MINUS,
        MULTIPLY,
        DIVIDE,
        VISUAL_DIVIDE,
        VARIABLE,
        NUMBER
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

    public static VToken visualDivide(String division, int column) {
        return new VToken(VTokenType.VISUAL_DIVIDE, division, column);
    }

    protected VToken(VTokenType type, int startColumn) {
        this(type, null, startColumn);
    }

    protected VToken(VTokenType type, String tokenInString, int startColumn) {
        this.type = type;
        this.startColumn = startColumn;
        this.tokenInString = tokenInString;
    }

    public VTokenType getType() {
        return type;
    }

    public String getTokenInString() {
        return tokenInString;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public int getEndColumn() {
        return startColumn + (tokenInString != null ? tokenInString.length() : 1);
    }

    @Override
    public String toString() {
        return "token{" + type + (tokenInString != null ? tokenInString : "") + "}";
    }
}
