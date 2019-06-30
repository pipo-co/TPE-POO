package game.backend.level;

import game.backend.Figure;
import game.backend.GameListener;
import game.backend.Grid;
import game.backend.cell.Cell;
import game.backend.cell.SpecialCandyGeneratorCell;
import game.backend.element.*;
import game.backend.level.gameState.GameState;
import game.backend.level.gameState.Level4State;

import java.util.Timer;
import java.util.TimerTask;

public class Level4 extends Grid {

    private static final int SECOND = 1000;
    private static final int TIMER_DELAY = SECOND/2;
    private static final int INITIAL_TIME = 120*SECOND;
    private static final int FREQUENCY = 5;
    private static final int AMOUNT = 5;
    private static final int INITIAL_AMOUNT = 3;


    private Timer timer;

    @Override
    protected GameState newState() {
        return new Level4State(INITIAL_TIME);
    }

    @Override
    public void initialize() {
        super.initialize();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ((Level4State)state()).subSecond();
            }
        }, TIMER_DELAY, SECOND);
    }

    @Override
    protected void setCandyCellGenerator() {
        candyGenCell = new SpecialCandyGeneratorCell(this, FREQUENCY, AMOUNT, INITIAL_AMOUNT);
    }

    @Override
    public void cellExplosion(Element e){
        int bonus = 0;
        if(e.hasBonus()){
            bonus = ((TimeCandy)e).getBonusTime();
        }
        ((Level4State)state()).addTime(bonus);
        super.cellExplosion(e);
    }

    @Override
    public Element getSpecialLevelElement() {
        int i = (int)(Math.random() * CandyColor.values().length);
        int j = (int)(Math.random() * BonusTimesEnum.values().length);

        return new TimeCandy(CandyColor.values()[i], BonusTimesEnum.values()[j].getValue());
    }
}
