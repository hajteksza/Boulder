package boulderdash.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Klasa zajmuje si� dostarczaniem tekstur do wy�wietlania na ekran.
 * @author Kajetan Szafran
 */
public class ImageMaker {
	
	private static final int WIDTH = 32;
	private static final int HEIGHT = 32;
	
	private BufferedImage sheet;
	
	private BufferedImage[] score = new BufferedImage[100];
	
	private BufferedImage[] textures = new BufferedImage[100];
	
	//GETTERS and SETTERS
	public BufferedImage getScoreTexture(int a) { return score[a]; }
	
	public BufferedImage getTexture(int a) { return textures[a]; }
	
	public BufferedImage getSheet() { return this.sheet; }
	
	//====================
	
	public ImageMaker(String path)
	{
		this.sheet = loadImage(path);
	//	putImagesToArray();
	}
	
	/**
	 * Metoda �aduje grafik� z pliku.
	 */
	private BufferedImage loadImage(String path)
	{
		try
		{
			return ImageIO.read(ImageMaker.class.getResource(path));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	/**
	 * Metoda wycina poszczeg�lne tekstury z ca�ego pliku graficznego. 
	 * @param x 
	 * @param y
	 * @param width - d�ugo�� w pikselach wyci�tej tekstury
	 * @param height - szeroko�� w pikselach wyci�tej tekstury
	 */
	public BufferedImage cutImageSheet(int x, int y, int width, int height)
	{
		return sheet.getSubimage(x*WIDTH, y*HEIGHT, width, height);		
	}
	
	public void putImagesToArray()
	{
		int j = 0;
		for(int y = 12; y < 14; y++)
			for(int x = 0; x < 8; x++)
			{
				score[j] = cutImageSheet(x, y, WIDTH, HEIGHT);
				j++;
			}
		
		int i = 0;
		for(int y = 0; y < 12; y++)
			for(int x = 0; x < 8; x++)
			{
				textures[i] = cutImageSheet(x, y, WIDTH, HEIGHT);
				i++;
			}	
	}
}
