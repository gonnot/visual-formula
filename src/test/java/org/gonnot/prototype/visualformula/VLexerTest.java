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
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import static net.codjo.test.common.matcher.JUnitMatchers.*;
/**
 *
 */
@RunWith(Enclosed.class)
public class VLexerTest {
    public static class RealSampleTest {
        @Test
        public void testOneSimpleAddition() throws Exception {
            assertLexerResult("10+20", "{NUMBER(10)-C:0}", "{ADD-C:2}", "{NUMBER(20)-C:3}");
            assertLexerResult("10 + 20", "{NUMBER(10)-C:0}", "{ADD-C:3}", "{NUMBER(20)-C:5}");
        }


        @Test
        public void testOneSimpleMultiplication() throws Exception {
            assertLexerResult("10*-20", "{NUMBER(10)-C:0}", "{MULTIPLY-C:2}", "{NUMBER(-20)-C:3}");
        }
    }
    public static class BasicOperatorTest {
        @Test
        public void testOperatorPlus() throws Exception {
            assertLexerResult("+", "{ADD-C:0}");
            assertLexerResult(" + ", "{ADD-C:1}");
        }


        @Test
        public void testOperatorMinus() throws Exception {
            assertLexerResult("-", "{MINUS-C:0}");
            assertLexerResult(" - ", "{MINUS-C:1}");
        }


        @Test
        public void testOperatorMultiply() throws Exception {
            assertLexerResult("*", "{MULTIPLY-C:0}");
            assertLexerResult(" * ", "{MULTIPLY-C:1}");
        }


        @Test
        public void testOperatorDivide() throws Exception {
            assertLexerResult("/", "{DIVIDE-C:0}");
            assertLexerResult(" / ", "{DIVIDE-C:1}");
        }
    }
    public static class BasicOperandTest {
        @Test
        public void testNumber() throws Exception {
            assertLexerResult("1", "{NUMBER(1)-C:0}");
        }


        @Test
        public void testNegativeNumber() throws Exception {
            assertLexerResult("-1", "{NUMBER(-1)-C:0}");
        }


        @Test
        public void testBigNumber() throws Exception {
            assertLexerResult("12345", "{NUMBER(12345)-C:0}");
            assertLexerResult("12345  ", "{NUMBER(12345)-C:0}");
            assertLexerResult("  12345  ", "{NUMBER(12345)-C:2}");
        }


        @Test
        public void testNumberFloat() throws Exception {
            assertLexerResult("1.5", "{NUMBER(1.5)-C:0}");
            assertLexerResult("1.", "{NUMBER(1.)-C:0}");
        }


        @Test
        public void testVariable() throws Exception {
            assertLexerResult("quantity", "{VARIABLE(quantity)-C:0}");
            assertLexerResult(" quantity ", "{VARIABLE(quantity)-C:1}");
        }
    }


    private static void assertLexerResult(String line, String... expectedTokens) {
        VLexer lexer = new VLexer();
        List<VToken> tokens = lexer.parse(line);
        assertThat(convertTokenToString(tokens).toString(), is(Arrays.asList(expectedTokens).toString()));
    }


    private static List<String> convertTokenToString(List<VToken> tokens) {
        List<String> result = new ArrayList<String>(tokens.size());
        for (VToken token : tokens) {
            String value = token.getTokenInString() != null ? "(" + token.getTokenInString() + ")" : "";
            result.add("{" + token.getType() + value + "-C:" + token.getColumn() + "}");
        }
        return result;
    }
}
