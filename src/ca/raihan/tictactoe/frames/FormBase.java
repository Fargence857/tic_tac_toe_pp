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

package ca.raihan.tictactoe.frames;

import ca.raihan.awt.AWTFrame;

/**
 * The abstract base class for Game frame(s).
 * 
 * @author Pranjal Raihan
 */
public abstract class FormBase extends AWTFrame {
    
    protected FormBase() {
        initComponents();
        this.setTitle("Tic Tac Toe++");
        this.setResizable(false);
    }
    
    private void initComponents() {
        setPreferredSize(new java.awt.Dimension(700, 700));
        setLayout(null);
        
        pack();
    }
    
}
