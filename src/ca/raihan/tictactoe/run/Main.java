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

package ca.raihan.tictactoe.run;

import ca.raihan.awt.AWTFrame;
import ca.raihan.cfg.Config;
import ca.raihan.tictactoe.Keywords;

import ca.raihan.tictactoe.game.AWTGameManager;
import ca.raihan.tictactoe.game.GameInitParams;

import ca.raihan.tictactoe.players.Player;
import ca.raihan.tictactoe.resources.Resources;

import java.awt.Color;
import java.awt.Image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.Scanner;

/**
 *
 * @author Pranjal Raihan
 */
public class Main {
    
    /**
     * @param args the command line arguments
     * 
     * @throws java.lang.Throwable in any case
     */
    public static void main(String[] args) throws Throwable {
        
        AWTFrame frame = new AWTFrame();
        
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.GRAY);
        
        Config cfg = Resources.getDefaultConfig();
        
        FileLocationType xType, oType;
        try {
            xType = FileLocationType.valueOf(
                    cfg.getString(Keywords.IMAGE_X_FILE_LOC_TYPE));
            oType = FileLocationType.valueOf(
                    cfg.getString(Keywords.IMAGE_O_FILE_LOC_TYPE));
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid file location type", ex);
        }
        
        String xPath = cfg.getString(Keywords.IMAGE_X_FILE_LOC);
        String oPath = cfg.getString(Keywords.IMAGE_O_FILE_LOC);
        
        Image xImage = resolveImage(xPath, xType);
        Image oImage = resolveImage(oPath, oType);
        
        AWTGameManager gm = new AWTGameManager(
                new GameInitParams<>(
                        new PlayerImpl(), xImage, 
                        new PlayerImpl(), oImage, 
                        cfg));
        
        System.out.print("depth: ");
        Scanner scanner = new Scanner(System.in);
        try {
            int depth = Integer.parseInt(scanner.nextLine().trim());
            gm.createGrids(depth, 500);
            gm.populate(frame, 50, 50);
            gm.start();
            frame.setVisible(true);
        } catch (NumberFormatException ex) {
            System.err.println(ex);
            System.exit(-1);
        }        
    }
    
    private static enum FileLocationType {
        EMBEDDED,
        ABSOLUTE;
    }
    
    private static Image resolveImage(String path, FileLocationType flt) 
            throws IOException {
        switch (flt) {
            case ABSOLUTE:
                return ImageIO.read(new File(path));
            case EMBEDDED:
                return ImageIO.read(Resources.class.getResourceAsStream(path));
        }
        throw new IOException(
                "Cannot find or read image, " + path + ", type: " + flt);
    }
    
    private static class PlayerImpl implements Player {
        
        public String getCanonicalName() {
            return "";
        }
        
    }
    
}
