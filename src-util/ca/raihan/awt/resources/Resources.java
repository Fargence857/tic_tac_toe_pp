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

package ca.raihan.awt.resources;

import java.io.IOException;
import java.io.InputStream;

import java.awt.Image;

import javax.imageio.ImageIO;

/**
 * A collection of resources that is for use by all classes
 * 
 * @author Pranjal Raihan
 */
public class Resources {
    
    private static Image WARNING_ICON;
    
    static {
        InputStream in = null;
        try {
            in = Resources.class.getResourceAsStream("img/warning.png");
            WARNING_ICON = ImageIO.read(in);
        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            if (in != null)
            try {
                in.close();
            } catch (IOException ex) {}
        }
    }
    
    /**
     * Disables instantiation directly and by reflection
     * 
     * @throws IllegalAccessException 
     */
    private Resources() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
    
    /**
     * Returns the image of a warning icon
     * 
     * @return the image of a warning icon
     */
    public static Image getWarningIcon() {
        return WARNING_ICON;
    }
    
}
