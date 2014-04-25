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

import ca.raihan.event.EventContext;
import ca.raihan.tictactoe.players.Player;

import ca.raihan.util.internal.__UninstantiableImpl;

/**
 *
 * @author Pranjal Raihan
 * @param <T>
 */
public interface Grid<T> {
    
    int getStates();
    
    Grid<T> getGreatParent();
    
    GridCollection getChildren();
    
    GridCollection getPeers();
    
    GridCoord getCoord();
    
    Player getPlayer();
    
    GameManager getGameManager();
    
    T getHandle();
    
    void accept(Player player);
    
    
    
    
    static class States extends __UninstantiableImpl {
        
        public static final int PLAYABLE;
        
        public static final int STARTED;
        
        public static final int LOCKED;
        
        public static final int HAS_CHILDREN;
        
        public static final int GREAT_PARENT;
        
        
        
        
        static {
            PLAYABLE = 1;
            STARTED = 1 << 1;
            LOCKED = 1 << 2;
            HAS_CHILDREN = 1 << 3;
            GREAT_PARENT = 1 << 4;
        }
        
        
        
        
        private States() {
        }
        
    }
    
    static interface ChangeBlockContext extends EventContext {
        
        GridCoord getCoord();
        
    }
    
    static interface PlayContext extends EventContext {
        
        Flag getFlag();
        
        GridCoord getCoord();
        
    }
    
}
