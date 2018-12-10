package boulderdash.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Klasa obs�uguje wprowadzanie danych za pomoc� klawiatury
 * @author Kajetan Szafran
 *
 */
public class KeyManager implements KeyListener{

	private boolean[] keys;
	public boolean up, down, left, right;
	
	public KeyManager()
	{
		keys = new boolean[256];
	}
	
	public void updateKey()
	{
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
	}
	
	public void keyPressed(KeyEvent e)
	{
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) 
	{
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {}
}
