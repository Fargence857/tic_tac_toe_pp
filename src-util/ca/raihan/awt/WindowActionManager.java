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

/**
 * An interface for making a custom {@code WindowActionManager} that controls 
 * the behavior of the window's actions like closing, opening etc.
 * 
 * @author Pranjal Raihan
 */
public interface WindowActionManager {
    
    /**
     * Returns a fresh copy of the handler
     * 
     * @return a clone of the handler
     */
    public CloneableWindowAdapter getNewHandler();
    
}
