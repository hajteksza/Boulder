package boulderdash.logic.tiles;

/**
 * Klasa pochodna klasy Tile.<p>
 * Opisuje w�a�ciwo�ci fizyczne obiektu 'Diamond'.
 * @author Kajetan Szafran
 *
 */
public class DiamondTile extends Tile{

	public DiamondTile(int id)
	{
		super(id);
	}
	
	public boolean isRounded() { return true; }
	
	public boolean isConsumable() { return true; }
}
