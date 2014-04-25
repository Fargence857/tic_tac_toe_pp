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
public final strictfp class Numbers {
    
    /**
     * 32-bit floating point number epsilon. Usually around {@code 1.1920929E-7}
     */
    public static final float       SINGLE_EPSILON;
    
    /**
     * 64-bit floating point number epsilon. 
     * Usually around {@code 2.220446049250313E-16}
     */
    public static final double      DOUBLE_EPSILON;
    
    
    
    
    static {
        float floatEpsilon = 1.0f;
        do
           floatEpsilon /= 2.0F;
        while (1.0F + (floatEpsilon / 2.0F) != 1.0F);
        SINGLE_EPSILON = floatEpsilon;
        
        double doubleEpsilon = 1.0D;
        do
           doubleEpsilon /= 2.0D;
        while (1.0D + (doubleEpsilon / 2.0D) != 1.0D);
        DOUBLE_EPSILON = doubleEpsilon;
    }
    
    
    
    
    /**
     * disable external initialization and via reflection
     */
    private Numbers() {
        throw new IllegalAccessError("NO NumberUtils INSTANCES FOR YOU!!!");
    }
    
    
    
        
    /**
     * Checks if the specified number is a known floating point number.
     * Delegates to {@link #isFP(java.lang.Class)} 
     * 
     * @param number the number to check
     * 
     * @return if the number is a floating point number
     */
    public static boolean isFP(Number number) {
        return number instanceof Float || number instanceof Double;
    }
    
    /**
     * Checks if the specified number is a known floating point number
     * 
     * @param clazz the class of the number to check
     * 
     * @return if the number is a floating point number
     */
    public static boolean isFP(Class<? extends Number> clazz) {
        return (clazz == Float.class || clazz == Double.class);
    }
    
    
    
    
    /**
     * Checks if two 32-bit floating point numbers are equal accounting for 
     * precision errors.
     * 
     * @param f1 a single precision float
     * @param f2 a single precision float
     * 
     * @return {@code true} if {@code f1} and {@code f2} have a difference in 
     * magnitude less than the accepted amount.
     * 
     * @see #SINGLE_EPSILON
     */
    public static boolean equals(float f1, float f2) {
        return Math.abs(f1 - f2) < SINGLE_EPSILON;
    }
    
    /**
     * Checks if two 64-bit floating point numbers are equal accounting for 
     * precision errors.
     * 
     * @param d1 a double precision float
     * @param d2 a double precision float
     * 
     * @return {@code true} if {@code d1} and {@code d2} have a difference in 
     * magnitude less than the accepted amount.
     * 
     * @see #DOUBLE_EPSILON
     */
    public static boolean equals(double d1, double d2) {
        return Math.abs(d1 - d2) < DOUBLE_EPSILON;
    }
    
}