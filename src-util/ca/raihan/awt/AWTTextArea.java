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

import java.awt.HeadlessException;
import java.awt.TextArea;

/**
 * A simple extension of the default AWT {@code TextArea}
 * 
 * @author Pranjal Raihan
 */
public class AWTTextArea extends TextArea {
    
    /**
     * The {@code Properties} Object for subclasses to use; can be changed 
     * to a subclass of {@code Properties} to modify existing or creating 
     * custom properties
     */
    protected Properties properties = Properties.getProperties();
    
    
    
    
    /**
     * Stores the properties that this class uses
     */
    protected static class Properties {
        
        /**
         * Create and display both vertical and horizontal scrollbars.
         */
        public static final int SCROLLBARS_BOTH = TextArea.SCROLLBARS_BOTH;

        /**
         * Create and display vertical scrollbar only.
         */
        public static final int SCROLLBARS_VERTICAL_ONLY 
                = TextArea.SCROLLBARS_VERTICAL_ONLY;

        /**
         * Create and display horizontal scrollbar only.
         */
        public static final int SCROLLBARS_HORIZONTAL_ONLY 
                = TextArea.SCROLLBARS_HORIZONTAL_ONLY;

        /**
         * Do not create or display any scrollbars for the text area.
         */
        public static final int SCROLLBARS_NONE = TextArea.SCROLLBARS_NONE;
        
        /**
         * Singleton instance
         */
        private static final Properties properties = new Properties();
        
        
        
        
        /**
         * Private constructor
         */
        private Properties() {}
        
        /**
         * Returns the singleton {@code ClassProperties} Object
         * 
         * @return the singleton {@code ClassProperties} Object
         */
        public static Properties getProperties() {
            return properties;
        }
        
        
        
        
        /**
         * Returns the constant for displaying both scrollbars
         * 
         * @return the constant for displaying both scrollbars
         */
        public int getScrollBarsBoth() {
            return SCROLLBARS_BOTH;
        }
        
        /**
         * Returns the constant for displaying the vertical scrollbar
         * 
         * @return the constant for displaying the vertical scrollbar
         */
        public int getScrollBarsVerticalOnly() {
            return SCROLLBARS_VERTICAL_ONLY;
        }
        
        /**
         * Returns the constant for displaying the horizontal scrollbar
         * 
         * @return the constant for displaying the horizontal scrollbar
         */
        public int getScrollBarsHorizontalOnly() {
            return SCROLLBARS_HORIZONTAL_ONLY;
        }
        
        /**
         * Returns the constant for displaying the no scrollbars
         * 
         * @return the constant for displaying the no scrollbars
         */
        public int getScrollBarsNone() {
            return SCROLLBARS_NONE;
        }
        
    }
    
    /**
     * Constructs a new text area with the empty string as text.
     * This text area is created with scrollbar visibility equal to
     * {@link #SCROLLBARS_BOTH}, so both vertical and horizontal
     * scrollbars will be visible for this text area.
     * @exception HeadlessException if
     *    <code>GraphicsEnvironment.isHeadless</code> returns true
     * @see java.awt.GraphicsEnvironment#isHeadless()
     */
    public AWTTextArea() throws HeadlessException {
        super();
    }
    
    /**
     * Constructs a new text area with the specified text.
     * This text area is created with scrollbar visibility equal to
     * {@link #SCROLLBARS_BOTH}, so both vertical and horizontal
     * scrollbars will be visible for this text area.
     * @param      text       the text to be displayed; if
     *             <code>text</code> is <code>null</code>, the empty
     *             string <code>""</code> will be displayed
     * @exception HeadlessException if
     *        <code>GraphicsEnvironment.isHeadless</code> returns true
     * @see java.awt.GraphicsEnvironment#isHeadless()
     */
    public AWTTextArea(String text) throws HeadlessException {
        this(text, 0, 0, Properties.SCROLLBARS_BOTH);
    }
    
    /**
     * Constructs a new text area with the specified number of
     * rows and columns and the empty string as text.
     * A column is an approximate average character
     * width that is platform-dependent.  The text area is created with
     * scrollbar visibility equal to {@link Properties#SCROLLBARS_BOTH}, so both
     * vertical and horizontal scrollbars will be visible for this
     * text area.
     * @param rows the number of rows
     * @param columns the number of columns
     * @exception HeadlessException if
     *     <code>GraphicsEnvironment.isHeadless</code> returns true
     * @see java.awt.GraphicsEnvironment#isHeadless()
     */
    public AWTTextArea(int rows, int columns) throws HeadlessException {
        this("", rows, columns, Properties.SCROLLBARS_BOTH);
    }
    
    /**
     * Constructs a new text area with the specified text,
     * and with the specified number of rows and columns.
     * A column is an approximate average character
     * width that is platform-dependent.  The text area is created with
     * scrollbar visibility equal to {@link #SCROLLBARS_BOTH}, so both
     * vertical and horizontal scrollbars will be visible for this
     * text area.
     * @param      text       the text to be displayed; if
     *             <code>text</code> is <code>null</code>, the empty
     *             string <code>""</code> will be displayed
     * @param     rows      the number of rows
     * @param     columns   the number of columns
     * @exception HeadlessException if
     *   <code>GraphicsEnvironment.isHeadless</code> returns true
     * @see java.awt.GraphicsEnvironment#isHeadless()
     */
    public AWTTextArea(String text, int rows, int columns)
        throws HeadlessException {
        this(text, rows, columns, Properties.SCROLLBARS_BOTH);
    }
    
    /**
     * Constructs a new text area with the specified text,
     * and with the rows, columns, and scroll bar visibility
     * as specified.
     * <p>
     * The <code>TextArea</code> class defines several constants
     * that can be supplied as values for the
     * <code>scrollbars</code> argument:
     * <ul>
     * <li><code>SCROLLBARS_BOTH</code>,
     * <li><code>SCROLLBARS_VERTICAL_ONLY</code>,
     * <li><code>SCROLLBARS_HORIZONTAL_ONLY</code>,
     * <li><code>SCROLLBARS_NONE</code>.
     * </ul>
     * Any other value for the
     * <code>scrollbars</code> argument is invalid and will result in
     * this text area being created with scrollbar visibility equal to
     * the default value of {@link #SCROLLBARS_BOTH}.
     * @param      text       the text to be displayed; if
     *             <code>text</code> is <code>null</code>, the empty
     *             string <code>""</code> will be displayed
     * @param      rows       the number of rows; if
     *             <code>rows</code> is less than <code>0</code>,
     *             <code>rows</code> is set to <code>0</code>
     * @param      columns    the number of columns; if
     *             <code>columns</code> is less than <code>0</code>,
     *             <code>columns</code> is set to <code>0</code>
     * @param      scrollbars  a constant that determines what
     *             scrollbars are created to view the text area
     * @exception HeadlessException if
     *    <code>GraphicsEnvironment.isHeadless</code> returns true
     * @see java.awt.GraphicsEnvironment#isHeadless()
     */
    public AWTTextArea(String text, int rows, int columns, int scrollbars)
        throws HeadlessException {
        super(text, rows, columns, scrollbars);
    }
    
}
