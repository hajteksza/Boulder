package boulderdash.logic.tiles;

/**
 * Klasa Tile:<p>
 * - Klasa bazowa dla wszystkich klas z przyrostkiem "Tile".<p>
 * - Zawiera metody opisuj�ce fizyczne w�a�ciwo�ci obiekt�w z gry;<p>
 * - Przechowuje po jednej instancji ka�dego obiektu;<p>
 * @author Kajetan Szafran
 *
 */
public abstract class Tile {

	// STATIC PART
	public static Tile[] tileList ={
						new SpaceTile(0),
						new PlayerTile(1),
						new WallTile(2),
						new MetalTile(3),
						new BoulderTile(4),
						new DirtTile(5),	
						new DiamondTile(6),
						new FireflyTile(7),
						new FireflyTile(8),
						new HiddenGatesTile(9),
						new GatesTile(10),
						new BoulderTile(11),
						new DiamondTile(12)
						};
	
	//CLASS
	protected final int id;
	
	public Tile(int id)
	{
		this.id = id;
	}
	
	public boolean isGates() { return false; }
	
	public boolean isPlayer() { return false; }
	
	public boolean isMovable() { return false; }
	
	public boolean isConsumable() { return false; }
	
	public boolean isExplodable() { return true; }
	
	public boolean isEmpty() { return false; }
	
	public boolean isRounded() { return false; }
	
	public int getId() { return id; }
	
}
