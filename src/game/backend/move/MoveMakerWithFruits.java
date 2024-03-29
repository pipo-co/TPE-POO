package game.backend.move;

import game.backend.Grid;
import game.backend.element.*;

public class MoveMakerWithFruits extends MoveMaker{

    public MoveMakerWithFruits(Grid grid) {
        super(grid);
    }

    @Override
    protected void initMap() {
        super.initMap();
        // se agrego el constructor fruit para poder mantener el funcionamiento como en el modelo que se nos entrego
        String specialKey = new Fruit().getKey();

        map.put(specialKey + new Candy().getKey(), new CandyMove(grid));
        map.put(new Candy().getKey() + specialKey, new CandyMove(grid));


        map.put(specialKey + new HorizontalStripedCandy().getKey(), new CandyMove(grid));
        map.put(new HorizontalStripedCandy().getKey() + specialKey, new CandyMove(grid));

        map.put(specialKey + new VerticalStripedCandy().getKey(), new CandyMove(grid));
        map.put(new VerticalStripedCandy().getKey() + specialKey, new CandyMove(grid));

        map.put(new WrappedCandy().getKey() + specialKey, new CandyMove(grid));
        map.put(specialKey + new WrappedCandy().getKey(), new CandyMove(grid));

        map.put(specialKey + new Bomb().getKey(), new InvalidMove(grid));
        map.put(new Bomb().getKey() + specialKey, new InvalidMove(grid));

        map.put(specialKey + specialKey, new InvalidMove(grid));
    }
}
