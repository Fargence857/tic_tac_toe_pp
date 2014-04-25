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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;

import java.awt.Color;
import java.awt.image.BufferedImage;

import ca.raihan.awt.image.ImageUtils;

/**
 * A PictureBox that conceals a picture which is drawn on the component itself. 
 * The paint policy is configurable
 * 
 * @author Pranjal Raihan
 */
public class AWTPictureBox extends Component implements Borderable {
    
    /**
     * The image instance stored for painting
     */
    protected Image image;
    
    /**
     * the id of the image for the {@code MediaTracker}
     */
    protected int id;
    
    /**
     * The mediatracker to 
     */
    protected MediaTracker tracker = new MediaTracker(this);
    
    /**
     * An implementation of a paint manager that specifies how this PictureBox
     * paints
     * @see ImagePaintManager
     * @see ImagePaintPolicy
     */
    protected ImagePaintManager paintManager;
    
    /**
     * The painter instance fetched from the paintManager
     * NOTE: It is recommended that the painter not be tampered with as it is
     * specified by the paint manager
     */
    protected ImagePainter painter;
    
    /**
     * if the border is to be painted upon calling paint
     */
    protected boolean isBorderShown;
    
    /**
     * The explicitly stored component width which can be independent from
     * the width in the {@code Component} class to allow certain applications
     */
    int componentWidth;
    
    /**
     * The explicitly stored component height which can be independent from
     * the height in the {@code Component} class to allow certain applications
     */
    int componentHeight;
    
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
         * The default width of a PictureBox
         */
        private static final int WIDTH = 100;
        
        /**
         * The default height of a PictureBox
         */
        private static final int HEIGHT = 100;
        
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
         * Returns the width
         * 
         * @return the width
         */
        public int getWidth() {
            return WIDTH;
        }
        
        /**
         * Returns the height
         * 
         * @return the height
         */
        public int getHeight() {
            return HEIGHT;
        }
        
    }
    
    
    
    
    {
        paintManager = ImagePaintPolicy.FULL;
        painter = paintManager.getNewPainter();
    }
    
    /**
     * Makes a new PictureBox with the default dimensions
     */
    public AWTPictureBox() {
        this(Properties.WIDTH, Properties.HEIGHT);
    }
    
    /**
     * Makes a new PictureBox with the default dimensions and the specified
     * image
     * 
     * @param img the {@code Image} to be used by the PictureBox
     */
    public AWTPictureBox(Image img) {
        this(img, 100, 100);
    }
    
    /**
     * Makes a new PictureBox with the specified dimensions
     * 
     * @param width the width of the component
     * @param height the height of the component
     */
    public AWTPictureBox(int width, int height) {
        this(null, width, height);
    }
    
    /**
     * Makes a new PictureBox with the specified dimensions and image
     * 
     * @param img the {@code Image} to be used by the PictureBox
     * @param width the width of the component
     * @param height the height of the component
     */
    public AWTPictureBox(Image img, int width, int height) {
        if (width < 0 || height < 0)
            throw new IllegalArgumentException("width (" + width + ") and "
                    + "height (" + height + ") must be >= 0");
        this.image = img;
        componentWidth = width;
        componentHeight = height;
        super.setSize(componentWidth, componentHeight);
    }
    
    
    
    
    /**
     * Returns the {@code Image} that this PictureBox is to draw
     * 
     * @return the {@code Image} that this PictureBox is to draw
     */
    public Image getImage() {
        return image;
    }
    
    /**
     * Sets the image of the component
     * 
     * @param img the new {@code Image}
     */
    public void setImage(Image img) {
        this.image = img;
        if (image == null)
            return;
        if (!loadImage(img))
            image = null;
        repaint();
    }
    
    /**
     * @param img the {@code Image} to be loaded and tracked by the 
     * {@code MediaTracker}
     * 
     * @return if adding an image to the loading queue was successful
     */
    protected boolean loadImage(Image img) {
        int nextID = getNextID();
        tracker.addImage(img, nextID);
        try {
            tracker.waitForID(nextID);
        } catch (InterruptedException ex) {
            return false;
        }
        tracker.removeImage(img, id);
        return true;
    }
    
    /**
     * Returns and increments the id for the {@code MediaTracker}
     * 
     * @return the next id for the {@code MediaTracker}
     */
    protected int getNextID() {
        return id++;
    }
    
    
    
    
    /**
     * Returns {@code true} if the border is shown
     * 
     * @return {@code true} if the border is shown
     */
    public boolean isBorderShown() {
        return isBorderShown;
    }
    
    /**
     * Sets the border visible or not visible
     * 
     * @param aFlag if the border is to be shown
     */
    public void setBorderShown(boolean aFlag) {
        if (isBorderShown != aFlag) {
            isBorderShown ^= true;
            revalidate();
        }
    }
    
    
    
    
    /**
     * Returns the {@code ImagePaintManager} of this PictureBox
     * 
     * @return the paint manager
     */
    public ImagePaintManager getImagePaintPolicy() {
        return this.paintManager;
    }
    
    /**
     * Assigns a {@code ImagePaintManager} to the PictureBox
     * 
     * @param manager the manager that controls the paint methods
     * 
     * @see ImagePaintPolicy
     */
    public void setImagePaintPolicy(ImagePaintManager manager) {
        paintManager = manager;
        painter = manager.getNewPainter();
    }
    
    
    
    
    /**
     * {@inheritDoc}
     * 
     * @param width 
     * @param height 
     */
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        componentWidth = width;
        componentHeight = height;
    }
    
    /**
     * {@inheritDoc}
     * 
     * @param d 
     */
    @Override
    public void setSize(Dimension d) {
        this.setSize(d.width, d.height);
    }
    
    
    
    
    /**
     * {@inheritDoc}
     * 
     * @param g
     */
    public void paint(Graphics g) {
        if (painter == null)
            return;
        if (!tracker.checkAll())
            return;
        painter.paint(g, this, image);
        paintBorder(g);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @param g 
     */
    public void paintBorder(Graphics g) {
        if (isBorderShown())
            painter.paintBorder(g, this);
    }
    
    
    
    
    /**
     * An interface for custom paint operations on an {@code AWTPictureBox} and 
     * its subclasses
     * 
     * @author Pranjal Raihan
     */
    public static interface ImagePainter extends Cloneable {

        /**
         * Draws an image on the specified PictureBox with a supplied 
         * {@literal Graphics} Object. Returns {@literal true} if the drawing was 
         * successful, else returns {@literal false}.
         * 
         * @param g the {@literal Graphics} context to draw in
         * @param c the PictureBox to paint on
         * @param image the {@literal Image} to paint
         * 
         * @return if the painting was successful
         */
        boolean paint(Graphics g, AWTPictureBox c, Image image);

        /**
         * Draws a border on the specified PictureBox with a a supplied 
         * {@literal Graphics} Object. Returns {@literal true} if the drawing was 
         * successful, else returns {@literal false}.
         * 
         * @param g the {@literal Graphics} context to draw in
         * @param c the PictureBox to paint on
         * 
         * @return if the painting was successful
         */
        boolean paintBorder(Graphics g, AWTPictureBox c);

        /**
         * Returns a fresh clone of the {@literal ImagePainter}
         * 
         * @return a clone of the {@literal ImagePainter}
         */
        ImagePainter clone();

    }
    
    
    /**
     * A convenient adapter for {@code ImagePainter} that does some default 
     * operations.
     * 
     * @author Pranjal Raihan
     */
    public static abstract class ImagePainterAdapter implements 
            ImagePainter {

        /**
         * {@inheritDoc}
         * 
         * @param g {@inheritDoc}
         * @param c {@inheritDoc}
         * @param image {@inheritDoc}
         * 
         * @return if the painting was successful
         */
        @Override
        public boolean paint(Graphics g, AWTPictureBox c, Image image) {
            return false;
        }

        /**
         * {@inheritDoc}
         * 
         * @param g {@inheritDoc}
         * @param c {@inheritDoc}
         * 
         * @return if the painting was successful
         */
        @Override
        public boolean paintBorder(Graphics g, AWTPictureBox c) {
            Graphics g2 = g.create();
            g2.setColor(Color.BLACK);
            g2.drawRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
            return true;
        }
        
        
        
        
        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("CloneDeclaresCloneNotSupported")
        public ImagePainterAdapter clone() {
            try {
                return (ImagePainterAdapter) super.clone();
            } catch (CloneNotSupportedException ex) {
                throw new InternalError();
            }
        }
        
    }
    
    
    
    
    /**
     * Encapsulates an {@code ImagePainter} of which a clone can be fetched. This 
     * interface can be used to make custom paint managers for classes extending 
     * {@code AWTPictureBox}
     * 
     * @author Pranjal Raihan
     */
    public static interface ImagePaintManager {
        
        /**
         * Returns a fresh copy of the {@literal ImagePainter}
         * 
         * @return a fresh copy of the {@literal ImagePainter}
         */
        ImagePainter getNewPainter();
        
    }
    
    /**
     * The default {@code ImagePaintManager} implementations that a PictureBox
     * uses to paint images
     */
    public static class ImagePaintPolicy implements ImagePaintManager {
        
        /**
         * Paints over the full extent of the PictureBox, stretches the image 
         * if necessaryPaints over the full extent of the PictureBox, stretches the image 
         * if necessary
         */         
        public static final ImagePaintPolicy STRETCH = new ImagePaintPolicy(
            new ImagePainterAdapter() {
            
            @Override
            public boolean paint(Graphics g, AWTPictureBox c, Image image) {
                if (image == null)
                    return true;
                if (image.getWidth(null) <= 0 || image.getHeight(null) <= 0)
                    return true;
                g.drawImage(
                    ImageUtils.getResizedImage(
                        image, c.componentWidth, c.componentHeight, 
                        BufferedImage.TYPE_INT_ARGB), 
                    0, 0, null);
                return true;
            }
            
            @Override
            public boolean paintBorder(Graphics g, AWTPictureBox c) {
                return paintBorderCommon(g, c);
            }
            
        }, "Stretch");
        
        /**
         * Paints the whole image at the center of the PictureBox, no resizing
         */
        public static final ImagePaintPolicy CENTER = new ImagePaintPolicy(
            new ImagePainterAdapter() {
            
            @Override
            public boolean paint(Graphics g, AWTPictureBox c, Image image) {
                if (image == null)
                    return true;
                double iW = (double)image.getWidth(null);
                double iH = (double)image.getHeight(null);
                if (iH <= 0 || iW <= 0)
                    return true;
                
                int cW = c.getWidth();
                int cH = c.getHeight();
                
                g.drawImage(
                    ImageUtils.getResizedImage(
                        image, (int)iW, (int)iH, BufferedImage.TYPE_INT_ARGB), 
                    (int)(cW / 2 - iW / 2) , (int)(cH / 2 - iH / 2), null);
                return true;
            }
            
            @Override
            public boolean paintBorder(Graphics g, AWTPictureBox c) {
                return paintBorderCommon(g, c);
            }
            
        }, "Center");
        
        /**
         * Resizes the image to just fit into the picturebox
         */
        public static final ImagePaintPolicy FULL = new ImagePaintPolicy(
                new ImagePainterAdapter() {
            
            @Override
            public boolean paint(Graphics g, AWTPictureBox c, Image image) {
                if (image == null)
                    return true;
                
                double iW = (double)image.getWidth(null);
                double iH = (double)image.getHeight(null);
                final int cW = c.getWidth();
                final int cH = c.getHeight();
                if (iH <= 0 || iW <= 0 || cW <= 0 || cH <= 0)
                    return true;
                
                double aspect = iW / iH;
                
                if (iW > cW) {
                    iH -= (iW - cW) / aspect;
                    iW = cW;
                }
                if (iH > cH) {
                    iW -= (iH - cH) * aspect;
                    iH = cH;
                }
                
                g.drawImage(
                    ImageUtils.getResizedImage(
                        image, (int)iW, (int)iH, BufferedImage.TYPE_INT_ARGB), 
                    (int)(cW / 2 - iW / 2) , (int)(cH / 2 - iH / 2), null);
                return true;
            }
            
            @Override
            public boolean paintBorder(Graphics g, AWTPictureBox c) {
                return paintBorderCommon(g, c);
            }
            
        }, "Full");
        
        
        
        
        /**
         * The concealing {@literal ImagePainter} instance
         */
        private final ImagePainter actionHandler;
        
        /**
         * The name of this policy
         */
        private final String name;
        
        
        
        
        /**
         * Makes a new Paint Policy
         * 
         * @param actionHandler the {@code ImagePainter} instance
         * 
         * @param name the name of the policy
         */
        protected ImagePaintPolicy(ImagePainter actionHandler, String name)
        {
            this.actionHandler = actionHandler;
            this.name = name;
        }
        
        
        
        
        /**
         * {@inheritDoc}
         */
        public ImagePainter getNewPainter() {
            return actionHandler.clone();
        }
        
        
        
        
        /**
         * Returns the default {@literal ImagePaintPolicy}
         * 
         * @return the default {@literal ImagePaintPolicy}
         */
        public static ImagePaintPolicy getDefault() {
            return FULL;
        }
        
        
        
        
        /**
         * The common paint border implementation
         * 
         * @param g the {@literal Graphics} context to draw in
         * @param c the PictureBox
         * 
         * @return if the border was successfully painted
         */
        protected static boolean paintBorderCommon(Graphics g, 
                AWTPictureBox c) {
            Color color = c.getForeground();
            if (color == null)
                return false;
            Graphics g2 = g.create();
            g2.setColor(color);
            g2.drawRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
            return true;
        }
        
        
        
        /**
         * {@inheritDoc}
         * 
         * @return the {@literal String} representation of this {@literal Object}
         */
        @Override
        public String toString() {
            return name;
        }
        
    }
    
}
