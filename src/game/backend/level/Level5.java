package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.Cell;
import game.backend.cell.ConditionalRemovalCell;
import game.backend.cell.SpecialCandyGeneratorCell;
import game.backend.element.Element;
import game.backend.element.Fruit;
import game.backend.element.FruitType;
import game.backend.move.MoveMakerWithFruits;

public class Level5 extends Grid {
    public static final int REQUIRED_UNCOMBINABLES = 5;
    public static final int UNCOMBINABLE_FRECUENCY = 5;
    public static final int MAX_MOVES = 70;


    @Override
    protected Cell cellCreator() {
        return new ConditionalRemovalCell(this);
    }

    @Override
    protected GameState newState() {
        return new Level5State(REQUIRED_UNCOMBINABLES);
    }

    @Override
    public boolean cellRemovalCriteria(Cell cell){
        return cell.isMovable() && (cell.getContent().isCombinable() || cell.isBottom());
    }

    @Override
    protected void setMoveMaker() {
        moveMaker = new MoveMakerWithFruits(this);
    }

    private void fruitRemoval(){
        for(int i = 0; i < SIZE; i++){
            if(!g()[SIZE-1][i].getContent().isCombinable()){
                clearContent(SIZE-1, i);
                ((Level5State)state()).addRemovedFruit();
            }
        }
        fallElements();
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        if (ret = super.tryMove(i1, j1, i2, j2)) {
            state().addMove();
            fruitRemoval();
        }
        return ret;
    }

    @Override
    public Element getSpecialLevelElement() {
        int i = (int)(Math.random() * FruitType.values().length);
        return new Fruit(FruitType.values()[i]);
    }

    @Override
    protected void setCandyCellGenerator() {
        candyGenCell = new SpecialCandyGeneratorCell(this, UNCOMBINABLE_FRECUENCY, REQUIRED_UNCOMBINABLES);
    }

    private class Level5State extends GameState{


        private int fruitsLeft;

        public Level5State(int fruitsLeft) {
            this.fruitsLeft = fruitsLeft;
        }

        @Override
        public boolean gameOver() {
            return playerWon() || getMoves() >= MAX_MOVES;
        }

        @Override
        public boolean playerWon() {
            return fruitsLeft <= 0;
        }

        public void addRemovedFruit(){
            fruitsLeft--;
        }

        @Override
        public boolean hasExtraScoreInfo() {
            return true;
        }

        @Override
        public String getExtraScoreMessage() {
            return "Fruits left: ";
        }

        @Override
        public String getExtraScoreValue() {
            return String.valueOf(fruitsLeft);
        }

    }
}
