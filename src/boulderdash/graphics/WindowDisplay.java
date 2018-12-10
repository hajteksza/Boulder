package boulderdash.graphics;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Klasa obs�uguje okno u�ywane do wy�wietlania tekstur.
 * @author Kajetan Szafran
 *
 */
public class WindowDisplay{

	private JFrame frame;
	private Canvas canvas;
	
	private String title;
	private int width = 0;
	private int height = 0;

	//GETTERS and SETTERS
	public Canvas getCanvas() { return this.canvas; }

	public JFrame getFrame() { return this.frame ; }
	
	//===================
	
	public WindowDisplay(String title, int width, int height)
	{
		this.title = title;
		this.width = width;
		this.height = height;
		
		createWindow();
	}
	
	private void createWindow()
	{
		frame = new JFrame(this.title);
		frame.setSize(this.width, this.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(this.width, this.height));
		canvas.setMinimumSize(new Dimension(this.width, this.height));
		canvas.setMaximumSize(new Dimension(this.width, this.height));
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack();
	}
}
