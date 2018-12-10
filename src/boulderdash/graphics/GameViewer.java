package boulderdash.graphics;

import java.awt.Graphics;
import java.awt.image.*;
import boulderdash.input.KeyManager;
import boulderdash.input.MouseManager;

/**
 * Klasa obs�uguje wy�wietlanie �wiata gry na ekranie oraz animacje.
 * 
 * @author Kajetan Szafran
 */
public class GameViewer {
	
	private String window_title;
	private int width = 0;
	private int height = 0;
	private final int TILE_SIZE = 32;
	
	private boolean isMapChangeRequestNeeded;
	
	//STATE CONTROLL 
	private final boolean EXIT = true;
	private final boolean GAME = false;
	private boolean currentState;
	
	//INPUT
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	//RENDERING GRAPHICS
	private WindowDisplay gameWindow;
	private BufferStrategy buffstr;
	private Graphics graph;
	private ImageMaker images;
	private ImageMaker messageImage;
	
	private int[][] gameMap;
	private int[] scoreList;
	
	//SETTERS and GETTERS
	public void setMap(int[][] map) { gameMap = map; }
	
	public void setScoreList(int[] list) { scoreList = list; }

	public void setWidth(int width) { this.width = width; }

	public void setHeight(int height) { this.height = height; }
	
	public KeyManager getKeyManager() { return this.keyManager; }
	
	public MouseManager getMouseManager() { return this.mouseManager; }
	
	public void setState(boolean state) { this.currentState = state; }
	
	public boolean getState() { return this.currentState; }
	
	//=====================
	
	public GameViewer(int width, int height)
	{
		this.window_title = "Boulder Dash";
		keyManager 	 = new KeyManager();
		mouseManager = new MouseManager();
		isMapChangeRequestNeeded = false;
	}
	
	public void initView()
	{
		width *= TILE_SIZE;
		height *= TILE_SIZE;
		
		images = new ImageMaker("/textures/sprites.png");
		images.putImagesToArray();
		gameWindow = new WindowDisplay(window_title, width, height);
		gameWindow.getFrame().addKeyListener(keyManager);
		gameWindow.getFrame().addMouseListener(mouseManager);
		gameWindow.getFrame().addMouseMotionListener(mouseManager);
		gameWindow.getCanvas().addMouseListener(mouseManager);
		gameWindow.getCanvas().addMouseMotionListener(mouseManager);
	}	
	
//============================== RENDEROWANIE GRAFIKI =======================================
	
	public void renderExit()
	{
		messageImage = new ImageMaker("/textures/message.png");
		graph.drawImage(messageImage.getSheet(), 0, 0, null);
	}
	public void renderGame()
	{
		for(int yc = 0; yc < scoreList.length; yc++)
			graph.drawImage(images.getScoreTexture(scoreList[yc]), (int) yc*32, 0, null);
				
		for(int y = 0; y < gameMap.length; y++)
			for(int x = 0; x < gameMap[y].length; x++)
			{
				graph.drawImage(images.getTexture(animations[gameMap[y][x]]), (int) x*32, (int) y*32 + 32, null);
			}	
	}

	public void render()
	{
		isMapChangeRequestNeeded = false;
		
		buffstr = gameWindow.getCanvas().getBufferStrategy();
		if(buffstr == null)
		{
			gameWindow.getCanvas().createBufferStrategy(3);
			return;
		}
		
		graph = buffstr.getDrawGraphics();
		
		// Czyszczenie okna
		graph.clearRect(0, 0, width*TILE_SIZE, height*TILE_SIZE);
		
		//Rysowanie na ekran
		if(currentState == EXIT)
		{
			renderExit();
		}
		else if (currentState == GAME)
			renderGame();
		
		//==================
			
		buffstr.show();
		graph.dispose();
		
		updateTextureAnimations();
	}
	
//===================================================================================
	
	public boolean requestMapChange() { return this.isMapChangeRequestNeeded; }
	
	private int[] animations = {6,0,51,49,56,57,80,72,6,49,50,56,80,1};
	
	/**
	 * Metoda umo�liwia tworzenie animacji niekt�rych obiekt�w.<p>
	 * Metoda w p�tli wybiera kt�ra tekstura powinna by� w danym momencie u�yta,
	 * aby stworzy� p�ynn� animacj�.
	 */
	public void updateTextureAnimations()
	{
		for( int i = 0; i < animations.length; i++)
		{
				 if (animations[i] == 87)	animations[i] = 80;	// diamond 
			else if (animations[i] == 79)	animations[i] = 72;	// firefly
			else if (animations[i] == 50)	animations[i] = 48;	// hiddenGates
			else if (animations[i] == 48)	animations[i] = 50;	// hiddenGates
			else if (animations[i] == 5)	
			{
				animations[i] = 1;	// explosion
				isMapChangeRequestNeeded = true;
			}
			else if (animations[i] > 79 && animations[i] < 87)	animations[i]++; // diamond
			else if (animations[i] > 71 && animations[i] < 79)	animations[i]++; // firefly
			else if (animations[i] > 0 && animations[i] < 5)	animations[i]++; // explosion
				 
			else if(keyManager.right && animations[i] == 0)		animations[i] = 42;	//player
			else if(keyManager.left && animations[i] == 0)		animations[i] = 34;	//player
			else if(!keyManager.left && !keyManager.right && 
				   (animations[i] == 0 || animations[i] == 42 || animations[i] == 34))	animations[i] = 0;	//player
		}
	}
}
