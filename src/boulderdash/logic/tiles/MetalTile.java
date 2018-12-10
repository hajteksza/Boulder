package boulderdash.logic.tiles;

/**
 * Klasa pochodna klasy Tile.<p>
 * Opisuje w�a�ciwo�ci fizyczne obiektu 'Metal'.
 * @author Kajetan Szafran
 *
 */
public class MetalTile extends Tile{

	public MetalTile(int id)
	{
		super(id);
	}
	
	public boolean isExplodable() { return false; }
}

