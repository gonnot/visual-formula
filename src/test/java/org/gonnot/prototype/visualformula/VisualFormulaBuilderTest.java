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
import org.gonnot.prototype.visualformula.VisualFormulaBuilder.VisualFormula;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import static net.codjo.test.common.matcher.JUnitMatchers.*;
import static org.gonnot.prototype.visualformula.VisualFormulaBuilder.bigDecimalFormula;
import static org.gonnot.prototype.visualformula.VisualFormulaBuilder.integerEvaluator;
import static org.gonnot.prototype.visualformula.VisualFormulaBuilder.integerFormula;
import static org.gonnot.prototype.visualformula.VisualFormulaBuilder.stringDumpEvaluator;
/**
 *
 */
@RunWith(Enclosed.class)
public class VisualFormulaBuilderTest {
    public static class VariablesTest {
        @Test
        public void testOneSimpleVariable() throws Exception {
            assertThat(VisualFormulaBuilder.init()
                             ._("exposure")
                             .compile(integerFormula())
                             .variable("exposure", 5)
                             .execute(integerEvaluator()), is(5));
        }


        @Test
        public void testOneSimpleVariableInString() throws Exception {
            assertThat(VisualFormulaBuilder.init()
                             ._("exposure")
                             .compile(bigDecimalFormula())
                             .variable("exposure", null)
                             .execute(stringDumpEvaluator()), is("exposure"));
        }


        @Test
        @Ignore("Not sure that it's a good idea to handle such case")
        public void testOneSimpleNegativeVariable() throws Exception {
            assertThat(VisualFormulaBuilder.init()
                             ._("-exposure")
                             .compile(integerFormula())
                             .variable("exposure", 5)
                             .execute(integerEvaluator()), is(-5));
        }


        @Test
        public void testMinusOperatorOnVariables() throws Exception {
            assertThat(VisualFormulaBuilder.init()
                             ._("price - rebate")
                             .compile(integerFormula())
                             .variable("price", 5)
                             .variable("rebate", 2)
                             .compute(), is(3));
        }
    }
    public static class ConstantTest {
        @Test
        public void testOneSimpleConstant() throws Exception {
            assertFormula("5", 5);
        }


        @Test
        public void testOneSimpleNegativeConstant() throws Exception {
            assertFormula("-5", -5);
        }


        @Test
        public void testAddTwoConstants() throws Exception {
            assertFormula("5 + 2", 7);

            assertFormula("3 + 5", 8);
        }


        @Test
        public void testAddTwoConstantsWithOneNegative() throws Exception {
            assertFormula("5 + -2", 3);
        }


        @Test
        public void testAddMoreConstants() throws Exception {
            assertFormula("5 + 2 + 3", 10);
        }


        @Test
        public void testMinusTwoConstants() throws Exception {
            assertFormula("5 - 2", 3);
        }


        @Test
        public void testMinusAndAddMoreConstants() throws Exception {
            assertFormula("5 - 2 + 10", 13);

            assertFormula("5 + 1 - 10", -4);
        }


        @Test
        public void testMultiplyTwoConstants() throws Exception {
            assertFormula("5 * 2", 10);
        }


        @Test
        public void testMultiplyThreeConstants() throws Exception {
            assertFormula("1 * 2 * 3", "((1 x 2) x 3)");
        }


        @Test
        public void testMultiplyTwoConstantsAndAdd() throws Exception {
            assertFormula("5 * 2 + 1", 11);
        }


        @Test
        public void testDivideTwoConstants() throws Exception {
            assertFormula("10 / 2", 5);
        }


        @Test
        public void testDivideThreeConstants() throws Exception {
            assertFormula("30 / 3 / 2", "((30 / 3) / 2)");
        }


        @Test
        public void testDivideTwoConstantsAndAdd() throws Exception {
            assertFormula("100 / 50 + 1", 100 / 50 + 1);
        }
    }
    public static class NaturalPrioritiesTest {
        @Test
        public void testNaturalPriorities() throws Exception {
            assertFormula("1 + 2 * 3", 1 + 2 * 3);
            assertFormula("3 + 2 + 2 * 3 + 2", 3 + 2 + 2 * 3 + 2);
            assertFormula("1 + 2 + 3 * 4", 1 + 2 + 3 * 4);
            assertFormula("1 + 2 * 3 * 4 + 5", 1 + 2 * 3 * 4 + 5);
            assertFormula("1 + 2 * 3 + 4 * 5", 1 + 2 * 3 + 4 * 5);
        }


        @Test
        public void testNaturalPrioritiesInString() throws Exception {
            assertFormula("1 + 2 * 3 * 4 + 5", "((1 + ((2 x 3) x 4)) + 5)");
            assertFormula("1 + 2 * 3 + 4", "((1 + (2 x 3)) + 4)");
            assertFormula("1 + 2 * 3 + 4 * 5", "((1 + (2 x 3)) + (4 x 5))");
        }
    }


    private static void assertFormula(String stringFormula, int expected) {
        VisualFormula formula = VisualFormulaBuilder.init()
              ._(stringFormula)
              .compile();

        assertThat(formula.compute(), describedAs("formula '"
                                                  + stringFormula
                                                  + "' has been wrongly parsed <'" + formula.dumpTree() + "'>", is(expected)));
    }


    private static void assertFormula(String input, String expectedTranslation) {
        assertThat(VisualFormulaBuilder.init()
                         ._(input)
                         .compile().dumpTree(), is(expectedTranslation));
    }
}
