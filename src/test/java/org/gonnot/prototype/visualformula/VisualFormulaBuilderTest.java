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
import static org.assertj.core.api.Assertions.assertThat;
import static org.gonnot.prototype.visualformula.VisualFormulaBuilder.bigDecimalFormula;
import static org.gonnot.prototype.visualformula.VisualFormulaBuilder.integerEvaluator;
import static org.gonnot.prototype.visualformula.VisualFormulaBuilder.integerFormula;
import static org.gonnot.prototype.visualformula.VisualFormulaBuilder.stringDumpEvaluator;
/**
 *
 */
@RunWith(Enclosed.class)
@SuppressWarnings("JavacQuirks")
public class VisualFormulaBuilderTest {
    public static class DocSampleTest {
        @Test
        public void testStringEvaluator() throws Exception {
            assertThat(VisualFormulaBuilder.init()
                             .line("3 + quantity * price - 10")
                             .compile()
                             .variable("quantity", 2)
                             .variable("price", 3)
                             .executeWith(stringDumpEvaluator()))
                  .isEqualTo("((3 + (quantity x price)) - 10)");
        }


        @Test
        public void testIntegerEvaluator() throws Exception {
            assertThat(VisualFormulaBuilder.init()
                             .line("3 + quantity * price - 10")
                             .compile(integerFormula())
                             .variable("quantity", 2)
                             .variable("price", 3)
                             .executeWith(integerEvaluator()))
                  .isEqualTo(-1);
        }


        @Test
        public void testTwoConsecutiveCalculation() throws Exception {
            VisualFormula<Integer> formula = VisualFormulaBuilder.init()
                  .line("3 + value")
                  .compile(integerFormula());

            assertThat(formula.variable("value", 2).executeWith(integerEvaluator())).isEqualTo(5);
            assertThat(formula.variable("value", 3).executeWith(integerEvaluator())).isEqualTo(6);
        }


        @Test
        public void testOneVisualDivision() throws Exception {

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
        public void testTwoVisualDivisions() throws Exception {

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
        public void testOneSimpleVariable() throws Exception {
            assertThat(VisualFormulaBuilder.init()
                             .line("exposure")
                             .compile(integerFormula())
                             .variable("exposure", 5)
                             .executeWith(integerEvaluator()))
                  .isEqualTo(5);
        }


        @Test
        public void testOneSimpleVariableInString() throws Exception {
            assertThat(VisualFormulaBuilder.init()
                             .line("exposure")
                             .compile(bigDecimalFormula())
                             .variable("exposure", null)
                             .executeWith(stringDumpEvaluator()))
                  .isEqualTo("exposure");
        }


        @Test
        @Ignore("Not sure that it's a good idea to handle such case")
        public void testOneSimpleNegativeVariable() throws Exception {
            assertThat(VisualFormulaBuilder.init()
                             .line("-exposure")
                             .compile(integerFormula())
                             .variable("exposure", 5)
                             .executeWith(integerEvaluator()))
                  .isEqualTo(-5);
        }


        @Test
        public void testMinusOperatorOnVariables() throws Exception {
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
        public void testOneSimpleConstant() throws Exception {
            assertFormula("5").equalsTo(5);
        }


        @Test
        public void testOneSimpleNegativeConstant() throws Exception {
            int expected = -5;
            assertFormula("-5").equalsTo(expected);
        }


        @Test
        public void testAddTwoConstants() throws Exception {
            assertFormula("5 + 2").equalsTo(7);

            assertFormula("3 + 5").equalsTo(8);
        }


        @Test
        public void testAddTwoConstantsWithOneNegative() throws Exception {
            assertFormula("5 + -2").equalsTo(3);
        }


        @Test
        public void testAddMoreConstants() throws Exception {
            assertFormula("5 + 2 + 3").equalsTo(10);
        }


        @Test
        public void testMinusTwoConstants() throws Exception {
            assertFormula("5 - 2").equalsTo(3);
        }


        @Test
        public void testMinusAndAddMoreConstants() throws Exception {
            assertFormula("5 - 2 + 10").equalsTo(13);

            int expected = -4;
            assertFormula("5 + 1 - 10").equalsTo(expected);
        }


        @Test
        public void testMultiplyTwoConstants() throws Exception {
            assertFormula("5 * 2").equalsTo(10);
        }


        @Test
        public void testMultiplyThreeConstants() throws Exception {
            assertFormula("1 * 2 * 3").equalsTo("((1 x 2) x 3)");
        }


        @Test
        public void testMultiplyTwoConstantsAndAdd() throws Exception {
            assertFormula("5 * 2 + 1").equalsTo(11);
        }


        @Test
        public void testDivideTwoConstants() throws Exception {
            assertFormula("10 / 2").equalsTo(5);
        }


        @Test
        public void testDivideThreeConstants() throws Exception {
            assertFormula("30 / 3 / 2").equalsTo("((30 / 3) / 2)");
        }


        @Test
        public void testDivideTwoConstantsAndAdd() throws Exception {
            assertFormula("100 / 50 + 1").equalsTo(100 / 50 + 1);
        }
    }
    public static class NaturalPrioritiesTest {
        @Test
        public void testNaturalPriorities() throws Exception {
            assertFormula("1 + 2 * 3").equalsTo(1 + 2 * 3);
            assertFormula("3 + 2 + 2 * 3 + 2").equalsTo(3 + 2 + 2 * 3 + 2);
            assertFormula("1 + 2 + 3 * 4").equalsTo(1 + 2 + 3 * 4);
            assertFormula("1 + 2 * 3 * 4 + 5").equalsTo(1 + 2 * 3 * 4 + 5);
            assertFormula("1 + 2 * 3 + 4 * 5").equalsTo(1 + 2 * 3 + 4 * 5);
        }


        @Test
        public void testNaturalPrioritiesInString() throws Exception {
            assertFormula("1 + 2 * 3 * 4 + 5").equalsTo("((1 + ((2 x 3) x 4)) + 5)");
            assertFormula("1 + 2 * 3 + 4").equalsTo("((1 + (2 x 3)) + 4)");
            assertFormula("1 + 2 * 3 + 4 * 5").equalsTo("((1 + (2 x 3)) + (4 x 5))");
        }


        @Test
        public void testWhenNoBaseFormulaBuildFromTop() throws Exception {
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
        public void testOneFormulaSurroundedByWhiteSpace() throws Exception {
            assertFormula("         ",
                          "1 + 2 * 3",
                          "         ")
                  .equalsTo(1 + 2 * 3);
        }
    }
    public static class VisualFormulaTest {
        @Test
        public void testOneVisualDivision() throws Exception {
            assertFormula(" 100  ",
                          " ---- ",
                          "  50  ")
                  .equalsTo(100 / 50);
        }


        @Test
        public void testOneVisualDivisionWithAnAdditionAsNumerator() throws Exception {
            assertFormula(" 50 + 50 ",
                          " ------- ",
                          "   50    ")
                  .equalsTo((50 + 50) / 50);
        }


        @Test
        public void testAddToOneVisualDivision() throws Exception {
            assertFormula("     100  ",
                          " 3 + ---- ",
                          "      2   ")
                  .equalsTo(3 + (100 / 2));
        }


        @Test
        public void testComplexAddToOneVisualDivision() throws Exception {
            assertFormula("          90 + 10  ",
                          " 1 + 2  + -------  ",
                          "             2     ").equalsTo(1 + 2 + ((90 + 10) / 2));
        }


        @Test
        public void testTwoVisualDivisions() throws Exception {
            assertFormula(" 10        9   ",
                          " -- + 2 + ---  ",
                          " 10        3   ")
                  .equalsTo("(((10 / 10) + 2) + (9 / 3))")
                  .equalsTo(1 + 2 + 3);
        }


        @Test
        public void testVisualDivisionsBelowDividingAnotherOne() throws Exception {
            assertFormula("  10             ",
                          "  --             ",
                          "   2         9   ",
                          " ---- + 2 + ---  ",
                          "   5         3   ")
                  .equalsTo("((((10 / 2) / 5) + 2) + (9 / 3))")
                  .equalsTo(1 + 2 + 3);
        }


        @Test
        public void testVisualDivisionsAboveDividingAnotherOne() throws Exception {
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
        public void testBaseDivision() throws Exception {
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
        public void testBaseDivisionContainsMaxWhitespace() throws Exception {
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
        public void testMoreComplex() throws Exception {
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
                if (stringBuilder.length() > 0) {
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
