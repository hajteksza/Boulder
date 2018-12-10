package boulderdash.logic.levels;

import boulderdash.logic.tiles.Tile;

/**
 * Klasa przechowywuje mapy poziom�w wykorzystywanych w grze.
 * @author Kajetan Szafran
 */
public class World {
	
	private int diamondsRequired;
	public final int MAX_LEVEL_NUMBER = 4;
	public static final int FIRST_LEVEL = 1; 
	/**
	 * Tablica przechowuj�ca kopi� wybranej planszy.<p>
	 *  Na tej tablicy wykonywane s� obliczenia.
	 */
	private int[][] currentMap;
	/**
	 * Tablica pomocnicza dla tablicy currentMap.
	 */
	private int[][] bufferMap;
	/**
	 * Tablica przechowuj�ca informacj� o kierunku ruchu przeciwnik�w w grze.
	 */
	private int[][] enemyDirectionMap;
	/**
	 * Tablica przechowuj�ca dane wysy�ane do widoku do wy�wietlania na ekran.
	 */
	private int[][] textureMap;
	/**
	 * Tablica pomocnicza dla textureMap. Pomaga w animacji wybuch�w.
	 */
	private int[][] textureBufferMap;
	
	private final int UP = 1;
	private final int DOWN = 2;
	private final int LEFT = 3;
	private final int RIGHT = 4;
	
	public World(int mapNumber)
	{
		currentMap		   =	new int[map1.length][map1[0].length];
		bufferMap 		   =	new int[map1.length][map1[0].length];
		enemyDirectionMap  =	new int[map1.length][map1[0].length];
		textureMap 	   	   = 	new int[map1.length][map1[0].length];
		textureBufferMap   = 	new int[map1.length][map1[0].length];
		setMap(mapNumber);
	}
	
	public void setMap(int map_number)
	{
		switch(map_number)
		{
			case 1:	copyMap(map1, currentMap);      diamondsRequired = 18;		break;
			case 2:	copyMap(map2, currentMap);		diamondsRequired = 18;		break;
			case 3:	copyMap(map3, currentMap);		diamondsRequired = 15;		break;
			case 4:	copyMap(test,currentMap);		diamondsRequired = 15;		break;
		}
	}
	
	public void copyMap(int[][] copyFrom, int[][] copyTo)
	{
		for(int i = 0; i < copyFrom.length; i++)
		{
			System.arraycopy(copyFrom[i], 0, copyTo[i], 0, copyFrom[i].length);
		}
	}
	
	public void updateTextureMap()
	{
		copyMap(currentMap, textureMap);
		
		for(int y = 0; y < currentMap.length; y++)
			for(int x = 0; x < currentMap[y].length; x++)
			{
				if(textureBufferMap[y][x] > 0)
					textureMap[y][x] = textureBufferMap[y][x];
			}
	}
	
	public void acceptMapChange(boolean cond)
	{
		if(cond)
			for(int y = 0; y < currentMap.length; y++)
				for(int x = 0; x < currentMap[y].length; x++)
					if(textureBufferMap[y][x] > 0)
						textureBufferMap[y][x] = 0;

	}
	
	public int getDiamondsRequirement() { return this.diamondsRequired; } 
	
	public int[][] getMap() { return currentMap; }
	
	public int[][] getBuffer() { return bufferMap; }
	
	public int[][] getTextureMap() { return textureMap; }
	
	public int[][] getTextureBufferMap() { return textureBufferMap; }
	
	public int[][] getEnemyMap() { return enemyDirectionMap; }
	
	public int getMapHeight() { return currentMap.length; }
	
	public int getMapWidth() { return currentMap[0].length; }
	
	/**
	 * Metoda zwracaj�ca obiekt znajduj�cy si� na podanych wsp�rz�dnych mapy.
	 */
	public Tile getTile(int y, int x)
	{
		Tile t = Tile.tileList[currentMap[y][x]];
		if(t == null)
			return Tile.tileList[0];
		return t;
	}
	
	/**
	 * Metoda zwracaj�ca obiekt znajduj�cy si� w odleg�o�ci <p>
	 * jednej jednostki w kierunku 'dir' od podanych wsp�rz�dnych mapy.
	 */
	public Tile getTile(int y, int x, int dir)
	{
		Tile t = null;
		
		switch (dir)
		{
		case UP:
			t = Tile.tileList[currentMap[y-1][x]];
			break;
			
		case DOWN:
			t = Tile.tileList[currentMap[y+1][x]];
			break;
			
		case LEFT:
			t = Tile.tileList[currentMap[y][x-1]];
			break;
			
		case RIGHT:
			t = Tile.tileList[currentMap[y][x+1]];
			break;
		}

		if(t == null)
			return Tile.tileList[0];
		return t;
	}

	private int[][] map1 = {	{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
								{3,5,4,5,5,4,5,5,6,5,4,4,5,5,5,5,5,5,6,5,5,5,5,5,6,4,5,5,5,5,4,6,3},
								{3,5,1,5,5,5,5,5,4,5,5,4,5,5,4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4,4,3}, 
								{3,5,4,5,5,4,5,5,4,5,5,5,4,5,5,5,4,5,5,5,5,5,5,5,5,5,4,4,5,5,5,5,3},
								{3,6,4,5,5,5,5,5,5,5,5,5,5,6,5,5,5,5,5,5,4,4,4,4,5,6,5,5,4,5,5,5,3},
			    				{3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,5,5,5,5,3},
			    				{3,6,5,5,5,4,4,5,5,5,4,5,5,5,4,5,5,5,5,0,5,5,4,5,5,5,5,4,5,5,5,4,3},
			    				{3,5,5,5,5,5,0,5,5,0,6,5,5,5,5,5,5,0,5,6,5,5,4,5,5,0,4,5,5,5,6,5,3},
			    				{3,4,5,4,5,5,0,5,5,5,5,5,5,5,0,5,5,0,0,0,5,5,5,5,5,0,5,5,5,4,4,5,3}, 
								{3,6,5,5,5,4,5,5,0,5,5,5,6,5,0,5,5,5,5,5,5,5,5,5,4,6,5,5,5,5,5,5,3},
			    				{3,5,5,5,5,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3},
			    				{3,5,4,6,5,5,5,5,5,4,5,5,5,5,5,5,5,5,5,5,4,0,4,5,5,5,5,4,5,5,5,5,3},
			    				{3,5,4,5,5,5,5,5,5,0,5,5,5,0,5,5,5,5,4,4,6,6,5,5,5,4,5,5,5,5,5,5,3},
			    				{3,5,5,5,4,5,5,5,5,6,5,5,5,4,5,5,5,5,4,5,5,5,5,5,5,5,5,4,5,5,5,5,3},
			    				{3,5,5,5,5,4,6,5,5,5,0,5,5,5,5,9,5,5,5,5,0,0,5,6,5,5,5,5,5,4,5,4,3},
			    				{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}};
	
	private int[][] map2 = {	{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
								{3,6,5,5,5,5,5,4,5,5,2,5,5,5,4,5,5,5,2,0,7,0,4,5,5,5,5,5,5,5,5,5,3},
								{3,5,1,5,5,5,4,4,5,5,5,5,6,5,0,6,5,5,2,5,5,5,5,5,5,6,2,2,2,2,5,5,3},
								{3,5,5,4,5,5,5,5,6,5,2,5,5,5,0,5,5,5,2,6,5,4,5,5,5,2,5,5,5,6,5,5,3},
								{3,5,2,2,5,2,2,2,2,2,2,5,5,5,0,5,5,5,2,2,2,2,2,2,2,5,5,5,5,2,5,5,3},
								{3,5,5,5,5,5,5,5,5,5,5,5,5,5,0,5,5,6,5,5,5,5,5,5,5,5,6,5,5,2,5,5,3},
								{3,6,5,5,5,4,5,5,5,5,6,5,5,5,0,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,3},
								{3,0,0,0,0,0,0,0,4,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3},
								{3,5,5,5,5,5,5,5,5,4,5,5,5,5,0,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,3},
								{3,2,2,2,2,2,2,2,2,2,2,5,5,5,0,5,4,6,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3},
								{3,6,5,5,5,4,5,5,4,5,2,5,5,4,0,5,5,5,2,5,5,5,5,5,5,5,5,5,5,5,5,6,3},
								{3,5,5,5,5,4,4,5,5,5,2,5,5,4,0,5,5,5,2,4,4,4,5,5,5,6,0,4,0,5,5,5,3},
								{3,5,6,5,5,5,5,4,5,5,0,0,0,5,0,0,0,0,0,0,0,5,5,5,5,5,5,5,5,5,5,5,3},
								{3,5,5,5,5,0,5,5,5,5,2,5,5,5,0,5,5,5,2,5,5,5,5,5,5,5,5,4,5,5,5,4,3},
								{3,7,0,0,0,0,5,5,6,5,2,5,4,5,0,5,5,5,2,4,5,5,6,5,5,4,5,5,5,6,5,4,3},
								{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}	};		
	
	private int[][] map3 = {	{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
								{3,5,5,5,5,5,5,5,5,5,5,3,4,5,4,5,4,5,6,3,4,4,5,5,6,5,5,3,5,4,5,5,3},
								{3,5,1,5,5,4,5,5,4,5,4,3,5,5,5,5,4,5,5,3,5,4,5,5,5,5,5,3,5,5,5,5,3},
								{3,5,5,5,5,5,5,5,5,5,5,3,5,4,5,4,5,5,4,3,5,4,5,4,5,5,6,3,5,4,5,5,3},
								{3,5,5,4,3,6,5,5,5,5,5,3,5,5,5,5,5,5,5,3,5,5,5,5,5,4,5,3,5,5,5,4,3},
								{3,5,5,5,5,5,5,5,5,5,5,3,5,5,5,5,6,5,5,3,5,5,5,0,5,5,5,3,5,5,5,5,3},
								{3,5,5,5,5,5,5,5,4,6,5,3,5,5,5,4,5,5,5,3,5,5,4,0,5,5,5,3,5,5,5,5,3},
								{3,4,5,3,5,5,5,5,5,5,5,3,5,4,5,5,9,5,5,3,5,5,4,0,5,5,5,3,5,4,5,5,3},
								{3,5,5,7,0,0,0,0,0,0,0,0,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,3},
								{3,5,5,0,5,5,4,5,5,5,6,0,4,5,5,5,5,5,5,5,5,5,0,0,0,0,0,0,0,0,0,0,3},
								{3,5,5,0,6,5,5,5,5,5,5,0,5,5,5,5,5,5,5,5,5,5,0,5,5,5,5,5,5,5,5,5,3},
								{3,5,5,0,5,5,6,5,5,5,4,0,5,5,5,5,4,5,5,5,6,5,0,5,5,6,5,5,5,5,6,9,3},
								{3,4,5,0,5,6,5,5,6,5,5,0,5,5,5,5,5,5,5,3,5,5,0,5,5,5,5,5,5,5,5,5,3},
								{3,5,5,0,5,5,5,5,5,5,5,0,5,5,5,4,5,5,4,3,4,5,0,5,5,4,5,4,5,4,5,5,3},
								{3,5,5,0,0,0,0,0,0,0,0,7,5,4,5,5,5,6,5,3,5,5,7,5,5,5,6,5,5,5,6,5,3},
								{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}	};	
	
	private int[][] test = {	{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
								{3,5,5,5,5,5,6,6,6,6,6,6,6,6,6,6,6,6,5,3,5,5,5,5,5,5,5,5,5,5,5,5,5},
								{3,5,1,5,5,5,6,6,6,6,6,6,6,6,6,6,6,6,6,3,5,5,5,5,5,5,5,5,5,5,5,5,5},
								{3,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4,5,3,5,5,5,5,5,5,5,5,5,5,5,5,5},
								{3,5,5,5,5,5,0,5,5,5,4,4,4,5,5,5,6,4,5,3,5,5,5,5,5,5,5,5,5,5,5,5,5},
								{3,5,5,5,5,5,0,5,5,4,4,4,4,4,5,5,6,5,5,3,5,5,5,5,5,5,5,5,5,5,5,5,5},
								{3,5,5,5,5,5,2,5,5,5,5,5,5,5,5,5,6,4,5,3,5,5,5,5,5,5,5,5,5,5,5,5,5},
								{3,5,0,0,0,0,0,0,0,4,0,0,5,5,5,5,6,0,5,3,5,5,5,5,5,5,5,5,5,5,5,5,5},
								{3,2,0,0,0,0,0,0,0,0,0,0,5,5,5,5,6,0,5,3,5,5,5,5,5,5,5,5,5,5,5,5,5},
								{3,5,0,0,0,0,0,0,0,0,0,0,5,5,5,5,5,0,2,3,5,5,5,5,5,5,5,5,5,5,5,5,5},
								{3,5,0,0,7,0,0,0,0,0,0,0,5,5,5,5,5,0,3,3,5,5,5,5,5,5,5,5,5,5,5,5,5},
								{3,5,0,0,2,2,2,2,2,2,0,0,5,5,5,5,5,0,4,3,5,5,5,5,5,5,5,5,5,5,5,5,5},
								{3,5,0,0,0,0,0,0,0,0,0,0,5,5,5,5,5,7,5,3,5,5,5,5,5,5,5,5,5,5,5,5,5},
								{3,5,0,0,0,0,0,0,0,0,0,0,5,5,5,5,5,5,9,3,5,5,5,5,5,5,5,5,5,5,5,5,5},
								{3,5,0,0,0,0,0,0,0,0,0,0,5,5,5,5,5,5,9,3,5,5,5,5,5,5,5,5,5,5,5,5,5},
								{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,5,5,5,5,5,5,5,5,5,5,5,5,5}	};
}
