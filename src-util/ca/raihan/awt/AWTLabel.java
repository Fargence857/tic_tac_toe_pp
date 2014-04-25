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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Label;

/**
 * A simple extension of the default AWT {@code Label}
 * 
 * @see Label
 * 
 * @author Pranjal Raihan
 */
public class AWTLabel extends Label implements Borderable {
    
    /**
     * If the border is to be drawn
     */
    protected boolean isBorderShown;
    
    /**
     * The {@code Properties} Object for subclasses to use; can be changed 
     * to a subclass of {@code Properties} to modify existing or creating 
     * custom properties
     */
    protected Properties properties = Properties.getProperties();
    
    /**
     * Stores the default values that this class uses
     */
    protected static class Properties {
        
        /**
         * Indicates that the label should be left justified.
         */
        public static final int LEFT        = 0;

        /**
         * Indicates that the label should be centered.
         */
        public static final int CENTER      = 1;

        /**
         * Indicates that the label should be right justified.
         * @since   JDK1.0t.
         */
        public static final int RIGHT       = 2;
        
        /**
         * The default alignment
         */
        public static final int DEFAULT_ALIGNMENT = LEFT;
        
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
         * Returns the left alignment
         * 
         * @return the left alignment
         */
        public int getLeftAlignment() {
            return LEFT;
        }
        
        /**
         * Returns the right alignment
         * 
         * @return the right alignment
         */
        public int getRightAlignment() {
            return RIGHT;
        }
        
        /**
         * Returns the center alignment
         * 
         * @return the center alignment
         */
        public int getCenterAlignment() {
            return CENTER;
        }
        
    }
    
    
    
    
    /**
     * Constructs an empty label.
     * The text of the label is the empty string <code>""</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public AWTLabel() throws HeadlessException {
        this("", Properties.DEFAULT_ALIGNMENT);
    }
    
    /**
     * Constructs a new label with the specified string of text,
     * left justified.
     * @param text the string that the label presents.
     *        A <code>null</code> value
     *        will be accepted without causing a NullPointerException
     *        to be thrown.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public AWTLabel(String text) throws HeadlessException {
        this(text, Properties.DEFAULT_ALIGNMENT);
    }
    
    /**
     * Constructs a new label that presents the specified string of
     * text with the specified alignment.
     * Possible values for <code>alignment</code> are {@code Properties.LEFT}, 
     * <code>Properties.RIGHT</code>, and <code>Properties.CENTER</code>.
     * @param text the string that the label presents.
     *        A <code>null</code> value
     *        will be accepted without causing a NullPointerException
     *        to be thrown.
     * @param     alignment   the alignment value.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public AWTLabel(String text, int alignment) throws HeadlessException {
        super(text, alignment);
    }
    
    
    
    
    /**
     * Returns {@code true} if the text is overflowing the label's bounds. 
     * Returns {@code false} if the text is not overflowing or the component is
     * not being displayed
     * 
     * @return if the text is overflowing the bounds of the label
     * 
     * @see AWTLabel#getTextSize() getTextSize()
     */
    public boolean isOverflowing() {
        Dimension d = this.getTextSize();
        
        return d == null ? false : d.width >= (getWidth() - 1);
    }
    
    
    
    
    /**
     * Returns the dimensions of the text on the screen as a {@code Dimension} 
     * or {@code null} if the component is not being displayed (i.e. either or 
     * both of the {@code Font} or {@code Graphics} are {@code null})
     * 
     * @return 
     */
    public Dimension getTextSize() {
        Font font = this.getFont();
        Graphics g = this.getGraphics();
        if (font == null || g == null)
            return null;
        FontMetrics metrics = g.getFontMetrics(font);
        int height = metrics.getHeight();
        int width = metrics.stringWidth(this.getText());
        return new Dimension(width, height);
    }
    
    
    
    
    /**
     * Returns {@code true} if the border is to be drawn
     * 
     * @return {@code true} if the border is to be drawn
     */
    public boolean isBorderShown() {
        return this.isBorderShown;
    }
    
    /**
     * Sets the visibility of the border.
     * <p>
     * <b>NOTE:</b>
     * <br>
     * It is best to rid of the {@code LayoutManager} of the parent or to use
     * one that does not interfere with the label's size
     * 
     * @param aFlag if the border is to be drawn
     */
    public void setBorderShown(boolean aFlag) {
        this.isBorderShown = aFlag;
        revalidate();
    }
    
    
    
    
    /**
     * {@inheritDoc}
     */
    public void paint(Graphics g) {
        super.paint(g);
        this.paintBorder(g);
    }
    
    /**
     * {@inheritDoc}
     */
    public void paintBorder(Graphics g) {
        if (!isBorderShown)
            return;
        Color c = this.getForeground();
        if (c == null)
            return;
        Graphics g2 = g.create();
        g2.setColor(Color.BLACK);
        g2.drawRect(0, 0, this.getWidth() - 1 , this.getHeight() - 1);
    }
    
}
