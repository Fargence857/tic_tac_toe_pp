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

package ca.raihan.event;

import java.util.HashSet;
import java.util.Set;

import ca.raihan.util.Contract;

/**
 * Base class for events.
 * 
 * @author Pranjal Raihan
 * @param <T>
 */
public abstract class AbstractEvent<T extends EventContext> implements 
        Event<T> {
    
    protected final Set<EventListener<T>> listeners = new HashSet<>();
    
    protected final Object mutex = new Object();
    
    
    
    
    /**
     * Adds a new listener to the event, 
     * {@link ConfigEventListener#onEvent(Object, ConfigEventContext) 
     * onEvent(sender, context)} will be called when the event is raised.
     * 
     * @param listener the listener to add
     * 
     * @throws NullPointerException if {@code listener} is {@code null}
     */
    public void addListener(EventListener<T> listener) {
        Contract.nonNull(listener);
        synchronized (mutex) {
            listeners.add(listener);
        }
    }
    
    /**
     * Removes a specified listener from the event. Returns {@code true} if the 
     * listener was removed, otherwise returns {@code false}.
     * 
     * @param listener the listener to remove. a {@code null} value will always 
     * return {@code false}
     * 
     * @return {@code true} if the listener was removed, otherwise 
     * {@code false}.
     */
    public boolean removeListener(EventListener<T> listener) {
        if (listener == null)
            return false;
        synchronized (mutex) {
            return listeners.remove(listener);
        }
    }
    
    /**
     * Returns {@code true} if any listeners are currently listening to this 
     * event, else returns {@code false}.
     * 
     * @return {@code true} if any listeners are currently listening to this 
     * event, else {@code false}.
     */
    public boolean hasListeners() {
        return !listeners.isEmpty();
    }
    
    protected void raiseImpl(Object sender, T context) {
        for (EventListener<T> listener : listeners) {
            if (listener != null) {
                listener.onEvent(sender, context);
            }
        }  
    }
    
}
