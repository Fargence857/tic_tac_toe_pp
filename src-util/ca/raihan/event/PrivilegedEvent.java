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

package ca.raihan.event;

import java.util.function.Predicate;

import ca.raihan.util.Contract;

/**
 *
 * @author Pranjal Raihan
 * @param <T>
 */
public final class PrivilegedEvent<T extends EventContext> extends 
        AbstractEvent<T> {
    
    private final Object key;
    
    private Predicate<Object> predicate;
    
    
    
    
    /**
     * Construct a new event with the specified key. This key will be used to 
     * raise events.
     * 
     * @param key the key to save a reference to
     * 
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public PrivilegedEvent(final Object key) {
        Contract.nonNull(key);
        this.key = key;
    }
    
    /**
     * Construct a new event with the specified predicate. This predicate will 
     * be used to raise events.
     * 
     * @param key the key to save a reference to
     * @param predicate the predicate
     * 
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public PrivilegedEvent(final Object key, Predicate<Object> predicate) {
        this(key);
        Contract.nonNull(predicate);
        this.predicate = predicate;
    }
    
    
    
    
    /**
     * Raises the event. Signals all listeners.
     * 
     * @param key the key to verify that the sender has permission to raise 
     * this event, a {@code ConfigEventException} will be thrown if the key 
     * specified here does not pass an identity check with the key that was 
     * specified during the event's construction.
     * 
     * @param sender the sender of the event
     * 
     * @param context the context of the event
     */
    public void raise(Object key, Object sender, T context) {
        if (predicate != null) {
            if (!predicate.test(key)) {
                throw new EventException("Invalid key: " + key);
            }
        } else if (this.key != key) {
            throw new EventException("Invalid key: " + key);
        }
        raiseImpl(sender, context);
    }
    
}
