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

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A dialog box for showing information as well as click-able options
 * 
 * @author Pranjal Raihan
 */
public class AWTDialog extends Dialog implements ListenableWindow {
    
    /**
     * The text component (contains all the text)
     */
    private AWTMultilineLabel text = new AWTMultilineLabel();
    
    /**
     * The {@code WindowActionManager} instance that controls the default
     * set of actions taken when the dialog is to be closed
     */
    private WindowActionManager closeOperation = WindowCloseOperation.CLOSE;
    
    /**
     * The control buttons added to this dialog
     */
    private List<Button> buttons = new ArrayList<>(3);
    
    /**
     * The listener of the dialog itself when and if the one specified by the
     * {@code WindowCloseOperation} is {@code null}
     */
    private MouseListener internalListener = new MouseAdapter()
    {
        public void mouseClicked(MouseEvent e)
        {
            if (!e.getComponent().isEnabled())
                return;
            AWTDialog.this.setVisible(true);
            AWTDialog.this.dispose();
        }
    };
    
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
         * The default width
         */
        private static final int  WIDTH = 249;
        
        /**
         * The default height
         */
        private static final int  HEIGHT = 129;
        
        /**
         * The default gap between buttons
         */
        private static final int  BUTTONS_GAP = 14;
        
        /**
         * The default button width
         */
        private static final int  BUTTON_WIDTH = 59;
        
        /**
         * The default button height
         */
        private static final int  BUTTON_HEIGHT = 23;
        
        /**
         * The default y offset, {@code BUTTON_HEIGHT + BUTTONS_GAP}
         */
        private static final int  BUTTON_Y_OFFSET = 
                BUTTON_HEIGHT + BUTTONS_GAP;
        
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
        
        /**
         * Returns the default gap between buttons
         * 
         * @return the default gap between buttons
         */
        public int getButtonsGap() {
            return BUTTONS_GAP;
        }
        
        /**
         * Returns the default button width
         * 
         * @return the default button width
         */
        public int getButtonWidth() {
            return BUTTON_WIDTH;
        }
        
        /**
         * Returns the default button height
         * 
         * @return the default button height
         */
        public int getButtonHeight() {
            return BUTTON_HEIGHT;
        }
        
        /**
         * Returns the offset in y between the bottom of the dialog to the top
         * of the buttons
         * 
         * @return the offset in y between the bottom of the dialog to the top
         * of the buttons
         */
        public int getButtonYOffset() {
            return BUTTON_Y_OFFSET;
        }
        
    }
    
    
    
    
    /**
     * Makes a dialog inside an existing one
     * 
     * @param owner the owner dialog
     */
    public AWTDialog(Dialog owner) {
        super(owner);
        initialize();
    }
    
    /**
     * Makes a dialog inside a Frame
     * 
     * @param owner the owner frame
     */
    public AWTDialog(Frame owner) {
        super(owner);
        initialize();
    }
    
    /**
     * Makes a dialog inside a Window
     * 
     * @param owner the owner window
     */
    public AWTDialog(Window owner) {
        super(owner);
        initialize();
    }
    
    /**
     * Makes a dialog inside an existing one with the specified title
     * 
     * @param owner the owner dialog
     * @param title the title of the dialog
     */
    public AWTDialog(Dialog owner, String title) {
        super(owner, title);
        initialize();
    }
    
    /**
     * Makes a dialog inside a Frame with the specified title
     * 
     * @param owner the owner frame
     * @param title the title of the dialog
     */
    public AWTDialog(Frame owner, String title) {
        super(owner, title);
        initialize();
    }
    
    /**
     * Makes a dialog inside a window with the specified title
     * 
     * @param owner the owner window
     * @param title the title of the dialog
     */
    public AWTDialog(Window owner, String title) {
        super(owner, title);
        initialize();
    }
    
    /**
     * Makes a dialog inside a frame with the specified modality as 
     * {@code boolean}
     * 
     * @param owner the owner frame
     * @param modal if {@code true}, it blocks access to other application forms
     */
    public AWTDialog(Frame owner, boolean modal) {
        super(owner, modal);
        initialize();
    }
    
    /**
     * Makes a dialog inside a frame with the specified title and 
     * modality as {@code boolean}
     * 
     * @param owner the owner frame
     * @param title the title of the dialog
     * @param modal if {@code true}, it blocks access to other application forms
     */
    public AWTDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        initialize();
    }
    
    /**
     * Makes a dialog inside an existing one with the specified title and 
     * modality as {@code boolean}
     * 
     * @param owner the owner dialog
     * @param title the title of the dialog
     * @param modal if {@code true}, it blocks access to other application forms
     */
    public AWTDialog(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
        initialize();
    }
    
    /**
     * Makes a dialog inside a window with the specified modality
     * 
     * @param owner the owner window
     * @param modalityType the {@code ModalityType} which determines how the 
     * dialog behaves relative to other windows
     */ 
    public AWTDialog(Window owner, ModalityType modalityType) {
        super(owner, modalityType);
        initialize();
    }
    
    /**
     * Makes a dialog inside a window with the specified title and modality
     * 
     * @param owner the owner window
     * @param title the title of the dialog
     * @param modalityType the {@code ModalityType} which determines how the 
     * dialog behaves relative to other windows
     */
    public AWTDialog(Window owner, String title, ModalityType modalityType) {
        super(owner, title, modalityType);
        initialize();
    }
    
    /**
     * Makes a new dialog in an existing one with the specified title, modality 
     * as {@code boolean} and {@code GraphicsConfiguration}
     * 
     * @param owner the owner dialog
     * @param title the title of the dialog
     * @param modal if {@code true}, it blocks access to other application forms
     * @param gc the {@code GraphicsConfiguration} to use
     */
    public AWTDialog(Dialog owner, String title, boolean modal, 
            GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
        initialize();
    }
    
    /**
     * Makes a new dialog in a frame with the specified title, modality 
     * as {@code boolean} and {@code GraphicsConfiguration}
     * 
     * @param owner the owner frame
     * @param title the title of the dialog
     * @param modal if {@code true}, it blocks access to other application forms
     * @param gc the {@code GraphicsConfiguration} to use
     */
    public AWTDialog(Frame owner, String title, boolean modal, 
            GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
        initialize();
    }
    
    /**
     * Makes a new dialog in a window with the specified title, modality 
     * as {@code boolean} and {@code GraphicsConfiguration}
     * 
     * @param owner the owner window
     * @param title the title of the dialog
     * @param modalityType the {@code ModalityType} which determines how the 
     * dialog behaves relative to other windows
     * @param gc the {@code GraphicsConfiguration} to use
     */
    public AWTDialog(Window owner, String title, ModalityType modalityType,
                  GraphicsConfiguration gc) {
        super(owner, title, modalityType, gc);
        initialize();
    }
    
    /**
     * Common to constructors and needs to be called after the constructor and 
     * so cannot be put in an initializer block
     */
    private void initialize() {
        this.setResizable(false);
        this.setSize(properties.getWidth(), properties.getHeight());
        this.addWindowListener(closeOperation.getNewHandler());
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.add(text);
    }
    
    
    
    
    /**
     * Sets the readable text of the dialog
     * 
     * @param text the new text
     */
    public void setText(String text) {
        this.text.setText(text);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        super.setSize(width, height);
        updateLabel();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @param d 
     */
    public void setSize(Dimension d) {
        this.setSize(d.width, d.height);
    }
    
    
    
    
    /**
     * This dialog cannot be specified a close operation
     * 
     * @param closeOperation the {@code WindowActionManager}
     * 
     * @deprecated 
     */
    @Deprecated
    public void addWindowListener(WindowActionManager closeOperation) {
        throw new UnsupportedOperationException(
                "A " + this.getClass().getSimpleName() + 
                " cannot be specified a close operation");
    }
    
    
    
    
    /**
     * Adds a button to the button group at the bottom of the dialog
     * 
     * @param button the {@code Button} to add
     */
    public void addButton(Button button) {
        if (button == null || buttons.contains(button))
            return;
        int bWidth = properties.getButtonWidth();
        int bGap = properties.getButtonsGap();
        button.setSize(bWidth, properties.getButtonHeight());
        int y = this.getHeight() - properties.getButtonYOffset();
        int buttonsNumber = buttons.size();
        int center = (this.getWidth() - 1) / 2;
        int totalWidth = ((buttonsNumber + 1) * bWidth) + 
                (buttonsNumber - 1) * bGap;
        int x = center - (totalWidth / 2);
        
        for (int i = 0; i < buttonsNumber; ++i) {
            Button b = buttons.get(i);
            b.setLocation(x, y);
            x += bWidth + bGap;
        }
        
        button.setLocation(x, y);
        this.add(button);
        buttons.add(button);
        button.addMouseListener(internalListener);
    }
    
    /**
     * Adds a listener to each explicitly added button in this dialog
     * 
     * @param listener the listener to add
     */
    public void addButtonsListener(MouseListener listener) {
        for (Button button : buttons)
            button.addMouseListener(listener);
    }
    
    
    
    
    /**
     * Updates the bounds of the text component
     */
    private void updateLabel() {
        int bGap = properties.getButtonsGap();
        text.setBounds(2 * bGap, bGap + 20,
                this.getWidth() - 1 - (4 * bGap), 
                this.getHeight() - 1 - 
                (bGap + properties.getButtonYOffset()) - 20);
        text.revalidate();
    }
    
}
