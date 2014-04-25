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

import ca.raihan.util.internal.__UninstantiableImpl;

import ca.raihan.util.Contract;

import static ca.raihan.tictactoe.game.GridConsts.*;

/**
 *
 * @author Pranjal Raihan
 */
final class GridUtils extends __UninstantiableImpl {
    
    private GridUtils() {
    }
    
    
    
    static void checkCoord(GridCoord coord) {
        Contract.nonNull(coord);
        final int x = coord.getX();
        final int y = coord.getY();
        if (x < 0 || x >= DIMENSION || y < 0 || y >= DIMENSION) {
            throw new IllegalArgumentException(
                    "Coordinate out of bounds: " + coord);
        }
    }
    
    /**
     * Converts {@code GridCoord} to an {@code int}.
     * 
     * @param coordinate the coordinate to convert
     * 
     * @return the index that corresponds to the specified coordinate
     */
    static int coordToIndex(GridCoord coordinate) {
        return (3 * coordinate.getX() + coordinate.getY());
    }
    
}
