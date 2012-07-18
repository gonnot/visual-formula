Visual-Formula
==============

*Makes mathematical formulas more readable in java*

Description
-----------

This library aims to validate the concept of a "*visual mathematical formula evaluator library*".

**Initial concept**
```java
    VisualFormula formula = VisualFormulaBuilder.initFormula()
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

Goals
-----

+ Simple to use
+ No external dependencies
+ Fast
+ Highly open to extension

Current Status
--------------

+ The library handles one line formula with variables

```java
    int result = VisualFormulaBuilder.init()
                     ._("3 + quantity * price - 10")
                     .compile(integerFormula())
                     .variable("quantity", 2)
                     .variable("price", 3)
                     .executeWith(integerEvaluator())
    result; // -1
```

+ One Formula can be reused more than once to do calculation

```java
    VisualFormula<Integer> formula = VisualFormulaBuilder.init()
          ._("3 + value")
          .compile(integerFormula());

    formula.variable("value", 2).executeWith(integerEvaluator()); // is(5)
    formula.variable("value", 3).executeWith(integerEvaluator()); // is(6)
``` 

+ It's possible to dump the parsed tree using the ```stringDumpEvaluator```

```java
    String result = VisualFormulaBuilder.init()
                     ._("3 + quantity * price - 10")
                     .compile(integerFormula())
                     .variable("quantity", 2)
                     .variable("price", 3)
                     .executeWith(stringDumpEvaluator())

    result; // "((3 + (quantity x price)) - 10)"
```

*All these examples comes from unit tests (see ```VisualFormulaBuilderTest.DocSampleTest```)*

What's next
-----------

* Handle parenthesis
* ~~Improve lexer in order to store location in the token (row and column) - required for the next step~~
* Handle visual division
    * ~~lexer~~
    * evaluator
* Improve API (the current public API is not really user friendly)
