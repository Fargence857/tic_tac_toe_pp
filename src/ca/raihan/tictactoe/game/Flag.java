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

/**
 *
 * @author Pranjal Raihan
 */
public enum Flag {
    
    PLAYER_ONE(1),
    PLAYER_TWO(-1),
    NONE(0);
    
    private final int value;
    
    private Flag(int value) {
        this.value = value;
    }
    
    
    
    
    public int intValue() {
        return value;
    }
    
    public static boolean areOpposite(Flag f1, Flag f2) {
        if (f1 == NONE || f2 == NONE)
            return false;
        return (f1 == PLAYER_ONE || f1 == PLAYER_TWO) && f1 != f2;
    } 
    
}
