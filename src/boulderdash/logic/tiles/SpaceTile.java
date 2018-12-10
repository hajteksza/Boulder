package boulderdash.logic.tiles;

/**
 * Klasa pochodna klasy Tile.<p>
 * Opisuje w�a�ciwo�ci fizyczne obiektu 'Space'.
 * @author Kajetan Szafran
 *
 */
public class SpaceTile extends Tile {

	public SpaceTile(int id)
	{
		super(id);
	}
	
	public boolean isConsumable() { return true; }
	
	public boolean isEmpty() { return true; }
}
