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

/**
 *
 */
public interface VNodeVisitor<VISITOR_RESULT, VARIABLE_TYPE> {
    void init(FormulaContext<? extends VARIABLE_TYPE> context);

    VISITOR_RESULT visitNumber(String numberInString, VNode currentNode);

    VISITOR_RESULT visitVariable(String variableName, VNode currentNode);

    VISITOR_RESULT visitAdd(VNode leftOperand, VNode rightOperand, VNode currentNode);

    VISITOR_RESULT visitMinus(VNode leftOperand, VNode rightOperand, VNode currentNode);

    VISITOR_RESULT visitMultiply(VNode leftOperand, VNode rightOperand, VNode currentNode);

    VISITOR_RESULT visitDivide(VNode leftOperand, VNode rightOperand, VNode currentNode);
}