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
import java.util.HashMap;
import java.util.Map;
/**
 *
 */
public class FormulaContext<T> {
    private Map<String, T> variables = new HashMap<String, T>();


    public void declare(String name, T value) {
        variables.put(name, value);
    }


    public T getValueOf(String name) {
        return variables.get(name);
    }
}
