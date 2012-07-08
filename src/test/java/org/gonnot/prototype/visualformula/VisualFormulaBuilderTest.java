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
        VisualFormula formula = VisualFormulaBuilder.init()
              ._("5")
              .compile();

        assertThat(formula.compute(), is(5));
    }
}
