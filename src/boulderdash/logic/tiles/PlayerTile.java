package boulderdash.logic.tiles;

/**
 * Klasa pochodna klasy Tile.<p>
 * Opisuje w�a�ciwo�ci fizyczne obiektu 'Player'.
 * 
 * @author Kajetan Szafran
 */
public class PlayerTile extends Tile{
	
	public PlayerTile(int id)
	{
		super(id);
	}
	
	public boolean isPlayer() { return true; }
}
