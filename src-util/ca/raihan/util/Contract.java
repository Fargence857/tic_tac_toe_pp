/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
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

import ca.raihan.util.internal.__UninstantiableImpl;

/**
 * Helper class that checks user compliance.
 * 
 * @author Pranjal Raihan
 */
public final class Contract extends __UninstantiableImpl {
    
    /**
     * Exception format
     */
    static final String NULL_POINTER_EXCEPTION_FORMAT = "%s cannot be null";
    
    
    
    
    private Contract() {
    }
    
    
    
    
    public static <T> T nonNull(T obj) {
        if (obj == null)
            throw new NullPointerException("unexpected null pointer");
        return obj;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> void nonNulls(T... objects) {
        for (T obj : objects) {
            nonNull(obj);
        }
    }
    
    /**
     * Checks for {@code null} contract violation.
     * 
     * @param <T> any reference type
     * @param obj instance of reference type {@code T}
     * @param name the name of the object
     * 
     * @return {@code obj}
     * 
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    public static <T> T nonNull(T obj, String name) {
        if (obj == null)
            throw new NullPointerException(
                    String.format(NULL_POINTER_EXCEPTION_FORMAT, name));
        return obj;
    }
    
    /**
     * If {@code predicate} is {@code false}, an 
     * {@code IllegalArgumentException} is thrown.
     * 
     * @param predicate the requirement
     * 
     * @throws IllegalArgumentException if (@code predicate} is {@code false}
     */
    public static void require(boolean predicate) 
            throws IllegalArgumentException {
        if (!predicate)
            throw new IllegalArgumentException("Contract violated");
    }
    
    /**
     * If {@code predicate} is {@code false}, an 
     * {@code IllegalArgumentException} is thrown.
     * 
     * @param predicate the requirement
     * @param message the message of the exception
     * 
     * @throws IllegalArgumentException if (@code predicate} is {@code false}
     */
    public static void require(boolean predicate, String message) 
            throws IllegalArgumentException {
        if (!predicate)
            throw new IllegalArgumentException(message);
    }
    
}
