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

import ca.raihan.util.Contract;

import ca.raihan.event.EventContext;
import ca.raihan.event.EventListener;
import ca.raihan.event.PrivilegedEvent;

import ca.raihan.tictactoe.players.Player;

/**
 *
 * @author Pranjal Raihan
 */
public class AWTTurnManager implements TurnManager {
    
    private AWTGrid grid;
    
    private final PrivilegedEvent<EventContext> nextTurn;
    
    private final PrivilegedEvent<EventContext> endTurn;
    
    private boolean p1Turn = true;
    
    private final AWTGameManager gameManager;
    
    
    
    
    public AWTTurnManager(AWTGameManager gameManager, Object eventKey) {
        nextTurn = new PrivilegedEvent<>(eventKey);
        endTurn = new PrivilegedEvent<>(eventKey);
        this.gameManager = Contract.nonNull(gameManager);
        nextTurnEvent().addListener(new EventListener<EventContext>() {

            @Override
            public void onEvent(Object sender, EventContext context) {
                p1Turn ^= true;
            }
            
        });
    }
    
    public Player getCurrentPlayer() {
        return p1Turn ? 
                gameManager.getPlayer(Flag.PLAYER_ONE) : 
                gameManager.getPlayer(Flag.PLAYER_TWO);
    }
    
    public void start() {
        if (grid == null) {
            throw new IllegalStateException("Grids not created");
        }
        grid.start();
    }
    
    public final PrivilegedEvent<EventContext> nextTurnEvent() {
        return nextTurn;
    }
    
    public final PrivilegedEvent<EventContext> endEvent() {
        return endTurn;
    }
    
}
