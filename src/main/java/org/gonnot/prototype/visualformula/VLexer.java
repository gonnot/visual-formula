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
import java.util.Scanner;
/**
 *
 */
class VLexer {
    public List<VToken> parse(String line) {
        List<VToken> tokens = new ArrayList<VToken>();
        Scanner scanner = new Scanner(line);
        while (scanner.hasNext()) {
            tokens.add(tokenFrom(scanner.next()));
        }
        return tokens;
    }


    private VToken tokenFrom(String tokenInString) {
        if ("+".equals(tokenInString)) {
            return VToken.add();
        }
        if ("-".equals(tokenInString)) {
            return VToken.minus();
        }
        if ("*".equals(tokenInString)) {
            return VToken.multiply();
        }
        if ("/".equals(tokenInString)) {
            return VToken.divide();
        }
        return VToken.number(tokenInString);
    }
}
