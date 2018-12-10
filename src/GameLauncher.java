import boulderdash.controll.GameController;
import boulderdash.graphics.GameViewer;
import boulderdash.logic.GameModel;

/**
 * Klasa uruchamia wszystkie komponenty gry.
 *
 *@author Kajetan Szafran
 */
public class GameLauncher {
	
	public static void main(String[] args)
	{
		GameModel theModel = new GameModel();
		
		GameViewer theView = new GameViewer(20, 15);
		
		GameController theController = new GameController(theView, theModel);
		
		theController.start();	
	}
}
