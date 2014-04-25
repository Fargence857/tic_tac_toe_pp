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

import java.awt.Image;

import ca.raihan.cfg.Config;

import ca.raihan.util.Contract;

import ca.raihan.tictactoe.players.Player;

/**
 *
 * @author Pranjal Raihan
 * @param <P1>
 * @param <P2>
 */
public class GameInitParams<P1 extends Player, P2 extends Player> {
    
    private final P1 player1;
    private final P2 player2;
    
    private final Image player1Image;
    private final Image player2Image;
    
    private final Config config;
    
    
    
    
    public GameInitParams(P1 p1, Image p1Image, P2 p2, Image p2Image, 
            Config cfg) {
        this.player1 = Contract.nonNull(p1);
        this.player2 = Contract.nonNull(p2);
        this.player1Image = p1Image;
        this.player2Image = p2Image;
        this.config = cfg;
    }
    
    
    
    
    public P1 getPlayer1() {
        return player1;
    }
    
    public P2 getPlayer2() {
        return player2;
    }
    
    public Image getPlayer1Image() {
        return player1Image;
    }
    
    public Image getPlayer2Image() {
        return player2Image;
    }
    
    public Config getConfig() {
        return config;
    }
    
}
