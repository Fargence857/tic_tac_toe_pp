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

import java.awt.Graphics;

/**
 * Used by any {@code Component} (or else) that a border can be painted
 * on
 * 
 * @author Pranjal Raihan
 */
public interface Borderable {
    
    /**
     * Returns {@code true} if the border is to be drawn
     * 
     * @return {@code true} if the border is to be drawn
     */
    boolean isBorderShown();
    
    /**
     * Sets the visibility of the border.
     * <p>
     * <b>NOTE:</b>
     * <br>
     * It is best to rid of the {@code LayoutManager} of the parent or to use
     * one that does not interfere with the component's size
     * 
     * @param aFlag if the border is to be drawn
     */
    void setBorderShown(boolean aFlag);
    
    
    
    
    /**
     * Paints the component's border.
     * <p>
     * If you override this in a subclass you should not make permanent
     * changes to the passed in <code>Graphics</code>. For example, you
     * should not alter the clip <code>Rectangle</code> or modify the
     * transform. If you need to do these operations you may find it
     * easier to create a new <code>Graphics</code> from the passed in
     * <code>Graphics</code> and manipulate it.
     *
     * @param g  the <code>Graphics</code> context in which to paint
     */
    void paintBorder(Graphics g);
    
}
