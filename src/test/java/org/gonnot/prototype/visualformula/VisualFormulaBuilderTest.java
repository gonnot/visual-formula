package org.gonnot.prototype.visualformula;
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


    private static void assertFormula(String formula, int expected) {
        assertThat(VisualFormulaBuilder.init()
                         ._(formula)
                         .compile()
                         .compute(),
                   is(expected));
    }
}
