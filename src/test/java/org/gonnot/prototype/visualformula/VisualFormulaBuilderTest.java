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

import org.gonnot.prototype.visualformula.VisualFormulaBuilder.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gonnot.prototype.visualformula.VisualFormulaBuilder.*;

/**
 *
 */
@RunWith(Enclosed.class)
public class VisualFormulaBuilderTest {
    public static class DocSampleTest {
        @Test
        public void testStringEvaluator() {
            assertThat(VisualFormulaBuilder.init()
                                           .line("3 + quantity * price - 10")
                                           .compile()
                                           .variable("quantity", 2)
                                           .variable("price", 3)
                                           .executeWith(stringDumpEvaluator()))
              .isEqualTo("((3 + (quantity x price)) - 10)");
        }

        @Test
        public void testIntegerEvaluator() {
            assertThat(VisualFormulaBuilder.init()
                                           .line("3 + quantity * price - 10")
                                           .compile(integerFormula())
                                           .variable("quantity", 2)
                                           .variable("price", 3)
                                           .executeWith(integerEvaluator()))
              .isEqualTo(-1);
        }

        @Test
        public void testTwoConsecutiveCalculation() {
            VisualFormula<Integer> formula = VisualFormulaBuilder.init()
                                                                 .line("3 + value")
                                                                 .compile(integerFormula());

            assertThat(formula.variable("value", 2).executeWith(integerEvaluator())).isEqualTo(5);
            assertThat(formula.variable("value", 3).executeWith(integerEvaluator())).isEqualTo(6);
        }

        @Test
        public void testOneVisualDivision() {

            int result = VisualFormulaBuilder.init()
                                             .line("                               ")
                                             .line("            price + serviceFee ")
                                             .line(" external * ------------------ ")
                                             .line("                 quantity      ")
                                             .line("                               ")
                                             .compile(integerFormula())
                                             .variable("external", 1)
                                             .variable("price", 10)
                                             .variable("serviceFee", 90)
                                             .variable("quantity", 2)
                                             .compute();

            assertThat(result).isEqualTo(50);
        }

        @Test
        public void testTwoVisualDivisions() {

            int result = VisualFormulaBuilder.init()
                                             .line("                               ")
                                             .line("                    serviceFee ")
                                             .line("            price + ---------- ")
                                             .line("                      index    ")
                                             .line(" external * ------------------ ")
                                             .line("                 quantity      ")
                                             .line("                               ")
                                             .compile(integerFormula())
                                             .variable("external", 1)
                                             .variable("price", 10)
                                             .variable("serviceFee", 900)
                                             .variable("index", 10)
                                             .variable("quantity", 2)
                                             .compute();

            assertThat(result).isEqualTo(50);
        }
    }

    public static class VariablesTest {
        @Test
        public void testOneSimpleVariable() {
            assertThat(VisualFormulaBuilder.init()
                                           .line("exposure")
                                           .compile(integerFormula())
                                           .variable("exposure", 5)
                                           .executeWith(integerEvaluator()))
              .isEqualTo(5);
        }

        @Test
        public void testOneSimpleVariableInString() {
            assertThat(VisualFormulaBuilder.init()
                                           .line("exposure")
                                           .compile(bigDecimalFormula())
                                           .variable("exposure", null)
                                           .executeWith(stringDumpEvaluator()))
              .isEqualTo("exposure");
        }

        @Test
        @Ignore("Not sure that it's a good idea to handle such case")
        public void testOneSimpleNegativeVariable() {
            assertThat(VisualFormulaBuilder.init()
                                           .line("-exposure")
                                           .compile(integerFormula())
                                           .variable("exposure", 5)
                                           .executeWith(integerEvaluator()))
              .isEqualTo(-5);
        }

        @Test
        public void testMinusOperatorOnVariables() {
            assertThat(VisualFormulaBuilder.init()
                                           .line("price - rebate")
                                           .compile(integerFormula())
                                           .variable("price", 5)
                                           .variable("rebate", 2)
                                           .compute())
              .isEqualTo(3);
        }
    }

    public static class ConstantTest {
        @Test
        public void testOneSimpleConstant() {
            assertFormula("5").equalsTo(5);
        }

        @Test
        public void testOneSimpleNegativeConstant() {
            int expected = -5;
            assertFormula("-5").equalsTo(expected);
        }

        @Test
        public void testAddTwoConstants() {
            assertFormula("5 + 2").equalsTo(7);

            assertFormula("3 + 5").equalsTo(8);
        }

        @Test
        public void testAddTwoConstantsWithOneNegative() {
            assertFormula("5 + -2").equalsTo(3);
        }

        @Test
        public void testAddMoreConstants() {
            assertFormula("5 + 2 + 3").equalsTo(10);
        }

        @Test
        public void testMinusTwoConstants() {
            assertFormula("5 - 2").equalsTo(3);
        }

        @Test
        public void testMinusAndAddMoreConstants() {
            assertFormula("5 - 2 + 10").equalsTo(13);

            int expected = -4;
            assertFormula("5 + 1 - 10").equalsTo(expected);
        }

        @Test
        public void testMultiplyTwoConstants() {
            assertFormula("5 * 2").equalsTo(10);
        }

        @Test
        public void testMultiplyThreeConstants() {
            assertFormula("1 * 2 * 3").equalsTo("((1 x 2) x 3)");
        }

        @Test
        public void testMultiplyTwoConstantsAndAdd() {
            assertFormula("5 * 2 + 1").equalsTo(11);
        }

        @Test
        public void testDivideTwoConstants() {
            assertFormula("10 / 2").equalsTo(5);
        }

        @Test
        public void testDivideThreeConstants() {
            assertFormula("30 / 3 / 2").equalsTo("((30 / 3) / 2)");
        }

        @Test
        public void testDivideTwoConstantsAndAdd() {
            assertFormula("100 / 50 + 1").equalsTo(100 / 50 + 1);
        }
    }

    public static class NaturalPrioritiesTest {
        @Test
        public void testNaturalPriorities() {
            assertFormula("1 + 2 * 3").equalsTo(1 + 2 * 3);
            assertFormula("3 + 2 + 2 * 3 + 2").equalsTo(3 + 2 + 2 * 3 + 2);
            assertFormula("1 + 2 + 3 * 4").equalsTo(1 + 2 + 3 * 4);
            assertFormula("1 + 2 * 3 * 4 + 5").equalsTo(1 + 2 * 3 * 4 + 5);
            assertFormula("1 + 2 * 3 + 4 * 5").equalsTo(1 + 2 * 3 + 4 * 5);
        }

        @Test
        public void testNaturalPrioritiesInString() {
            assertFormula("1 + 2 * 3 * 4 + 5").equalsTo("((1 + ((2 x 3) x 4)) + 5)");
            assertFormula("1 + 2 * 3 + 4").equalsTo("((1 + (2 x 3)) + 4)");
            assertFormula("1 + 2 * 3 + 4 * 5").equalsTo("((1 + (2 x 3)) + (4 x 5))");
        }

        @Test
        public void testWhenNoBaseFormulaBuildFromTop() {
            assertFormula("  20  ",
                          " ---- ",
                          "   2  ",
                          " ---- ",
                          "   5  ")
              .equalsTo("((20 / 2) / 5)")
              .equalsTo(20 / 2 / 5);
        }
    }

    public static class MultiLineTest {
        @Test
        public void testOneFormulaSurroundedByWhiteSpace() {
            assertFormula("         ",
                          "1 + 2 * 3",
                          "         ")
              .equalsTo(1 + 2 * 3);
        }
    }

    public static class VisualFormulaTest {
        @Test
        public void testOneVisualDivision() {
            assertFormula(" 100  ",
                          " ---- ",
                          "  50  ")
              .equalsTo(100 / 50);
        }

        @Test
        public void testOneVisualDivisionWithAnAdditionAsNumerator() {
            assertFormula(" 50 + 50 ",
                          " ------- ",
                          "   50    ")
              .equalsTo((50 + 50) / 50);
        }

        @Test
        public void testAddToOneVisualDivision() {
            assertFormula("     100  ",
                          " 3 + ---- ",
                          "      2   ")
              .equalsTo(3 + (100 / 2));
        }

        @Test
        public void testComplexAddToOneVisualDivision() {
            assertFormula("          90 + 10  ",
                          " 1 + 2  + -------  ",
                          "             2     ").equalsTo(1 + 2 + ((90 + 10) / 2));
        }

        @Test
        public void testTwoVisualDivisions() {
            assertFormula(" 10        9   ",
                          " -- + 2 + ---  ",
                          " 10        3   ")
              .equalsTo("(((10 / 10) + 2) + (9 / 3))")
              .equalsTo(1 + 2 + 3);
        }

        @Test
        public void testVisualDivisionsBelowDividingAnotherOne() {
            assertFormula("  10             ",
                          "  --             ",
                          "   2         9   ",
                          " ---- + 2 + ---  ",
                          "   5         3   ")
              .equalsTo("((((10 / 2) / 5) + 2) + (9 / 3))")
              .equalsTo(1 + 2 + 3);
        }

        @Test
        public void testVisualDivisionsAboveDividingAnotherOne() {
            assertFormula("   5         9   ",
                          " ---- + 2 + ---  ",
                          "  10         3   ",
                          "  --             ",
                          "   2             ")
              .equalsTo("(((5 / (10 / 2)) + 2) + (9 / 3))")
              .equalsTo(1 + 2 + 3);
        }
    }

    public static class DetermineBaseFormulaTest {
        @Test
        public void testBaseDivision() {
            assertFormula("  250            ",
                          "  ---            ",
                          "   50         9  ",
                          " ----- + 2 + --- ",
                          "   10         3  ",
                          "   --            ",
                          "    2            ")
              .equalsTo("((((250 / 50) / (10 / 2)) + 2) + (9 / 3))")
              .equalsTo(1 + 2 + 3);
        }

        @Test
        public void testBaseDivisionContainsMaxWhitespace() {
            assertFormula("  250         90 ",
                          "  ---         -- ",
                          "   50         10 ",
                          " ----- + 2 + --- ",
                          "   10         30 ",
                          "   --        --- ",
                          "    2         10 ")
              .equalsTo("((((250 / 50) / (10 / 2)) + 2) + ((90 / 10) / (30 / 10)))")
              .equalsTo(1 + 2 + 3);
        }

        @Test
        public void testMoreComplex() {
            assertFormula("  250         90       100 ",
                          "  ---         -- + 1 - --- ",
                          "   50         10       100 ",
                          " ----- + 2 + ------------- ",
                          "   10             30       ",
                          "   --             --       ",
                          "    2             10       ")
              .equalsTo("((((250 / 50) / (10 / 2)) + 2) + ((((90 / 10) + 1) - (100 / 100)) / (30 / 10)))")
              .equalsTo(1 + 2 + 3);
        }
    }

    private static FormulaAssert assertFormula(String... stringFormula) {
        return new FormulaAssert(stringFormula);
    }

    private static class FormulaAssert {
        private final String formulaInString;
        private final VisualFormula<Integer> formula;

        private FormulaAssert(String... stringFormulas) {
            VisualFormulaBuilder builder = VisualFormulaBuilder.init();
            for (String formulaRow : stringFormulas) {
                builder.line(formulaRow);
            }
            formula = builder.compile(integerFormula());

            formulaInString = toString(stringFormulas);
        }

        void equalsTo(Integer expected) {
            assertThat(formula.executeWith(integerEvaluator()))
              .as("formula '" + formulaInString + "' has been wrongly parsed <'" + formula.dumpTree() + "'>")
              .isEqualTo(expected);
        }

        FormulaAssert equalsTo(String expected) {
            assertThat(formula.dumpTree()).isEqualTo(expected);
            return this;
        }

        private static String toString(String[] stringFormulas) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String formulaRow : stringFormulas) {
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append("\n");
                }
                stringBuilder.append(formulaRow);
            }

            if (stringBuilder.indexOf("\n") != -1) {
                stringBuilder.insert(0, "\n").append("\n");
            }
            return stringBuilder.toString();
        }
    }
}
