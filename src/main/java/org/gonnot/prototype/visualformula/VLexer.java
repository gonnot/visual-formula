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
/**
 *
 */
class VLexer {

    public List<VToken> parse(String line) {
        List<VToken> tokens = new ArrayList<VToken>();
        for (int column = 0; column < line.length(); column++) {
            char currentChar = line.charAt(column);
            if (isNumberChar(currentChar) || isNegativeNumberStart(line, column, currentChar)) {
                int endColumn = findTokenEndIndex(WHILE_NUMBER, line, column + 1);
                tokens.add(VToken.number(line.substring(column, endColumn), column));
                column = endColumn - 1;
            }
            else if (Character.isJavaIdentifierStart(currentChar)) {
                int endColumn = findTokenEndIndex(WHILE_VARIABLE, line, column + 1);
                tokens.add(VToken.variables(line.substring(column, endColumn), column));
                column = endColumn - 1;
            }
            else {
                //noinspection SwitchStatementWithoutDefaultBranch
                switch (currentChar) {
                    case '+':
                        tokens.add(VToken.add(column));
                        break;
                    case '-':
                        tokens.add(VToken.minus(column));
                        break;
                    case '*':
                        tokens.add(VToken.multiply(column));
                        break;
                    case '/':
                        tokens.add(VToken.divide(column));
                        break;
                }
            }
        }
        return tokens;
    }


    private static boolean isNegativeNumberStart(String line, int column, char currentChar) {
        //noinspection SimplifiableIfStatement
        if (column + 1 >= line.length()) {
            return false;
        }
        return ('-' == currentChar && isNumberChar(line.charAt(column + 1)));
    }


    private static boolean isNumberChar(char aChar) {
        return Character.isDigit(aChar) || '.' == aChar;
    }


    private int findTokenEndIndex(Functor functor, String line, int column) {
        for (int i = column; i < line.length(); i++) {
            char currentChar = line.charAt(i);

            if (!functor.isPartOfTheToken(currentChar)) {
                return i;
            }
        }
        return line.length();
    }


    private interface Functor {
        public boolean isPartOfTheToken(char currentChar);
    }
    private static final Functor WHILE_NUMBER = new Functor() {
        public boolean isPartOfTheToken(char currentChar) {
            return isNumberChar(currentChar);
        }
    };
    private static final Functor WHILE_VARIABLE = new Functor() {
        public boolean isPartOfTheToken(char currentChar) {
            return Character.isJavaIdentifierPart(currentChar);
        }
    };
}
