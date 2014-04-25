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

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Pranjal Raihan
 * @param <T>
 */
public class NoNullArrayList<T> extends ArrayList<T> {
    
    public NoNullArrayList() {
        super();
    }
    
    public NoNullArrayList(int initialCapacity) {
        super(initialCapacity);
    }
    
    public NoNullArrayList(Collection<? extends T> c) {
        _addAll(c);
    }
    
    
    
    
    @Override
    public boolean add(T item) {
        return super.add(Contract.nonNull(item));
    }
    
    @Override
    public boolean addAll(Collection<? extends T> c) {
        return _addAll(c);
    }
    
    protected final boolean _addAll(Collection<? extends T> c) {
        if (c == null)
            return false;
        Object[] array = c.toArray();
        for (Object item : array)
            if (item == null)
                throw new NullPointerException(
                    "Cannot add null value to null intolerant list");
        return super.addAll(c);
    }
    
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        Object[] array = c.toArray();
        for (Object item : array)
            if (item == null)
                throw new NullPointerException(
                    "Cannot add null value to null intolerant list");
        return super.addAll(index, c);
    }
    
    @Override
    public boolean remove(Object o) {
        return o == null ? false : super.remove(o);
    }
    
}
