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

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;

/**
 *
 */
class VLexer {

    public List<VToken> parse(String line, int lineIndex) {
        var tokens = new ArrayList<VToken>();
        for (int column = 0; column < line.length(); column++) {
            char currentChar = line.charAt(column);
            VToken builtToken;
            if (isNumberChar(currentChar) || isNegativeNumberStart(line, column, currentChar)) {
                int endColumn = findTokenEndIndex(WHILE_NUMBER, line, column + 1);
                builtToken = VToken.number(line.substring(column, endColumn), column);
                column = endColumn - 1;
            }
            else if (Character.isJavaIdentifierStart(currentChar)) {
                int endColumn = findTokenEndIndex(WHILE_VARIABLE, line, column + 1);
                builtToken = VToken.variables(line.substring(column, endColumn), column);
                column = endColumn - 1;
            }
            else if ('-' == currentChar && '-' == nextChar(line, column) && !nextFollowingIsNumber(line, column)) {
                int endColumn = findTokenEndIndex(WHILE_MINUS, line, column + 1);
                builtToken = VToken.visualDivide(line.substring(column, endColumn), column);
                column = endColumn - 1;
            }
            else {
                builtToken = switch (currentChar) {
                    case '+' -> VToken.add(column);
                    case '-' -> VToken.minus(column);
                    case '*' -> VToken.multiply(column);
                    case '/' -> VToken.divide(column);
                    default -> null;
                };
            }
            if (builtToken != null) {
                builtToken.setRow(lineIndex);
                tokens.add(builtToken);
            }
        }
        return tokens;
    }

    private char nextChar(String line, int column) {
        if (column + 1 >= line.length()) {
            return 0;
        }
        return line.charAt(column + 1);
    }

    private boolean nextFollowingIsNumber(String line, int column) {
        if (column + 2 >= line.length()) {
            return false;
        }
        char followingNext = line.charAt(column + 2);
        return followingNext >= '0' && followingNext <= '9';
    }

    private static boolean isNegativeNumberStart(String line, int column, char currentChar) {
        return column + 1 < line.length() && currentChar == '-' && isNumberChar(line.charAt(column + 1));
    }

    private static boolean isNumberChar(int aChar) {
        return Character.isDigit(aChar) || '.' == aChar;
    }

    private int findTokenEndIndex(IntPredicate functor, String line, int column) {
        for (int i = column; i < line.length(); i++) {
            char currentChar = line.charAt(i);

            if (!functor.test(currentChar)) {
                return i;
            }
        }
        return line.length();
    }

    private static final IntPredicate WHILE_NUMBER = VLexer::isNumberChar;
    private static final IntPredicate WHILE_VARIABLE = Character::isJavaIdentifierPart;
    private static final IntPredicate WHILE_MINUS = currentChar -> currentChar == '-';
}
