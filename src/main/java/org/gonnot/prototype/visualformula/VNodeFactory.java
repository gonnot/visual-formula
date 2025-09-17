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

import org.gonnot.prototype.visualformula.VNode.VBinaryNode;
import org.gonnot.prototype.visualformula.VNode.VOperand;

/**
 *
 */
class VNodeFactory {

    private VNodeFactory() {
    }

    public static VNode operand(VToken token) {
        return new VOperand(token);
    }

    static VBinaryNode add(VToken operator) {
        return new VBinaryNode(operator, VNode.BASIC_OPERATION);
    }

    public static VBinaryNode minus(VToken operator) {
        return new VBinaryNode(operator, VNode.BASIC_OPERATION);
    }

    public static VBinaryNode multiply(VToken operator) {
        return new VBinaryNode(operator, VNode.PRIORITY_OPERATION);
    }

    public static VBinaryNode divide(VToken operator) {
        return new VBinaryNode(operator, VNode.PRIORITY_OPERATION);
    }

    public static VBinaryNode visualDivide(VToken operator) {
        return new VBinaryNode(operator, VNode.BASIC_OPERATION
                           /* TODO or should it be VNode.PRIORITY_OPERATION
                            -->  regarding the parsing algo a visual division is like a standard operand*/
        );
    }
}
