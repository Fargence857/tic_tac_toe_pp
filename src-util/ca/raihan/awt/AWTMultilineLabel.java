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
import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.List;


/**
 * A multi-line label that is a subclass of an AWT {@code Canvas}, it is based 
 * on {@code VWrappingLabel} which itself is an extension of the old Symantec's 
 * class {@code WrappingLabel}.
 * 
 * @author Pranjal Raihan
 */
public class AWTMultilineLabel extends Canvas implements Borderable {
    
    /**
     * If the border is to be drawn
     */
    protected boolean isBorderShown;
    
    /**
     * The vertical alignment
     */
    protected float verticalAlignment;
    
    /**
     * The horizontal alignment
     */
    protected float horizontalAlignment;
    
    /**
     * The text of the label
     */
    protected String text;
    
    /**
     * The {@literal FontMetrics} to fetch String width and height
     */
    protected FontMetrics fontMetrics;
    
    /**
     * The baseline of the text
     */
    protected int baseline;
    
    /**
     * The properties
     */
    protected Properties properties = Properties.getInstance();
    
    
    
    
    /**
     * Stores the defaults that this class uses
     */
    public static class Properties {
        
        /**
         * @see java.awt.Canvas#BOTTOM_ALIGNMENT
         */
        private static final float BOTTOM_ALIGNMENT = Canvas.BOTTOM_ALIGNMENT;
        
        /**
         * @see java.awt.Canvas#TOP_ALIGNMENT
         */
        private static final float TOP_ALIGNMENT = Canvas.TOP_ALIGNMENT;
        
        /**
         * @see java.awt.Canvas#CENTER_ALIGNMENT
         */
        private static final float CENTER_ALIGNMENT = Canvas.CENTER_ALIGNMENT;
        
        /**
         * @see java.awt.Canvas#LEFT_ALIGNMENT
         */
        private static final float LEFT_ALIGNMENT = Canvas.LEFT_ALIGNMENT;
        
        /**
         * @see java.awt.Canvas#RIGHT_ALIGNMENT
         */
        private static final float RIGHT_ALIGNMENT = Canvas.RIGHT_ALIGNMENT;
        
        /**
         * Singleton instance
         */
        private static final Properties INSTANCE = new Properties();
        
        
        
        
        /**
         * Private constructor
         */
        protected Properties() {}
        
        /**
         * Returns the singleton {@literal ClassInstancePropertyList} Object
         * 
         * @return the singleton {@literal ClassInstancePropertyList} Object
         */
        public static Properties getInstance() {
            return INSTANCE;
        }
        
        
        
        
        /**
         * Returns the bottom alignment
         * 
         * @return the bottom alignment
         */
        public float getBottomAlignment() {
            return BOTTOM_ALIGNMENT;
        }
        
        /**
         * Returns the top alignment
         * 
         * @return the top alignment
         */
        public float getTopAlignment() {
            return TOP_ALIGNMENT;
        }
        
        /**
         * Returns the center alignment
         * 
         * @return the center alignment
         */
        public float getCenterAlignment() {
            return CENTER_ALIGNMENT;
        }
        
        /**
         * Returns the left alignment
         * 
         * @return the lefy alignment
         */
        public float getLeftAlignment() {
            return LEFT_ALIGNMENT;
        }
        
        /**
         * Returns the right alignment
         * 
         * @return the right alignment
         */
        public float getRightAlignment() {
            return RIGHT_ALIGNMENT;
        }
        
    }
    
    
    
    
    /**
     * Makes a new label with no text and centered alignment
     */
    public AWTMultilineLabel() {
        this("");
    }
    
    /**
     * Makes a new label with the specified text
     * and centered alignment
     * 
     * @param text the text to set
     */
    public AWTMultilineLabel(String text) {
        this(text, Properties.LEFT_ALIGNMENT, Properties.CENTER_ALIGNMENT);
    }
    
    /**
     * Makes a new label with the specified text and alignment
     * 
     * @param text the text to set
     * @param horizontalAlignment the horizontal alignment
     * @param verticalAlignment the vertical alignment
     * 
     * @see java.awt.Canvas#BOTTOM_ALIGNMENT
     * @see java.awt.Canvas#CENTER_ALIGNMENT
     * @see java.awt.Canvas#LEFT_ALIGNMENT
     * @see java.awt.Canvas#RIGHT_ALIGNMENT
     * @see java.awt.Canvas#TOP_ALIGNMENT
     */
    public AWTMultilineLabel(String text, float horizontalAlignment, 
            float verticalAlignment) {
        this.text = text;
        this.horizontalAlignment = horizontalAlignment;
        this.verticalAlignment = verticalAlignment;
    }
    
    
    
    
    /**
     * Returns {@literal true} if the border is to be drawn
     * 
     * @return {@literal true} if the border is to be drawn
     */
    public boolean isBorderShown() {
        return this.isBorderShown;
    }
    
    /**
     * Sets the visibility of the border.
     * <p>
     * <b>NOTE:</b>
     * <br>
     * It is best to rid of the {@literal LayoutManager} of the parent or to use
     * one that does not interfere with the label's size
     * 
     * @param aFlag if the border is to be drawn
     */
    public void setBorderShown(boolean aFlag) {
        this.isBorderShown = aFlag;
        revalidate();
    }
    
    
    
    
    /**
     * Returns the vertical alignment
     * 
     * @see java.awt.Canvas#BOTTOM_ALIGNMENT
     * @see java.awt.Canvas#TOP_ALIGNMENT
     * @see #getClassInstanceProperties()
     * 
     * @return the vertical alignment
     */
    public float getVerticalAlignment() {
        return this.verticalAlignment;
    }
    
    /**
     * Sets the vertical alignment style
     * 
     * @param alignment the alignment
     * 
     * @see java.awt.Canvas#BOTTOM_ALIGNMENT
     * @see java.awt.Canvas#TOP_ALIGNMENT
     * @see #getClassInstanceProperties()
     * 
     */
    public void setVerticalAlignment(float alignment) {
        this.verticalAlignment = alignment;
        invalidate();
    }
    
    /**
     * Returns the horizontal alignment
     * 
     * @see java.awt.Canvas#CENTER_ALIGNMENT
     * @see java.awt.Canvas#LEFT_ALIGNMENT
     * @see java.awt.Canvas#RIGHT_ALIGNMENT
     * @see #getClassInstanceProperties()
     * 
     * @return the horizontal alignment
     */
    public float getHorizontalAlignment() {
        return this.horizontalAlignment;
    }
    
    /**
     * Sets the horizontal alignment style
     * 
     * @param alignment the alignment
     * 
     * @see java.awt.Canvas#BOTTOM_ALIGNMENT
     * @see java.awt.Canvas#CENTER_ALIGNMENT
     * @see java.awt.Canvas#LEFT_ALIGNMENT
     * @see java.awt.Canvas#RIGHT_ALIGNMENT
     * @see java.awt.Canvas#TOP_ALIGNMENT
     * @see #getClassInstanceProperties()
     * 
     */
    public void setHorizontalAlignment(float alignment) {
        this.horizontalAlignment = alignment;
        invalidate();
    }
    
    
    
    
    /**
     * Returns the text of the label
     * 
     * @return the text of the label
     */
    public String getText() {
        return text;
    }
    
    /**
     * Sets the text of the label to the one specified
     * 
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
        repaint();
    }
    
    
    
    
    /**
     * {@inheritDoc}
     * 
     * @return a string representation of this component's state
     */
    @Override
    public synchronized String paramString() {
        return "";
    }
    
    
    
    
    /**
     * {@inheritDoc}
     * 
     * @param g the {@literal Graphics} context
     */
    @Override
    public void paint(Graphics g) {
        if (text != null && !text.equals("")) {
            Dimension d = getSize();
            int currentY = 0;
            List<String> lines;
            
            fontMetrics = getFontMetrics(getFont());
            baseline = fontMetrics.getMaxAscent();
            
            lines = toLines(d.width);
            
            if (getVerticalAlignment() == properties.getCenterAlignment()) {
                int center = (d.height / 2);
                currentY = center - ((lines.size() / 2) * 
                        fontMetrics.getHeight());
            } else if (
                    getVerticalAlignment() == 
                    properties.getBottomAlignment()) {
                currentY = d.height - (lines.size() * fontMetrics.getHeight());
            }
            
            for (String string : lines) {
                this.drawAlignedString(g, string, 1, currentY, d.width - 1);
                currentY += fontMetrics.getHeight();
            }
        }
        this.paintBorder(g);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @param g the {@literal Graphics} context
     */
    @Override
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
    
    
    
    
    /**
     * Draws a {@literal String} with the proper alignment
     * 
     * @param g the {@literal Graphics} context to draw in
     * @param s the {@literal String} to draw
     * @param printX the {@literal x} position to draw in
     * @param printY the {@literal y} position to draw in
     * @param width the width of the overflow limit
     */
    protected void drawAlignedString(Graphics g, String s, int printX, 
            int printY, int width) {
        int x = printX;
        int y = printY + baseline;
        
        float h = getHorizontalAlignment();
        
        if (h == properties.getCenterAlignment())
            x += (width - fontMetrics.stringWidth(s)) / 2;
        else if (h == properties.getRightAlignment())
            x += width - fontMetrics.stringWidth(s);
        
        g.drawString(s, x, y);
    }
    
    /**
     * Breaks up a continuous String into smaller lines of String.
     * 
     * 
     * @param width the width of the overflow limit
     * 
     * @return a {@literal List} of the broken lines
     */
    protected List<String> toLines(int width) {
        if (text == null || fontMetrics == null)
            return null;
        
        List<String> rv = new ArrayList<>();
        int fromIndex = 0;
        int position;
        int bestPosition;
        String largest = null;
        
        while (fromIndex != -1) {
            
            while (fromIndex < text.length() 
                   && text.charAt(fromIndex) == ' ')
                fromIndex++;
            
            position = fromIndex;
            bestPosition = -1;
            
            while (position >= fromIndex) {
                boolean newLine = false;
                
                int newlinePosition = text.indexOf('\n', position);
                int spacePosition = text.indexOf(' ', position);
                
                if (newlinePosition != -1 &&
                     ((spacePosition == -1) || (spacePosition != -1 && 
                        newlinePosition < spacePosition))) {
                    position = newlinePosition;
                    newLine = true;
                } else
                    position = spacePosition;
                
                String s = position == -1 ? 
                        text.substring(fromIndex) : 
                        text.substring(fromIndex, position);
                
                if (fontMetrics.stringWidth(s) < width) {
                    largest = s;
                    bestPosition = position;
                    
                    if (newLine)
                        bestPosition++;
                    if (position == -1 || newLine)
                        break;
                } else
                    break;
                
                position++;
            }
            
            if (largest == null) {
                
                int totalWidth = 0;
                int oneCharWidth;
                
                position = fromIndex;
                
                int len = text.length();
                while (position < len) {
                    oneCharWidth = fontMetrics.charWidth(text.charAt(position));
                    if ((totalWidth + oneCharWidth) >= width)
                        break;
                    totalWidth += oneCharWidth;
                    position++;
                }
                rv.add(text.substring(fromIndex, position));
                fromIndex = position;
                
            } else {
                rv.add(largest);
                fromIndex = bestPosition;
            }
            
        }
        
        return rv;
    }
    
}