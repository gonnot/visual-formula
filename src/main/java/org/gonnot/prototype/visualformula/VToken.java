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
    private String tokenInString;

    static enum VTokenType {
        ADD,
        MINUS,
        MULTIPLY,
        DIVIDE,
        NUMBER;
    }


    public static VToken number(String number) {
        return new VToken(VTokenType.NUMBER, number);
    }


    public static VToken add() {
        return new VToken(VTokenType.ADD);
    }


    public static VToken minus() {
        return new VToken(VTokenType.MINUS);
    }


    public static VToken multiply() {
        return new VToken(VTokenType.MULTIPLY);
    }


    public static VToken divide() {
        return new VToken(VTokenType.DIVIDE);
    }


    protected VToken(VTokenType type) {
        this(type, null);
    }


    protected VToken(VTokenType type, String tokenInString) {
        this.type = type;
        this.tokenInString = tokenInString;
    }


    public VTokenType getType() {
        return type;
    }


    public String getTokenInString() {
        return tokenInString;
    }


    @Override
    public String toString() {
        return "VToken{" + type + (tokenInString != null ? tokenInString : "") + "}";
    }
}
