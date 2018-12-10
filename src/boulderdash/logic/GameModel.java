package boulderdash.logic;

import boulderdash.input.KeyManager;
import boulderdash.input.MouseManager;
import boulderdash.logic.levels.World;

/**
 * Klasa stanowi silnik gry. Zajmuje si� fizyk� i obliczeniami.
 * 
 * @author Kajetan Szafran
 */
public class GameModel {
		
	private KeyManager keyManager;
	private MouseManager mouseManager;
	private World world;
	
	private int currentMap;
	private int numberOfDimonds;
	private int[] score = {14,14,14,11,14,0,0,14,14,14,14,14,14,14,14,14,14,
						   14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14};
	
	//STATE CONTROLL
	private final boolean EXIT = true;
	private final boolean GAME = false;
	private boolean currentState;
	
	//ZMIENNE DO ROZGRYWKI
	private boolean playerKilled = false;
	private boolean win = false;
	private long timeNow;
	
	//GETTERS and SETTERS
	public void setKeyManager(KeyManager keyManager) { this.keyManager = keyManager; }
	
	public KeyManager getKeyManager() { return this.keyManager; }
	
	public void setMouseManager(MouseManager mouseManager) { this.mouseManager = mouseManager; }
	
	public MouseManager getMouseManager() { return this.mouseManager; }
	
	public World getWorld() { return world; }
	
	public int[] getScoreList() { return this.score; }
	
	public void setState(boolean state) { this.currentState = state; }
	
	public boolean getState() { return this.currentState; }
	
//======================== INICJOWANIE MODELU ==============================	
	
	public GameModel()
	{
		currentMap = world.FIRST_LEVEL;
		world = new World(currentMap);
		numberOfDimonds = 0;
	}
	
	public void initModel(KeyManager keyManager, MouseManager mouseManager)
	{
		setKeyManager(keyManager);
		setMouseManager(mouseManager);
		setState(GAME);
	}
	
//========================= AKTUALIZOWANIE DANYCH ===========================

	public void update()
	{	
		if(currentState == EXIT)
			updateExit();
		else if(currentState == GAME)
			updateGame();
	}	
	
	public void updateExit()
	{
		if(mouseManager.isLeftPressed())
			System.exit(0);
		else if(mouseManager.isRightPressed())
			System.exit(0);
	}
	
	public void updateGame()
	{
		keyManager.updateKey();
		calculateActions();
		updateMapPoints();
		world.updateTextureMap();
		
		checkIfEnoughDimonds();
		
		if(playerKilled || win)
			//Czas jest zatrzymywany na 2s
			if( System.currentTimeMillis() - timeNow > 2000)
			{
				if(playerKilled)	resetLevel();
				if(win)		winLevel();
				playerKilled = false;
				win = false;
			}
	}
	
	private void updateMapPoints()
	{
		for(int y = 0; y < world.getMapHeight(); y++)
			for(int x = 0; x < world.getMapWidth(); x++)
				if(world.getBuffer()[y][x] > 0)
				{
					world.getMap()[y][x] = world.getBuffer()[y][x];
					world.getBuffer()[y][x] = 0; // 0 == SPACE
				}
	}
	
//===================== STA�E WYKO�YSTYWANE PRZEZ MODEL ============================	
	
	private final int SPACE = 0;
	private final int PLAYER = 1;				@SuppressWarnings("unused")
	private final int WALL = 2;					@SuppressWarnings("unused")
	private final int METAL = 3;
	private final int BOULDER = 4;				@SuppressWarnings("unused")
	private final int DIRT = 5;
	private final int DIAMOND = 6;
	private final int FIREFLY = 7;
	private final int HIDDENGATES = 9;
	private final int GATES = 10;
	private final int BOULDERFALLING = 11;
	private final int DIAMONDFALLING = 12;
	private final int EXPLOSION = 13;
	
	private final int UP = 1;
	private final int DOWN = 2;
	private final int LEFT = 3;
	private final int RIGHT = 4;
	
//============================ FIZYKA GRY ===================================
	
	private void calculateActions()
	{
		for(int y = 0; y < world.getMapHeight(); y++)
			for(int x = 0; x < world.getMapWidth(); x++)
				switch(world.getMap()[y][x])
				{
				case PLAYER:
					if (keyManager.up) 		movePlayer(y,x,UP);	  	else
					if (keyManager.down) 	movePlayer(y,x,DOWN); 	else
					if (keyManager.left) 	movePlayer(y,x,LEFT);	else
					if (keyManager.right)	movePlayer(y,x,RIGHT);	
					break;
					
				case BOULDER:
					fall(y,x);
					break;
					
				case BOULDERFALLING:
					fall(y,x);
					break;
					
				case DIAMOND:
					fall(y,x);
					break;
					
				case DIAMONDFALLING:
					fall(y,x);
					break;
					
				case FIREFLY:
					moveEnemy(y,x);
					break;
				}
	}
	
	private boolean isTileEmpty(int y, int x)
	{
		return ( world.getBuffer()[y][x] == SPACE && world.getTile(y, x).isEmpty() );
	}
	
	public void explode(int y, int x)
	{
		for(int i = y-1; i<y+2; i++)
			for(int j = x-1; j<x+2; j++)
			{
				if(world.getTile(i, j).isExplodable())
				{
					world.getMap()[i][j] = SPACE;
					world.getBuffer()[i][j] = SPACE;
					world.getTextureBufferMap()[i][j] = EXPLOSION;
				}
			}
	}
	
	public void killPlayer()
	{
		timeNow = System.currentTimeMillis();
		playerKilled = true;
	}
	
	/**
	 * Metoda przesuwa obiekt na mapie z pozycji (y,x) na pozycj� oddalon� o jeden blok w stron� 'DIR'.
	 * @param y - wsp�rz�dna pocz�tkowa 
	 * @param x - wsp�rz�dna pocz�tkowa
	 * @param DIR - kierunek ruchu
	 */
	private void moveTile(int y, int x, int DIR)
	{
		switch (DIR)
		{
		case UP:		moveUp(y,x);		break;
		case DOWN:		moveDown(y,x);		break;
		case LEFT:		moveLeft(y,x);		break;
		case RIGHT:		moveRight(y,x);		break;
		}
	}
	private void moveUp(int y, int x)
	{
		world.getBuffer()[y-1][x] = world.getMap()[y][x];
		world.getMap()[y][x] = SPACE;
	}
	private void moveDown(int y, int x)
	{
		world.getBuffer()[y+1][x] = world.getMap()[y][x];
		world.getMap()[y][x] = SPACE;
	}
	private void moveLeft(int y, int x)
	{
		world.getBuffer()[y][x-1] = world.getMap()[y][x];
		world.getMap()[y][x] = SPACE;
	}
	private void moveRight(int y, int x)
	{
		world.getBuffer()[y][x+1] = world.getMap()[y][x];
		world.getMap()[y][x] = SPACE;
	}
	
	/**
	 * Metoda odpowiada za kalkulacj� spadania obiekt�w oraz 
	 * ewentualnych skutk�w uderzenia jednego obiektu o drugi. 
	 */
	private void fall(int y, int x)
	{
		if( isTileEmpty(y+1,x) && ( world.getMap()[y][x] == BOULDER || world.getMap()[y][x] == BOULDERFALLING ))
		{
			if( world.getMap()[y][x] == BOULDER )
				world.getMap()[y][x] = BOULDERFALLING;
			moveTile(y,x,DOWN);
		}
		else if( isTileEmpty(y+1,x) && ( world.getMap()[y][x] == DIAMOND || world.getMap()[y][x] == DIAMONDFALLING ))
		{
			if( world.getMap()[y][x] == DIAMOND )
				world.getMap()[y][x] = DIAMONDFALLING;
			moveTile(y,x,DOWN);
		}
		else if( world.getTile(y,x,DOWN).getId() == PLAYER && ( world.getMap()[y][x] == BOULDERFALLING || world.getMap()[y][x] == DIAMONDFALLING ))
		{
			explode(y+1,x);
			killPlayer();
		}
		else if( world.getTile(y,x,DOWN).getId() == FIREFLY )
		{
			moveTile(y,x,DOWN);
			explode(y+1,x);
		}
		else if ( world.getTile(y,x,DOWN).isRounded() )
		{
			if( isTileEmpty(y, x-1) && isTileEmpty(y+1, x-1) )		moveTile(y,x,LEFT); else
			if( isTileEmpty(y, x+1) && isTileEmpty(y+1, x+1) )		moveTile(y,x,RIGHT);
		}
		else if ( world.getMap()[y][x] == BOULDERFALLING )
			world.getMap()[y][x] = BOULDER;
		else if ( world.getMap()[y][x] == DIAMONDFALLING )
			world.getMap()[y][x] = DIAMOND;
	}
	
	/**
	 * Metoda odpowiedzialna za obliczanie ruch�w gracza
	 * @param y - wsp�rz�dna pocz�tkowa 
	 * @param x - wsp�rz�dna pocz�tkowa
	 * @param DIR - kierunek ruchu
	 */
	private void movePlayer(int y, int x, int DIR)
	{
		// DIR == direction
		
		int xc = x, // zmienne do poruszania obiekt�w obok gracza (kamieni)
			xk = x;
		
		switch(DIR)
		{
		case LEFT:		
			xc--;
			xk -= 2;	
			break;
						
		case RIGHT:	
			xc++;
			xk += 2;
			break;
		}
		if( world.getTile(y,x,DIR).isConsumable() || world.getTile(y,x,DIR).isMovable() )
		{
			//PORUSZANIE KAMIENI
			if( world.getTile(y,x,DIR).getId() == BOULDER && isTileEmpty(y,xk) && !isTileEmpty(y+1,xc) )
			{
				moveTile(y,xc,DIR); // move boulder
				moveTile(y,x,DIR);  // move player
			}
			//ZBIERANIE DIAMENTOW
			else if ( world.getTile(y,x,DIR).isConsumable() )
			{
				moveTile(y,x,DIR);
				if(world.getTile(y,x,DIR).getId() == DIAMOND)
				{
					collectDiamond();
					diamondsCollected();
				}
			}
		} 
		//WCHODZENIE DO WR�T
		else if( world.getTile(y,x,DIR).isGates())
		{
			world.getMap()[y][x] = SPACE;	// usuwanie gracza z mapy bo wszed� do bramy
			timeNow = System.currentTimeMillis();
			win = true;
		}
	}
		
	/**
	 * Metoda odpowiedzialna za odpowiedni wyb�r kierunku ruchu przeciwnik�w.
	 */
	public void enemyDir(int y, int x, int dir)
	{
		switch(dir)
		{
		case UP:		world.getEnemyMap()[y-1][x] = UP;		break;
		case DOWN:		world.getEnemyMap()[y+1][x] = DOWN;		break;
		case LEFT:		world.getEnemyMap()[y][x-1] = LEFT;		break;
		case RIGHT:		world.getEnemyMap()[y][x+1] = RIGHT;	break;
		}
		world.getEnemyMap()[y][x] = SPACE;
	}
		
	/**
	 * Metoda odpowiedzialna za obliczanie ruch�w wykonywnych przez przeciwnik�w.
	 */
	public void moveEnemy(int y, int x)
	{
		switch( world.getEnemyMap()[y][x] )
		{
		case UP:
			if( isTileEmpty(y-1,x) && !isTileEmpty(y,x+1) )		{ moveTile(y,x,UP);	enemyDir(y,x,UP); }	else
			if( isTileEmpty(y,x+1) ) 	{ moveTile(y,x,RIGHT);	enemyDir(y,x,RIGHT); }	else
			if( isTileEmpty(y,x-1) ) 	{ moveTile(y,x,LEFT );	enemyDir(y,x,LEFT);  }	else
			if( isTileEmpty(y+1,x) ) 	{ moveTile(y,x,DOWN );	enemyDir(y,x,DOWN);	 }
				
			if( world.getTile(y,x,DOWN ).isPlayer()  )		{ explode(y+1,x);	killPlayer(); }  else
			if( world.getTile(y,x,UP   ).isPlayer()  )		{ explode(y-1,x);	killPlayer(); }  else
			if( world.getTile(y,x,LEFT ).isPlayer()  )		{ explode(y,x-1); 	killPlayer(); }  else
			if( world.getTile(y,x,RIGHT).isPlayer()  )		{ explode(y,x+1);	killPlayer(); } 
			break;
				
		case DOWN:
			if( isTileEmpty(y+1,x) && !isTileEmpty(y,x-1) )		{ moveTile(y,x,DOWN);	enemyDir(y,x,DOWN); }	else
			if( isTileEmpty(y,x-1) ) 	{ moveTile(y,x,LEFT );	enemyDir(y,x,LEFT);	 }	else
			if( isTileEmpty(y,x+1) ) 	{ moveTile(y,x,RIGHT);	enemyDir(y,x,RIGHT); }	else
			if( isTileEmpty(y-1,x) ) 	{ moveTile(y,x,UP   );	enemyDir(y,x,UP);	 }
			
			if( world.getTile(y,x,DOWN ).isPlayer()  )		{ explode(y+1,x);	killPlayer(); }  else
			if( world.getTile(y,x,UP   ).isPlayer()  )		{ explode(y-1,x);	killPlayer(); }  else
			if( world.getTile(y,x,LEFT ).isPlayer()  )		{ explode(y,x-1); 	killPlayer(); }  else
			if( world.getTile(y,x,RIGHT).isPlayer()  )		{ explode(y,x+1);	killPlayer(); }
			break;
				
		case LEFT:
			if( isTileEmpty(y,x-1) && !isTileEmpty(y-1,x) )		{ moveTile(y,x,LEFT);	enemyDir(y,x,LEFT); }	else
			if( isTileEmpty(y-1,x) ) 	{ moveTile(y,x,UP   );	enemyDir(y,x,UP);	 }	else
			if( isTileEmpty(y+1,x) ) 	{ moveTile(y,x,DOWN );	enemyDir(y,x,DOWN);	 }	else
			if( isTileEmpty(y,x+1) ) 	{ moveTile(y,x,RIGHT);	enemyDir(y,x,RIGHT); }
				
			if( world.getTile(y,x,DOWN ).isPlayer()  )		{ explode(y+1,x);	killPlayer(); }  else
			if( world.getTile(y,x,UP   ).isPlayer()  )		{ explode(y-1,x);	killPlayer(); }  else
			if( world.getTile(y,x,LEFT ).isPlayer()  )		{ explode(y,x-1);	killPlayer(); }  else
			if( world.getTile(y,x,RIGHT).isPlayer()  )		{ explode(y,x+1);	killPlayer(); } 
			break;
				
		case RIGHT:
			if( isTileEmpty(y,x+1) && !isTileEmpty(y+1,x) )		{ moveTile(y,x,RIGHT);	enemyDir(y,x,RIGHT); }	else
			if( isTileEmpty(y+1,x) ) 	{ moveTile(y,x,DOWN);	enemyDir(y,x,DOWN);  }	else
			if( isTileEmpty(y-1,x) ) 	{ moveTile(y,x,UP  );	enemyDir(y,x,UP); 	 }	else
			if( isTileEmpty(y,x-1) ) 	{ moveTile(y,x,LEFT);	enemyDir(y,x,LEFT);  }
				
			if( world.getTile(y,x,DOWN ).isPlayer()  )		{ explode(y+1,x);	killPlayer(); }  else
			if( world.getTile(y,x,UP   ).isPlayer()  )		{ explode(y-1,x);	killPlayer(); }  else
			if( world.getTile(y,x,LEFT ).isPlayer()  )		{ explode(y,x-1); 	killPlayer(); }  else
			if( world.getTile(y,x,RIGHT).isPlayer()  )		{ explode(y,x+1);	killPlayer(); }
			break;
				
		default:
			world.getEnemyMap()[y][x] = RIGHT;
			break;
				
		}
	}
		
//====================== STATYSTYKI I ZMIANA POZIOM�W ==========================
	
	public void checkIfEnoughDimonds()
	{
		if(numberOfDimonds >= world.getDiamondsRequirement())
			openGates();
	}
	
	public void openGates()
	{
		for(int y = 0; y < world.getMapHeight(); y++)
			for(int x = 0; x < world.getMapWidth(); x++)
				if(world.getMap()[y][x] == HIDDENGATES)
					world.getMap()[y][x] = GATES;
	}
	
	public void winLevel()
	{
		currentMap++;
		if(currentMap > world.MAX_LEVEL_NUMBER)
			currentState = EXIT;
		else
			world.setMap(currentMap);
		
		numberOfDimonds = 0;
		score[5] = 0;
		score[6] = 0;
	}
	
	public void resetLevel()
	{
		world.setMap(currentMap);
		numberOfDimonds = 0;
		score[5] = 0;
		score[6] = 0;
	}
		
	private void collectDiamond()
	{
		this.numberOfDimonds++;
		if(score[6] >= 0 && score[6] < 9)
			score[6]++;
		else if(score[6] == 9)
		{
			score[6] = 0;
			score[5]++;
		}
	}

	public void resetDiamonds() { this.numberOfDimonds = 0; }
	
	public void diamondsCollected()
	{
		System.out.println("Number od diamonds: "+ this.numberOfDimonds);
	}
	
}
