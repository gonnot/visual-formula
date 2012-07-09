package org.gonnot.prototype.visualformula;
import org.gonnot.prototype.visualformula.VisualFormulaBuilder.VisualFormula;
import org.junit.Test;
import static net.codjo.test.common.matcher.JUnitMatchers.*;
/**
 *
 */
public class VisualFormulaBuilderTest {
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
        assertFormula("100 / 50 + 1", 3);
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
