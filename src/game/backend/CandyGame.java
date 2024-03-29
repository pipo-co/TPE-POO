package game.backend;

import game.backend.cell.Cell;
import game.backend.element.Element;
import game.backend.level.gameState.GameState;

public class CandyGame implements GameListener {

	private Grid grid;
	private GameState state;
	
	public CandyGame(Grid grid) {
		this.grid = grid;
	}
	
	public void initGame() {
		state = grid.createState();
		grid.initialize();
		addGameListener(this);
	}

	//Se convirtio este metodo a un metodo de clase ya que no
	//utilizaba ninguna propiedad pertinente a la instancia
	public static int getSize() {
		return Grid.SIZE;
	}
	
	public boolean tryMove(int i1, int j1, int i2, int j2){
		return grid.tryMove(i1, j1, i2, j2);
	}

	public Cell get(int i, int j){
		return grid.getCell(i, j);
	}

	public void addGameListener(GameListener listener) {
		grid.addListener(listener);
	}

	public long getScore() {
		return state.getScore();
	}

	@Override
	public void cellExplosion(Element e) {
		state.addScore(e.getScore());
	}

	@Override
	public void gridUpdated() {
		//
	}

	//Nuevo: Se encarga de finalizar el nivel.
	public void finish(){
		grid.finish();
	}

	public GameState getState() {
		return state;
	}
}
