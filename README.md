Visual-Formula
==============

*Makes mathematical formulas more readable in java*

Description
-----------

This library is a proof of concept to validate the idea of a "*visual mathematical formula evaluator library*" in java.

#### Initial concept

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

#### Current status

*"Visual aspects"* are completely implemented. Despite a lack of parenthesis support (still need to merge the feature branch), the spike is a success.

Before to create a full-blown library, heavy performance tests (memory/speed) will be performed.
  

Goals
-----

+ Simple to use
+ No external dependencies
+ Fast
+ Highly open to extension

Samples
-------

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

+ It's possible to handle one visual division

```java
    int result = VisualFormulaBuilder.init()
          ._("                               ")
          ._("            price + serviceFee ")
          ._(" external * ------------------ ")
          ._("                 quantity      ")
          ._("                               ")
          .compile(integerFormula())
          .variable("external", 1)
          .variable("price", 10)
          .variable("serviceFee", 90)
          .variable("quantity", 2)
          .compute();

    result; // 50
```

+ It's possible to handle n visual division

```java
    int result = VisualFormulaBuilder.init()
          ._("                               ")
          ._("                    serviceFee ")
          ._("            price + ---------- ")
          ._("                      index    ")
          ._(" external * ------------------ ")
          ._("                 quantity      ")
          ._("                               ")
          .compile(integerFormula())
          .variable("external", 1)
          .variable("price", 10)
          .variable("serviceFee", 900)
          .variable("index", 10)
          .variable("quantity", 2)
          .compute();

    result; // 50
```

*All these examples comes from unit tests (see ```VisualFormulaBuilderTest.DocSampleTest```)*

What's next
-----------

* Handle parenthesis
* ~~Improve lexer in order to store location in the token (row and column) - required for the next step~~
* ~~Handle visual division~~
    * ~~lexer~~
    * ~~parser~~
        * ~~handle token range~~
        * ~~find base formula row index (base formula is the root formula that will be evaluated)~~
* Improve API (the current public API is not really user friendly)
* Do performance tests
* Transform the spike into a real library 
    * Documentation
    * Website
    * Deploy on a maven repository
    
