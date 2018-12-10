package boulderdash.logic.tiles;

/**
 * Klasa pochodna klasy Tile.<p>
 * Opisuje w�a�ciwo�ci fizyczne obiektu 'Boulder'.
 * @author Kajetan Szafran
 *
 */
public class BoulderTile extends Tile{

	public BoulderTile(int id)
	{
		super(id);
	}
	
	public boolean isMovable() { return true; }
	
	public boolean isRounded() { return true; }
}