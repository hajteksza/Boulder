package boulderdash.logic.tiles;

/**
 * Klasa pochodna klasy Tile.<p>
 * Opisuje w�a�ciwo�ci fizyczne obiektu 'Dirt'.
 * @author Kajetan Szafran
 *
 */
public class DirtTile extends Tile {

	public DirtTile(int id)
	{
		super(id);
	}
	
	public boolean isConsumable()
	{
		return true;
	}
}
