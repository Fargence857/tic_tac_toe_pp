/*******************************************************************************
 * Copyright 2013 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package ca.raihan.util;

/**
 *
 * @author Pranjal Raihan
 */
public final class Equality {
    
    private Equality() {
        throw new IllegalAccessError(getClass().getName());
    }
    
    
    
    
    public static <T> boolean equals(T obj1, T obj2) {
        return obj1 == null ? 
                obj2 == null ? true : obj2.equals(obj1) : 
                obj1.equals(obj2);
    }
    
    public static <T> boolean equalsNullIntolerant(T obj1, T obj2) {
        return obj1 == null ? 
                obj2 == null ? false : obj2.equals(obj1) : 
                obj1.equals(obj2);
    }
    
}
