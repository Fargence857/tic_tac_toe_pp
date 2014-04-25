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
import java.awt.Color;
import java.awt.Image;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.function.Consumer;

import ca.raihan.util.Contract;

import ca.raihan.cfg.Config;

import ca.raihan.tictactoe.Keywords;

import ca.raihan.event.Event;
import ca.raihan.event.EventListener;
import ca.raihan.event.PrivilegedEvent;
import ca.raihan.event.EmptyEventContext;

import ca.raihan.tictactoe.players.Player;

/**
 *
 * @author Pranjal Raihan
 */
class AWTGrid implements Grid<Container> {
    
    private static final String NO_CHILDREN_MESSAGE = "Grid has no children";
    
    
    
    
    private int states;
    
    private GridCollection childGrids;
    
    private GridCollection peerGrids;
    
    private final Object mutex = new Object();
    
    private final Object eventKey;
    
    private Player player;
    
    private final PrivilegedEvent<ChangeBlockContext> changeBlockEvent;
    
    private final PrivilegedEvent<PlayContext> localEndEvent;
    
    private final PrivilegedEvent<PlayContext> playEvent;
    
    private AWTGridHandle guiHandle;
    
    private final GameManager<Container> gameManager;
    
    
    
    
    {
        guiHandle = new AWTGridHandle();
        guiHandle.setLayout(null);
        guiHandle.addMouseListener(new AWTMouseListener());
    }
    
    
    
    
    public AWTGrid(GameManager<Container> manager, Object eventKey) {
        this.gameManager = Contract.nonNull(manager);
        this.eventKey = eventKey;
        changeBlockEvent = new PrivilegedEvent<>(eventKey);
        localEndEvent = new PrivilegedEvent<>(eventKey);
        playEvent = new PrivilegedEvent<>(eventKey);
        setStatePlayableAndFilteredImpl(true);
    }
    
    
    
    
    public int getStates() {
        synchronized (mutex) {
            if ((states & States.GREAT_PARENT) == 0)
                checkGreatParent();
            return states;
        }
    }
    
    private void checkGreatParent() {
        if (peerGrids == null) {
            states |= States.GREAT_PARENT;
        }
    }
    
    void start() {
        synchronized (mutex) {
            states |= States.STARTED;
        }
    }
    
    @SuppressWarnings("unchecked")
    public Grid<Container> getGreatParent() {
        AWTGrid rv = this;
        synchronized (mutex) {
            while (!rv.isGreatParentImpl()) {
                rv = (AWTGrid) rv.getPeers().getOwner();
            }
            return rv;
        }
    }
    
    public GridCollection getChildren() {
        if (!hasStateChildrenImpl()) {
            throw new IllegalStateException(NO_CHILDREN_MESSAGE);
        }
        synchronized (mutex) {
            return new GridCollection.ConstGridCollection(childGrids);
        }
    }
    
    public GridCollection getPeers() {
        synchronized (mutex) {
            return new GridCollection.ConstGridCollection(peerGrids);
        }
    }
    
    public GridCoord getCoord() {
        synchronized (mutex) {
            if (peerGrids == null) {
                throw new IllegalStateException(
                        "No coordinate - top level grid: " + this.getHandle());
            }
            GridCoord rv = peerGrids.getCoord(this);
            if (rv == null) {
                throw new IllegalStateException(
                        "Peers do not recognized this grid");
            }
            return rv;
        }
    }
    
    public Player getPlayer() {
        synchronized (mutex) {
            return player;
        }
    }
    
    public AWTGridHandle getHandle() {
        return guiHandle;
    }
    
    public GameManager<Container> getGameManager() {
        return gameManager;
    }
    
    public Event<ChangeBlockContext> changeBlockEvent() {
        return changeBlockEvent;
    }
    
    public Event<PlayContext> endEvent() {
        return localEndEvent;
    }
    
    public Event<PlayContext> playEvent() {
        return playEvent;
    }
    
    public void accept(Player player) {
        Contract.nonNull(player);
        acceptImpl(player);
    }
    
    
    
    
    boolean hasStateChildrenImpl() {
        synchronized (mutex) {
            int state = getStates();
            return (state & States.HAS_CHILDREN) != 0;
        }
    }
    
    @SuppressWarnings("unchecked")
    AWTGrid getPreGreatParentImpl() {
           return isGreatParentImpl() ? 
                   this : 
                   (AWTGrid) getGreatParent()
                                .getChildren()
                                .getGrid(new GridCoord(0, 0));
    }
    
    boolean isGreatParentImpl() {
        return (getStates() & States.GREAT_PARENT) != 0;
    }
    
    private void acceptImpl(Player player) {
        setStateLockedImpl();
        if (hasStateChildrenImpl()) {
            getHandle().removeAll();
            this.childGrids.clear();
            this.childGrids = null;
            this.states = getStates() & ~States.HAS_CHILDREN;
        }
        synchronized (mutex) {
            this.player = player;
            getHandle().setImage(getImageOfPlayerImpl(player), true);
            boolean isGreat = isGreatParentImpl();
            if (isGreat) {
                getGameManager().getTurnManager().endEvent().raise(
                        eventKey, this, new EmptyEventContext());
                playEvent.raise(eventKey, this, 
                        new PlayContextImpl(
                                getGameManager().getFlagOf(player), null));
                return;
            }
            PlayContextImpl context = new PlayContextImpl(
                    getGameManager().getFlagOf(player), getCoord());
            playEvent.raise(eventKey, this, context);
            GridCoord c = getCoord();
            GridCollection.UpdateInfo ui = peerGrids.update();
            Flag f = ui.getFlag();
            if (f != Flag.NONE) {
                localEndEvent.raise(eventKey, this, new PlayContextImpl(f, c));
            }
        }
    }
    
    private Image getImageOfPlayerImpl(Player player) {
        String key;
        GameManager<Container> gm = getGameManager();
        Flag flag = gm.getFlagOf(player);
        switch (flag) {
            case PLAYER_ONE:
                key = Keywords.IMAGE_PLAYER_ONE;
                break;
            case PLAYER_TWO:
                key = Keywords.IMAGE_PLAYER_TWO;
                break;
            default:
                throw new IllegalArgumentException(
                        "cannot resolve flag: " + flag);
        }
        
        return (Image) gm.<Image>getResource(key, Image.class);
    }
    
    boolean isStateLockedImpl() {
        synchronized (mutex) {
            return (states & States.LOCKED) != 0; 
        }
    }
    
    final void setStateLockedImpl() {
        setStatePlayableAndFilteredImpl(false);
        synchronized (mutex) {
            this.states |= States.LOCKED;
            AWTGridHandle handle = getHandle();
            handle.setFiltered(true);
        }
    }
    
    boolean isStatePlayableImpl() {
        return (getStates() & States.PLAYABLE) != 0;
    }
    
    final void setStatePlayableAndFilteredImpl(boolean playable) {
        if (isStateLockedImpl())
            return;
        synchronized (mutex) {
            states = playable ? 
                    (states | States.PLAYABLE) : 
                    (states & ~States.PLAYABLE);
            AWTGridHandle handle = getHandle();
            handle.setFiltered(!playable);
        }
    }
    
    final void setStateOnlyNonPlayableImpl(boolean playable) {
        if (isStateLockedImpl())
            return;
        synchronized (mutex) {
            states = playable ? 
                    (states | States.PLAYABLE) : 
                    (states & ~States.PLAYABLE);
        }
    }
    
    void generateChildrenImpl(final int depth) {
        if (depth == 0)
            return;
        setStateOnlyNonPlayableImpl(false);
        childGrids = new GridCollection(this);
        states |= States.HAS_CHILDREN;
        ChangeBlockListener evtChange = new ChangeBlockListener();
        PlayListener evtPlay = new PlayListener();
        EndListener evtEnd = new EndListener();
        final int size = getHandle().getWidth() / GridConsts.DIMENSION;
        for (int i = 0; i < GridConsts.DIMENSION; ++i) {
            for (int j = 0; j < GridConsts.DIMENSION; ++j) {
                AWTGrid g = new AWTGrid(getGameManager(), eventKey);
                AWTGridHandle h = g.getHandle();
                h.setSize(size);
                h.setLocation(i * size, j * size);
                addImpl(g);
                g.changeBlockEvent().addListener(evtChange);
                g.playEvent().addListener(evtPlay);
                g.endEvent().addListener(evtEnd);
                g.peerGrids = childGrids;
                childGrids.putGrid(new GridCoord(i, j), g);
            }
        }
        childGrids.forEach(new Consumer<Grid>() {
            
            public void accept(Grid t) {
                if (t instanceof AWTGrid) {
                    ((AWTGrid) t).generateChildrenImpl(depth - 1);
                }
            }
            
        });
    }
    
    private void addImpl(AWTGrid g) {
        getHandle().add(g.getHandle());
    }
    
    private void evalutateChangeImpl(AWTGrid sender, PlayContext context) {
        if (isGreatParentImpl()) {
            return;
        }
        GridCoord coord = sender.getCoord();
        if (coord == null) {
            return;
        }
        GridCollection peers = getPeers();
        AWTGrid grid = (AWTGrid) peers.getGrid(coord);
        final boolean locked = (grid.getStates() & States.LOCKED) != 0;
        peers.forEach(new Consumer<Grid>() {
            
            @Override
            public void accept(Grid t) {
                if (t instanceof AWTGrid) {
                    AWTGrid g = ((AWTGrid) t);
                    g.doHierarchial(new Consumer<AWTGrid>() {
                        
                        @Override
                        public void accept(AWTGrid t) {
                            t.setStatePlayableAndFilteredImpl(locked);
                        }
                        
                    });
                }
            }
            
        });
        if (!locked) {
            grid.doHierarchial(new Consumer<AWTGrid>() {
                
                @Override
                public void accept(AWTGrid t) {
                    t.setStatePlayableAndFilteredImpl(true);
                }
            
            });
        }
    }
    
    @SuppressWarnings("unchecked")
    void doHierarchial(Consumer<? super AWTGrid> consumer) {
        consumer.accept(this);
        if (hasStateChildrenImpl()) {
            for (Grid g : getChildren()) {
                ((AWTGrid) g).doHierarchial(consumer);
            }
        }
    }
    
    
    
    
    class ChangeBlockListener implements 
            EventListener<Grid.ChangeBlockContext> {
        
        public void onEvent(Object sender, ChangeBlockContext context) {
        }
        
    }
    
    class ChangeBlockContextImpl implements Grid.ChangeBlockContext {
        
        private final GridCoord coord;
        
        
        
        
        public ChangeBlockContextImpl(GridCoord coord) {
            this.coord = coord;
        }
        
        
        
        
        public GridCoord getCoord() {
            return coord;
        }
        
    }
    
    class AWTMouseListener extends MouseAdapter {
        
        private Color playableColor = Color.GREEN;
        
        private String lastPlayableString = "#00FF00";
        
        private Color unplayableColor = Color.RED;
        
        private String lastUnplayableString = "#FF0000";
        
        private Color neutral;
        
        @Override
        public void mouseClicked(MouseEvent evt) {
            if (isStatePlayableImpl() && !hasStateChildrenImpl()) {
                TurnManager t = getGameManager().getTurnManager();
                accept(t.getCurrentPlayer());
                t.nextTurnEvent().raise(
                        eventKey, this, new EmptyEventContext());
            }
        }
        
        @Override
        public void mouseEntered(MouseEvent evt) {
            if (isStateLockedImpl() || hasStateChildrenImpl()) {
                return;
            }
            checkColors();
            Color c = isStatePlayableImpl() ? playableColor : unplayableColor;
            AWTGridHandle h = getHandle();
            h.setBackground(c);
            h.setForeground(c);
            h.setImage(
                    getImageOfPlayerImpl(
                            getGameManager()
                                    .getTurnManager()
                                    .getCurrentPlayer())); 
        }
        
        @Override
        public void mouseExited(MouseEvent evt) {
            checkColors();
            if (neutral != null) {
                AWTGridHandle h = getHandle();
                h.setBackground(neutral);
                h.setForeground(neutral);
            }
            if (isStateLockedImpl() || hasStateChildrenImpl()) {
                return;
            }
            getHandle().setImage(null, false);
        }
        
        private void checkColors() {
            if (neutral == null)
                neutral = getHandle().getBackground();
            Config cfg = getGameManager().getConfig();
            String found = cfg.getString(Keywords.COLOR_PLAYABLE);
            if (!found.equalsIgnoreCase(lastPlayableString)) {
                playableColor = Color.decode(lastPlayableString = found);
            }
            found = cfg.getString(Keywords.COLOR_UNPLAYABLE);
            if (!found.equalsIgnoreCase(lastUnplayableString)) {
                unplayableColor = Color.decode(lastUnplayableString = found);
            }
        }
        
    }
    
    class PlayListener implements 
            EventListener<Grid.PlayContext> {
        
        @SuppressWarnings("unchecked")
        public void onEvent(Object sender, PlayContext context) {
            getPreGreatParentImpl().evalutateChangeImpl(
                    (AWTGrid) sender, context);
        }
        
    }
    
    class EndListener implements EventListener<Grid.PlayContext> {
        
        @SuppressWarnings("unchecked")
        public void onEvent(Object sender, PlayContext context) {
            Flag f = context.getFlag();
            switch (f) {
                case PLAYER_ONE:
                case PLAYER_TWO:
                    accept(getGameManager().getPlayer(f));
                    System.out.println("local end");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid flag: " + f);
            }
        }
        
    }
    
    class PlayContextImpl implements Grid.PlayContext {
        
        private final Flag flag;
        
        private final GridCoord coord;
        
        
        
        
        PlayContextImpl(Flag flag, GridCoord coord) {
            this.flag = flag;
            this.coord = coord;
        }
        
        
        
        
        public Flag getFlag() {
            return this.flag;
        }
        
        public GridCoord getCoord() {
            return coord;
        }
        
    }
    
}
