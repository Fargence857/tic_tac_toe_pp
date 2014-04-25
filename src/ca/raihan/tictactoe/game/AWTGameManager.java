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

import java.awt.Container;

import java.util.HashMap;
import java.util.Map;

import java.util.function.Consumer;

import ca.raihan.cfg.Config;
import ca.raihan.tictactoe.Keywords;

import ca.raihan.tictactoe.players.Player;
import ca.raihan.util.Contract;

/**
 *
 * @author Pranjal Raihan
 */
public final class AWTGameManager implements GameManager<Container> {
    
    private final AWTTurnManager turnManager;
    
    private final Object eventKey = new Object();
    
    private AWTGrid grid;
    
    private Config config;
    
    private final Map<String, Object> resources = new HashMap<>();
    
    private final Player player1;
    
    private final Player player2;
    
    
    
    public AWTGameManager(
            GameInitParams<? extends Player, ? extends Player> initParams) {
        Contract.nonNull(initParams);
        turnManager = new AWTTurnManager(this, eventKey);
        setConfig(initParams.getConfig());
        this.player1 = initParams.getPlayer1();
        this.player2 = initParams.getPlayer2();
        resources.put(Keywords.IMAGE_PLAYER_ONE, initParams.getPlayer1Image());
        resources.put(Keywords.IMAGE_PLAYER_TWO, initParams.getPlayer2Image());
    }
    
    @SuppressWarnings("unchecked")
    public void createGrids(int depth, int size) {
        if (grid != null) {
            throw new IllegalStateException("Grid already created");
        }
        grid = new AWTGrid(this, eventKey);
        grid.getHandle().setSize(size);
        grid.generateChildrenImpl(depth);
        grid.doHierarchial(new Consumer<Grid>() {
            
            @Override
            public void accept(Grid t) {
                ((AWTGrid) t).setStatePlayableAndFilteredImpl(false);
            }
            
        });
    }
    
    public void populate(Container container, int x, int y) {
        if (grid == null) {
            throw new IllegalStateException("Grids not created");
        }
        grid.getHandle().setLocation(x, y);
        container.add(grid.getHandle());
    }
    
    public void start() {
        grid.doHierarchial(new Consumer<Grid>() {
            
            @Override
            public void accept(Grid t) {
                AWTGrid g = (AWTGrid) t;
                if (!g.hasStateChildrenImpl())
                    g.setStatePlayableAndFilteredImpl(true);
            }
            
        });
    }
    
    public AWTTurnManager getTurnManager() {
        return turnManager;
    }
    
    public Config getConfig() {
        return config;
    }
    
    public void setConfig(Config cfg) {
        this.config = cfg;
    }
    
    @SuppressWarnings("unchecked")
    public <U> U getResource(String key, Class<U> type) {
        Contract.nonNull(key);
        Contract.nonNull(type);
        Object found =  resources.get(key);
        if (found == null)
            return null;
        if (type.isAssignableFrom(found.getClass()))
            return (U) found;
        throw new IllegalArgumentException(
                "No resource with key \"" 
                        + key + "\" and type \"" 
                        + type.getName() + "\"");
    }

    @Override
    public Player getPlayer(Flag flag) {
        Contract.nonNull(flag);
        if (flag == Flag.NONE) {
            return null;
        }
        return flag == Flag.PLAYER_ONE ? player1 : player2;
    }
    
    @Override
    public Flag getFlagOf(Player player) {
        if (player == player1) {
            return Flag.PLAYER_ONE;
        } else if (player == player2) {
            return Flag.PLAYER_TWO;
        }
        throw new IllegalArgumentException("Player not found in this manager");
    }
    
}
