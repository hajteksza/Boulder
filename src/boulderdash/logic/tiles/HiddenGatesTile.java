package boulderdash.logic.tiles;

/**
 * Klasa pochodna klasy Tile.<p>
 * Opisuje w�a�ciwo�ci fizyczne obiektu 'HiddenGates'.
 * @author Kajetan Szafran
 *
 */
public class HiddenGatesTile extends Tile {

	public HiddenGatesTile(int id)
	{
		super(id);
	}
	
	public boolean isExplodable() { return false; }
}
