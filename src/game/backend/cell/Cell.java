package game.backend.cell;

import game.backend.Grid;
import game.backend.element.Element;
import game.backend.element.Nothing;
import game.backend.move.Direction;

public class Cell {
	
	protected Grid grid;
	protected Cell[] around = new Cell[Direction.values().length];
	protected Element content;
	
	public Cell(Grid grid) {
		this.grid = grid;
		this.content = new Nothing();
	}

	public Cell(){}
	
	public void setAround(Cell up, Cell down, Cell left, Cell right) {
		this.around[Direction.UP.ordinal()] = up;
		this.around[Direction.DOWN.ordinal()] = down;
		this.around[Direction.LEFT.ordinal()] = left;
		this.around[Direction.RIGHT.ordinal()] = right;
	}

	//Termine de caer
	public boolean hasFloor() {
		return !around[Direction.DOWN.ordinal()].isEmpty();
	}
	
	public boolean isMovable(){
		return content.isMovable();
	}

	public boolean isEmpty() {
		return !content.isSolid();
	}

	public boolean isCombinable(){ return content.isCombinable(); }

	public Element getContent() {
		return content;
	}

	public String getKey(){
		return "CELL";
	}
	
	//Deja Nothing como contenido y realiza la explosion si es que tiene
	public void clearContent() {
		if (isMovable()) {
			Direction[] explosionCascade = content.explode();
			grid.cellExplosion(content);
			this.content = new Nothing();
			if (explosionCascade != null) {
				expandExplosion(explosionCascade); 
			}
			this.content = new Nothing();
		}
	}

	//Realiza la explosion en las direcciones indicadas
	private void expandExplosion(Direction[] explosion) {
		for(Direction d: explosion) {
			this.around[d.ordinal()].explode(d);
		}
	}

	//Recibe una direccion y conduce la explosion en esa direccion
	private void explode(Direction d) {
		clearContent();//Arreglar la explosion
		if (this.around[d.ordinal()] != null)
			this.around[d.ordinal()].explode(d);
	}
	
	public Element getAndClearContent() {
		if (isMovable()) {
			Element ret = content;
			this.content = new Nothing();
			return ret;
		}
		return null;
	}

	public boolean fallUpperContent() {
		Cell up = around[Direction.UP.ordinal()];
		if (this.isEmpty() && !up.isEmpty() && up.isMovable()) {
			this.content = up.getAndClearContent();
			grid.wasUpdated();
			//Llegue y  prueba romperse
			if (this.hasFloor()) {
				grid.tryRemove(this);
				return true;
			} else {
				Cell down = around[Direction.DOWN.ordinal()];
				return down.fallUpperContent();
			}
		} 
		return false;
	}
	
	public void setContent(Element content) {
		this.content = content;
	}

	public boolean isBottom(){
		Cell down = around[Direction.DOWN.ordinal()];
		return down != null && !down.isMovable() && hasFloor() && !down.isCombinable();
	}

}
