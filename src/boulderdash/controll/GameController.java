package boulderdash.controll;

import boulderdash.graphics.*;
import boulderdash.logic.*;

/**
 * Zadania klasy:<p>
 * - po�redniczy w wymianie informacji pomi�dzy klasami GameModel i GameViewer;<p>
 * - implementuje g��wn� p�tl� gry,<p>
 * - wywo�uje odpowiednie metody powy�szych klas.
 * 
 * @author Kajetan Szafran
 */
public class GameController implements Runnable {
	
	private Thread thread;
	private GameViewer theView;
	private GameModel theModel;
	
	private boolean running = false;
	
	public GameController(GameViewer theView, GameModel theModel)
	{
		this.theView = theView;
		this.theModel = theModel;
	}
	
	public void sendDataToViewer()
	{
		theView.setMap(theModel.getWorld().getTextureMap());
		theView.setScoreList(theModel.getScoreList());
		theView.setState(theModel.getState());
	}
	
//=========== G��WNA P�TLA GRY =====================================
	
	public void run()
	{
		theModel.initModel(theView.getKeyManager(), theView.getMouseManager());
		theView.setState(theModel.getState());
		theView.setHeight(theModel.getWorld().getMapHeight() + 1); // +1 - miejsce dla paska statystyk
		theView.setWidth(theModel.getWorld().getMapWidth());
		theView.initView();
		
		int fps = 10;
		double timePerTick = 1000000000/fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		
		while(running)
		{
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1)
			{
				theModel.update();
				
				sendDataToViewer();
				
				theView.render();
				
				theModel.getWorld().acceptMapChange(theView.requestMapChange());
		
				delta--;
			}
			if(timer >= 1000000000)
			{
				timer = 0;
			}
		}
		stop();
	}
	
//=============================================================
	
	public synchronized void start()
	{
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		if (!running)
			return;
		running = false;
		try{
			thread.join();
		} 
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}
}
