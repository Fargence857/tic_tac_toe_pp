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

import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.function.Consumer;

import static ca.raihan.tictactoe.game.GridConsts.*;

import ca.raihan.tictactoe.players.Player;

import ca.raihan.util.Contract;

/**
 *
 * @author Pranjal Raihan
 */
public class GridCollection implements Iterable<Grid> {
    
    private static final int ARRAY_LENGTH = DIMENSION * DIMENSION;
    
    private final FlagGrid flagGrid = new FlagGrid(ARRAY_LENGTH);
    
    private final Grid[][] grids;
    
    private final Object mutex = new Object();
    
    private final Grid owner;
    
    private int count;
    
    
    
    
    public GridCollection(Grid owner) {
        grids = new Grid[DIMENSION][DIMENSION];
        this.owner = owner;
    }
    
    
    
    
    public void putGrid(GridCoord coord, Grid grid) {
        GridUtils.checkCoord(coord);
        synchronized (mutex) {
            Grid g = grids[coord.getX()][coord.getY()];
            if (g == null) {
                if (grid == null)
                    return;
                count++;
            } else {
                if (grid == null) {
                    count--;
                }
            }
            grids[coord.getX()][coord.getY()] = grid;
        }
    }
    
    public int count() {
        synchronized (mutex) {
            return count;
        }
    }
    
    public void setFlag(GridCoord coord, Flag flag) {
        synchronized (mutex) {
            flagGrid.setFlag(coord, flag);
        }
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    public Grid getGrid(GridCoord coord) {
        GridUtils.checkCoord(coord);
        synchronized (mutex) {
            return grids[coord.getX()][coord.getY()];
        }
    }
    
    public Grid getOwner() {
        return owner;
    }
    
    public GridCoord getCoord(Grid grid) {
        Contract.nonNull(grid);
        for (int i = 0; i < grids.length; ++i) {
            for (int j = 0; j < grids.length; ++j) {
                if (grids[i][j] == grid) {
                    return new GridCoord(i, j);
                }
            }
        }
        return null;
    }
    
    public UpdateInfo update() {
        updateFlagGrid();
        return new UpdateInfo(flagGrid, checkWinFlag());
    }
    
    private void updateFlagGrid() {
        final int size = grids.length;
        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j) {
                GridCoord coord = new GridCoord(i, j);
                Grid g = getGrid(coord);
                Player p = g.getPlayer();
                if (p != null && flagGrid.getFlag(coord) == Flag.NONE) {
                    flagGrid.setFlag(coord, 
                            g.getGameManager().getFlagOf(p));
                }
            }
    }
    
    private Flag checkWinFlag() {
        for (int i = 0; i < GridConsts.DIMENSION; ++i) {
            // check columns
            switch (sum(flagGrid.getColumn(i))) {
                case DIMENSION:
                    return Flag.PLAYER_ONE;
                case -DIMENSION:
                    return Flag.PLAYER_TWO;
            }
            // check rows
            switch (sum(flagGrid.getRow(i))) {
                case DIMENSION:
                    return Flag.PLAYER_ONE;
                case -DIMENSION:
                    return Flag.PLAYER_TWO;
            }
        }
        
        // check diagonal top left to bottom right
        switch (sum(flagGrid.getDiagonalLeftToRight())) {
            case DIMENSION:
                return Flag.PLAYER_ONE;
            case -DIMENSION:
                return Flag.PLAYER_TWO;
        }
        
        // check diagonal top right to bottom left
        switch (sum(flagGrid.getDiagonalRightToLeft())) {
            case DIMENSION:
                return Flag.PLAYER_ONE;
            case -DIMENSION:
                return Flag.PLAYER_TWO;
        }
        
        return Flag.NONE;
    }
    
    private static int sum(Flag[] flags) {
        int rv = 0;
        for (Flag f : flags) {
            rv += f.intValue();
        }
        return rv;
    }
    
    
    
    
    public void forEach(Consumer<? super Grid> action) {
        Contract.nonNull(action);
        synchronized (mutex) {
            for (Grid grid : this) {
                action.accept(grid);
            }
        }
    }    
    
    @SuppressWarnings("unchecked")
    public Iterator<Grid> iterator() {
        return new Itr(grids);
    }
    
    
    
    
    public void clear() {
        for (Grid[] arr : grids) {
            for (int i = 0; i < arr.length; ++i) {
                arr[i] = null;
            }
        }
    }
    
    
    
    
    public static class UpdateInfo {
        
        private final FlagGrid flagGrid;
        
        private final Flag flag;
        
        
        
        
        protected UpdateInfo(FlagGrid flagGrid, Flag flag) {
            this.flagGrid = new FlagGrid.ConstGrid(flagGrid);
            this.flag = flag;
        }
        
        
        
        
        public FlagGrid getFlagGrid() {
            return flagGrid;
        }
        
        public Flag getFlag() {
            return flag;
        }
        
    }
    
    
    
    
    private static class Itr<T extends Grid> implements Iterator<T> {
        
        private final Grid[][] grids;
        
        private final int len;
        
        private int row;
        private int col = -1;
        
        
        
        
        // Assumed square array
        private Itr(T[][] grids) {
            this.len = grids.length;
            this.grids = new Grid[len][len];
            for (int i = 0; i < len; ++i) {
                for (int j = 0; j < len; ++j) {
                    this.grids[i][j] = grids[i][j];
                }
            }
        }
        
        
        
        
        public boolean hasNext() {
            return (row == len - 1 ? 
                    (col < len - 1) : 
                    row < len);
        }
        
        @SuppressWarnings("unchecked")
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException(
                        "iterator has no more elements");
            if (col == len - 1) {
                col = -1;
                row++;
            }
            return (T) grids[row][++col];
        }
        
        public void remove() {
            throw new UnsupportedOperationException(
                    "GridCollection iterator saves a copy "
                            + "of the state of the grid");
        }
        
    }
    
    public static class ConstGridCollection extends GridCollection {
        
        private final GridCollection gridCollection;
        
        
        
        
        public ConstGridCollection(GridCollection gridCollection) {
            super(null);
            this.gridCollection = Contract.nonNull(gridCollection);
        }
        
        
        
        
        @Override
        public void putGrid(GridCoord coord, Grid grid) {
            throw new UnsupportedOperationException(
                    "ConstGridCollection is directly immutable");
        }
        
        @Override
        public void setFlag(GridCoord coord, Flag flag) {
            throw new UnsupportedOperationException(
                    "ConstGridCollection is directly immutable");
        }
        
        @Override
        public void clear() {
            throw new UnsupportedOperationException(
                    "ConstGridCollection is directly immutable");
        }
        
        @Override
        public UpdateInfo update() {
            throw new UnsupportedOperationException(
                    "ConstGridCollection is directly immutable");
        }
        
        @Override
        public GridCoord getCoord(Grid grid) {
            return gridCollection.getCoord(grid);
        }
        
        @Override
        public Grid getGrid(GridCoord coord) {
            return gridCollection.getGrid(coord);
        }
        
        @Override
        public Grid getOwner() {
            return gridCollection.getOwner();
        }
        
        @Override
        public int count() {
            return gridCollection.count();
        }
        
        @Override
        public Iterator<Grid> iterator() {
            return gridCollection.iterator();
        }
        
    }
    
}
