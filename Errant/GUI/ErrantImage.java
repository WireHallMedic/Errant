package Errant.GUI;

import java.awt.*;
import java.awt.image.*;
import Errant.Map.*;

// wrapper class for BufferedImage, so that when we need a bunch we can use the same interface
public class ErrantImage
{
   public static final int DEFAULT_SIZE = 32;
	protected BufferedImage image;
   protected BufferedImage baseImage;
	protected int width;
	protected int height;
	protected int halfWidth;
	protected int halfHeight;


	public BufferedImage getImage(){return image;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public int getHalfWidth(){return halfWidth;}
	public int getHalfHeight(){return halfHeight;}


   public ErrantImage(BufferedImage img)
   {
      this(img, DEFAULT_SIZE, DEFAULT_SIZE);
   }

   public ErrantImage(BufferedImage img, int tileSize)
   {
      this(img, tileSize, tileSize);
   }

   public ErrantImage(BufferedImage img, int w, int h)
   {
      setImage(img, false);
      setSize(w, h);
   }

	public void setImage(BufferedImage img){setImage(img, true);}
	public void setImage(BufferedImage img, boolean process)
   {
      baseImage = img;
      if(process)
         generateImage();
   }
   
   public void process()
   {
      halfWidth = width / 2;
      halfHeight = height / 2;
      generateImage();
   }
   
   public void generateImage()
   {
      image = ImageTools.scale(baseImage, width, height);
   }
   
	public void setWidth(int w){setSize(w, height);}
	public void setHeight(int h){setSize(width, h);}
   public void setSize(int w, int h)
   {
      width = w;
      height = h;
      process();
   }
   
   public void paint(Graphics2D g2d, int x, int y)
   {
      paintFromCorner(g2d, x - halfWidth, y - halfWidth);
   }
   
   public void paintFromCorner(Graphics2D g2d, int x, int y)
   {
      g2d.drawImage(getImage(), x, y, null);
   }
   
   public void paintAtTile(Graphics2D g2d, int xTile, int yTile){paintAtTile(g2d, xTile, yTile, 0.0, 0.0);}
   public void paintAtTile(Graphics2D g2d, int xTile, int yTile, double xOffset, double yOffset)
   {
      int tileSize = AnimationManager.getTileSize();
      int xPos = (xTile * tileSize) + (tileSize / 2) + ((int)(tileSize * xOffset));
      int yPos = (yTile * tileSize) + (tileSize / 2) + ((int)(tileSize * yOffset));
      paint(g2d, xPos, yPos);
   }
   
   public void paint(Graphics2D g2d, MapPosition position, int xCorner, int yCorner)
   {
      paintAtTile(g2d, position.getXLoc() - xCorner, position.getYLoc() - yCorner, position.getXOffset(), position.getYOffset());
   }
   
}