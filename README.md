Visual-Formula
==============

*Makes mathematical formulas more readable in java*

Description:
------------

A visual mathematical formula evaluator library.

```java
VisualFormula formula = VisualFormulaBuilder.initFormula(double|bigdecimal|...)
    ._("                           valuation        ")
    ._(" amount - quantity * ---------------------- ")
    ._("                      endQuantity * nominal ")
    ._("------------------------------------------- ")
    ._("            beginValuation                  ")
    .compile()
    .variable("valuation", 5)
    .variable("quantity", 10)
    ...;
```

Work in progress...

Features requests:
------------------
+ tbd

