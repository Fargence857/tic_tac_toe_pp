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

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author Pranjal Raihan
 */
public class FlagGrid implements Iterable<Flag> {
    
    protected final Flag[][] grid;
    
    private final Object mutex = new Object();
    
    
    
    
    public FlagGrid(final int len) {
        Contract.require(len >= 0, "len < 0");
        grid = new Flag[len][len];
        for (Flag[] arr : grid) {
            Arrays.fill(arr, Flag.NONE);
        }
    }
    
    public FlagGrid(FlagGrid copy) {
        Contract.nonNull(copy);
        final int len = copy.grid.length;
        this.grid = new Flag[len][len];
        for (int i = 0; i < len; ++i) {
            for (int j = 0; j < len; ++j) {
                this.grid[i][j] = copy.grid[i][j];
            }
        }
    }
    
    
    
    
    public Flag[] getRow(final int index) {
        final int len = grid.length;
        Contract.require(
                index >= 0 && index < len, 
                "index out of bounds: " + len);
        synchronized (mutex) {
            return Arrays.copyOf(grid[index], len);
        }
    }
    
    public Flag[] getColumn(final int index) {
        final int len = grid.length;
        Contract.require(
                index >= 0 && index < len, 
                "index out of bounds: " + len);
        Flag[] colArray = new Flag[len];
        synchronized (mutex) {
            for (int i = 0; i < len; ++i)
                colArray[i] = grid[i][index];
            return colArray;
        }
    }
    
    public Flag[] getDiagonalLeftToRight() {
        synchronized (mutex) {
            final int len = grid.length;
            Flag[] diagArray = new Flag[len];
            for (int i = 0; i < len; ++i)
                diagArray[i] = grid[i][i];
            return diagArray;
        }
    }
    
    public Flag[] getDiagonalRightToLeft() {
        synchronized (mutex) {
            final int len = grid.length;
            Flag[] diagArray = new Flag[len];
            for (int i = 0; i < len; ++i)
                diagArray[i] = grid[i][(len - 1) - i];
            return diagArray;
        }
    }
    
    public boolean isFull() {
        synchronized (mutex) {
            for (Flag flag : this) {
                if (flag == Flag.NONE) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Flag getFlag(GridCoord coord) {
        GridUtils.checkCoord(coord);
        synchronized (mutex) {
            return grid[coord.getX()][coord.getY()];
        }
    }
    
    public void setFlag(GridCoord coord, Flag flag) {
        Contract.nonNull(flag);
        GridUtils.checkCoord(coord);
        synchronized (mutex) {
            grid[coord.getX()][coord.getY()] = flag;
        }
    }
    
    public int played() {
        int count = 0;
        for (Flag flag : this) {
            switch (flag) {
                case PLAYER_ONE:
                case PLAYER_TWO:
                   count++;
            }
        }
        return count;
    }
    
    public int unplayed() {
        int count = 0;
        for (Flag flag : this) {
            switch (flag) {
                case NONE:
                   count++;
            }
        }
        return count;
    }
    
    
    
    
    public Iterator<Flag> iterator() {
        return new Itr(grid);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Flag[] f : grid) {
            builder.append(Arrays.toString(f));
            builder.append("\n");
        }
        return builder.toString();
    }
    
    public static final class ConstGrid extends FlagGrid {
        
        public ConstGrid(FlagGrid copy) {
            super(copy);
        }
        
        @Override
        public void setFlag(GridCoord coord, Flag flag) {
            throw new UnsupportedOperationException(
                    getClass().getName() + " is immutable");
        }
        
    }
    
    
    
    
    private static class Itr implements Iterator<Flag> {
        
        private final Flag[][] grid;
        
        private final int len;
        
        private int row;
        private int col;
        
        
        
        
        // Assumed square array
        private Itr(Flag[][] grid) {
            this.len = grid.length;
            this.grid = new Flag[len][len];
            for (int i = 0; i < len; ++i) {
                for (int j = 0; j < len; ++j) {
                    this.grid[i][j] = grid[i][j];
                }
            }
        }
        
        
        
        
        public boolean hasNext() {
            return (row == len - 1 ? 
                    (col < len - 1) : 
                    row < len);
        }
        
        public Flag next() {
            if (!hasNext())
                throw new NoSuchElementException(
                        "iterator has no more elements");
            if (col == len - 1) {
                col = -1;
                row++;
            }
            return grid[row][++col];
        }
        
        public void remove() {
            throw new UnsupportedOperationException("Removal not supported");
        }
        
    }
    
}
