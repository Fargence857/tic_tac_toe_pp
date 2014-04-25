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
import java.awt.Graphics;

/**
 *
 * @author Pranjal Raihan
 */
class AWTGridHandle extends ImagePanel {
    
    private boolean isBorderShown = true;
    
    private Color borderColor = Color.BLACK;
    
    
    
    
    public boolean isBorderShown() {
        return isBorderShown;
    }
    
    public void setBorderShown(boolean flag) {
        this.isBorderShown = flag;
        repaint();
    }
    
    public Color getBorderColor() {
        return borderColor;
    }
    
    public void setBorderColor(Color color) {
        this.borderColor = color;
    }
    
    
    
    
    public void setSize(int wh) {
        super.setSize(wh, wh);
    }
    
    
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (isBorderShown()) {
            Color c = getBorderColor();
            if (c != null) {
                g.setColor(c);
                int w = this.getWidth() - 1;
                int h = this.getHeight() - 1;
                if (w > 0 && h > 0)
                    g.drawRect(0, 0, w, h);
            }
        }
    }
    
}
