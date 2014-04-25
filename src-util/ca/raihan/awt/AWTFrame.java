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

import java.awt.Frame;
import java.awt.GraphicsConfiguration;

import java.awt.event.WindowAdapter;

/**
 * A simple extension of the default AWT {@code Frame}
 * 
 * @see Frame
 * 
 * @author Pranjal Raihan
 */
public class AWTFrame extends Frame implements ListenableWindow {
    
    /**
     * The default {@code WindowActionManager}
     */
    private WindowActionManager closeOperation = WindowCloseOperation.CLOSE;
    
    /**
     * The default {@code WindowActionManager}'s instance
     */
    private WindowAdapter actionHandler;
    
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
         * The default width
         */
        private static final int WIDTH = 400;
        
        /**
         * The default height
         */
        private static final int HEIGHT = 400;
        
        /**
         * Singleton instance
         */
        private static final Properties properties = new Properties();
        
        
        
        
        /**
         * Private constructor
         */
        private Properties() {
        }
        
        /**
         * Returns the singleton {@code ClassProperties} Object
         * 
         * @return the singleton {@code ClassProperties} Object
         */
        public static Properties getProperties() {
            return properties;
        }
        
        
        
        
        /**
         * Returns the default width
         * 
         * @return the default width
         */
        public int getWidth() {
            return WIDTH;
        }
        
        /**
         * Returns the default height
         * 
         * @return the default height
         */
        public int getHeight() {
            return HEIGHT;
        }
        
    }
    
    
    
    
    /**
     * Makes a new frame with the name of the class
     */
    public AWTFrame() {
        super();
        this.setTitle(getClass().getSimpleName());
        this.initialize();
    }
    
    /**
     * Makes a new frame with the specified title
     * 
     * @param title the title of the frame
     */
    public AWTFrame(String title) {
        super(title);
        initialize();
    }
    
    /**
     * Makes a new frame with the specified {@code GraphicsConfiguration}
     * 
     * @param gc the {@code GraphicsConfiguration} to control the graphics
     */
    public AWTFrame(GraphicsConfiguration gc) {
        super(gc);
        initialize();
    }
    
    /**
     * Makes a new frame with the specified title and 
     * {@code GraphicsConfiguration}
     * 
     * @param title the title of the frame
     * @param gc the {@code GraphicsConfiguration} to control the graphics
     */
    public AWTFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
        initialize();
    }
    
    /**
     * Initializer method instead of block
     */
    private void initialize() {
        this.setLayout(null);
        this.setSize(properties.getWidth(), properties.getHeight());
        this.addWindowListener(
                actionHandler = closeOperation.getNewHandler());
    }
    
    
    
    
    /**
     * Configures the frame to use the specified {@code WindowActionManager}'s
     * policy of how a frame is to behave
     * 
     * @param operation The {@WindowActionManager} that controls how the frame
     * behaves
     * 
     * @see WindowCloseOperation
     */
    public void addWindowListener(WindowActionManager operation) {
        if (closeOperation == operation)
            return;
        this.removeWindowListener(actionHandler);
        this.closeOperation = operation;
        this.addWindowListener(
                actionHandler = operation.getNewHandler());
    }
    
}
