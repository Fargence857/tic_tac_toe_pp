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
import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

import ca.raihan.awt.resources.Resources;

/**
 * The default {@WindowActionManager}s
 * 
 * @see WindowActionManager
 * 
 * @author Pranjal Raihan
 */
public enum WindowCloseOperation implements WindowActionManager {
    
    /**
     * Closes the window without warning on close
     */
    CLOSE(new AbstractCloseWindowAdapter() {
        public void windowClosing(WindowEvent e)
        {
            final Component window = e.getComponent();
            window.setVisible(false);
            if (window instanceof Window)
                ((Window)window).dispose();
        }

        public void windowClosed(WindowEvent e)
        {}
    }),
    
    /**
     * Shows a dialog that asks the user to confirm quit, and closes based on 
     * their input
     */
    SHOW_CONFIRM(new AbstractCloseWindowAdapter() {
        public void windowClosing(WindowEvent e)
        {
            final Component window = e.getComponent();
            AWTDialog dialog = new AWTDialog(
                    (Frame)null, "Confirm Close", true);

            dialog.setLocationRelativeTo(null);

            String yes = "Yes";
            String no = "No";

            final Button confirm = new Button(yes);
            final Button deny = new Button(no);

            MouseListener listener = new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                    Component c = e.getComponent();
                    if (!c.isEnabled())
                        return;
                    if (c == confirm) {
                        window.setVisible(false);
                        if (window instanceof Window)
                            ((Window)window).dispose();
                    }
                }
            };
            
            dialog.addButton(confirm);
            dialog.addButton(deny);
            dialog.addButtonsListener(listener);
            dialog.setText("Are you sure you want to close this window?");
            dialog.setVisible(true);
        }

        public void windowClosed(WindowEvent e)
        {}
    }),
    
    /**
     * Shows a dialog that warns the user to confirm quit, and closes based on 
     * their input
     */
    WARN_CONFIRM(new AbstractCloseWindowAdapter() {
        public void windowClosing(WindowEvent e)
        {
            final Component window = e.getComponent();
            AWTDialog dialog = new AWTDialog(
                    (Frame)null, "WARNING!", true);
            
            dialog.setLocationRelativeTo(null);
            dialog.setIconImage(Resources.getWarningIcon());
            
            String no = "Continue";
            String yes = "Close";
            
            final Button deny = new Button(no);
            final Button confirm = new Button(yes);
                        
            MouseListener listener = new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                    Component c = e.getComponent();
                    if (!c.isEnabled())
                        return;
                    if (c == confirm) {
                        window.setVisible(false);
                        if (window instanceof Window)
                            ((Window)window).dispose();
                    }
                }
            };
            dialog.addButton(deny);
            dialog.addButton(confirm);
            dialog.addButtonsListener(listener);
            dialog.setText(
                    "WARNING: Are you sure you want to close this window?");
            dialog.setVisible(true);
        }
        
        public void windowClosed(WindowEvent e)
        {}
    }),
    
    /**
     * Does nothing, the window can only be closed programmatically
     */
    NOTHING(new CloneableWindowAdapter() {});
    
    
    
    
    /**
     * The handler Object
     */
    private final CloneableWindowAdapter handler;
    
    
    
    
    /**
     * Makes a new {@code WindowCloseOperation}
     * 
     * @param handler the handler to encapsulate
     */
    private WindowCloseOperation(CloneableWindowAdapter handler) {
        this.handler = handler;
    }
    
    
    
    
    /**
     * {@inheritDoc}
     * 
     * @return a clone of the handler
     */
    public CloneableWindowAdapter getNewHandler() {
        return this.handler.clone();
    }
    
    
    
    
    /**
     * Convenience class for making a {@code WindowCloseOperation}
     */
    private static abstract class AbstractCloseWindowAdapter
            extends CloneableWindowAdapter {
        
        @Override
        public void windowOpened(WindowEvent e) {}
        
        @Override
        public abstract void windowClosing(WindowEvent e);
        
        @Override
        public abstract void windowClosed(WindowEvent e);
        
        @Override
        public void windowIconified(WindowEvent e) {}
        
        @Override
        public void windowDeiconified(WindowEvent e) {}
        
        @Override
        public void windowActivated(WindowEvent e) {}
        
        @Override
        public void windowDeactivated(WindowEvent e) {}
        
        @Override
        public void windowStateChanged(WindowEvent e) {}
        
        @Override
        public void windowGainedFocus(WindowEvent e) {}
        
        @Override
        public void windowLostFocus(WindowEvent e) {}
        
    }
    
}
