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

package ca.raihan.awt;

import java.awt.event.WindowAdapter;

/**
 * A clone-able {@code WindowAdapter}
 * 
 * @see WindowAdapter
 * 
 * @author Pranjal Raihan
 */
abstract class CloneableWindowAdapter extends WindowAdapter implements 
        Cloneable {
    
    /**
     * Returns a clone of the {@code CloneableWindowAdapter}
     * 
     * @return a clone of the {@code CloneableWindowAdapter}
     */
    @SuppressWarnings("CloneDeclaresCloneNotSupported")
    public CloneableWindowAdapter clone() {
        try {
            return (CloneableWindowAdapter) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new InternalError();
        }
    }
    
}
