package boulderdash.logic.tiles;

/**
 * Klasa pochodna klasy Tile.<p>
 * Opisuje w�a�ciwo�ci fizyczne obiektu 'Wall'.
 * @author Kajetan Szafran
 *
 */
public class WallTile extends Tile{

	public WallTile(int id)
	{
		super(id);
	}
	
	public boolean isRounded() { return true; }
}
