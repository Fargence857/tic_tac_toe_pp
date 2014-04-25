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

package ca.raihan.tictactoe.game;

import java.awt.Color;
import java.awt.Panel;
import java.awt.Image;

import ca.raihan.awt.AWTPictureBox;
import ca.raihan.util.Contract;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 *
 * @author Pranjal Raihan
 */
class ImagePanel extends Panel {
    
    private final AWTPictureBox pictureBox;
    
    private boolean isFiltered;
    
    private Color filterColor = null; // new Color(180, 180, 180, 90);
    
    
    
    
    protected ImagePanel() {
        pictureBox = new AWTPictureBox(getWidth(), getHeight());
        //add(pictureBox);
    }
    
    protected ImagePanel(Image image) {
        pictureBox = new AWTPictureBox(image, getWidth(), getHeight());
        //add(pictureBox);
    }
    
    
    
    
    /**
     * @return the current image on this panel
     */
    public Image getImage() {
        return pictureBox.getImage();
    }
    
    /**
     * Sets the current image of this panel to the specified {@code Image}.
     * 
     * @param img the new image to set
     */
    public void setImage(Image img) {
        pictureBox.setImage(img);
    }
    
    /**
     * Sets the current image of this panel to the specified image
     * 
     * @param img the new image to set
     * @param setVisible also set image visible?
     */
    public void setImage(Image img, boolean setVisible) {
        setImage(img);
        setImageVisible(setVisible);
    }
    
    
    
    
    /**
     * Returns {@code true} if the image on this panel is currently visible, 
     * {@code false} otherwise.
     * 
     * @return if the image on this panel is currently visible
     */
    public boolean isImageVisible() {
        return pictureBox.isVisible();
    }
    
    /**
     * Sets the visibility of the image on this panel
     * 
     * @param aFlag the visibility of this panel
     */
    public void setImageVisible(boolean aFlag) {
        this.pictureBox.setVisible(aFlag);
    }
    
    
    
    
    public boolean isFiltered() {
        return isFiltered;
    }
    
    public void setFiltered(boolean flag) {
        this.isFiltered = flag;
        repaint();
    }
    
    public Color getFilterColor() {
        return filterColor;
    }
    
    public void setFilterColor(Color color) {
        this.filterColor = color;
    }
    
    
    
    
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        pictureBox.setSize(width, height);
    }
    
    @Override
    public void setSize(Dimension dimension) {
        Contract.nonNull(dimension);
        this.setSize(dimension.width, dimension.height);
    }
    
    
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        pictureBox.paint(g);
        if (isFiltered()) {
            Color c = getFilterColor();
            if (c == null)
                return;
            g.setColor(c);
            int w = getWidth() - 2;
            int h = getHeight() - 2;
            if (w > 0 && h > 0) {
                g.fillRect(1, 1, w, h);
            }
        }
    }
    
}
