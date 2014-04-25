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

package ca.raihan.awt.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * A collection of utility methods for {@code Image}s
 * @author Pranjal Raihan
 */
public final class ImageUtils {
    
    /**
     * disable external initialization and via reflection
     */
    private ImageUtils()
    {
        throw new IllegalAccessError(getClass().getName());
    }
    
    
    
    
    /**
     * Scales the specified image to the desired dimensions by specified type
     * and the smooth scaling method
     * 
     * @param originalImage the image to resize
     * @param newWidth the new imageWidth of the image
     * @param newHeight the new imageHeight of the image
     * @param imageType the type of {@code BufferedImage}
     * 
     * @return the scaled image
     */
    public static Image getResizedImage(Image originalImage, int newWidth, 
            int newHeight, int imageType) {
        return getResizedImage(originalImage, newWidth, newHeight, imageType, 
                Image.SCALE_SMOOTH);
    }
    
    /**
     * Scales the specified image to the desired dimensions by specified type
     * and the specified scaling method
     * 
     * @param originalImage the image to resize
     * @param newWidth the new imageWidth of the image
     * @param newHeight the new imageHeight of the image
     * @param imageType the type of {@code BufferedImage}
     * @param scaleType the method of scaling to use under {@code Image}
     * 
     * @return the scaled image
     */
    public static Image getResizedImage(Image originalImage, int newWidth, 
        int newHeight, int imageType, int scaleType) {          
        if (originalImage == null)
            return null;
        Image temp = 
                originalImage.getScaledInstance(newWidth, newHeight, 
                Image.SCALE_SMOOTH);
        BufferedImage resizedImage = 
                new BufferedImage(
                newWidth, newHeight, imageType);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(temp, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;  
    }

}
