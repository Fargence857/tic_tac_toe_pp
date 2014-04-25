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

/**
 * A non null-able value holder.
 * 
 * @author Pranjal Raihan
 * 
 * @param <T> any reference type
 */
public final class NonNullable<T> {
    
    private static final String TO_STRING_FORMAT = "NonNullable {value=%s}";
    
    protected T value;
    
    
    
    
    protected NonNullable() {
    }
    
    public NonNullable(T value) {
        setValue(value);
    }
    
    
    
    
    public T getValue() {
        if (value == null)
            throw new IllegalStateException(
                    "non-nullable value not initialized");
        return value;
    }
    
    public void setValue(T value) {
        if (value == null)
            throw new IllegalStateException(
                    "NonNullable cannot have null value");
        this.value = value;
    }
    
    public boolean hasValue() {
        return value != null;
    }
    
    
    
    
    @Override
    public String toString() {
        T v = getValue();
        return String.format(
                TO_STRING_FORMAT, 
                v != null ? v.toString() : "<no value>");
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (!hasValue())
            return o instanceof NonNullable && !((NonNullable) o).hasValue();
        if (o instanceof NonNullable) {
            NonNullable nn = (NonNullable) o;
            if (!nn.hasValue())
                return false;
            return getValue().equals(nn.getValue());
        }
        return getValue().equals(o);
    }
    
    @Override
    public int hashCode() {
        return !hasValue() ? 0 : getValue().hashCode();
    }
    
}
