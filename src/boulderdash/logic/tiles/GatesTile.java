package boulderdash.logic.tiles;

/**
 * Klasa pochodna klasy Tile.<p>
 * Opisuje w�a�ciwo�ci fizyczne obiektu 'Gates'.
 * @author Kajetan Szafran
 *
 */
public class GatesTile extends Tile{

	public GatesTile(int id)
	{
		super(id);
	}
	
	public boolean isExplodable() { return false; }
	
	public boolean isGates() { return true; }
}
